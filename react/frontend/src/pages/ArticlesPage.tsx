import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../app/store";
import { fetchAllArticles } from "../features/articleSlice";
import { Row, Col, Spinner, Alert, Button } from "react-bootstrap";
import ArticleCard from "../components/ArticleCard";
import { Article } from "../types/Article.type";
import ArticleDetails from "../components/ArticleDetails";
import AddArticleOffcanvas from "../components/AddArticleCanvas";
import { AnimatePresence, motion } from "framer-motion";

const ArticlesPage = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { articles, loading, error } = useSelector(
    (state: RootState) => state.article
  );
  const [selectedArticle, setSelectedArticle] = useState<Article | null>(null);

  useEffect(() => {
    dispatch(fetchAllArticles());
  }, [dispatch]);


  if (loading) return <Spinner animation="border" />;
  if (error) return <Alert variant="danger">{error}</Alert>;

  return (
    <Row className="g-0">
      <Col
        md={4}
        style={{
          borderRight: "1px solid #eee",
          minHeight: "100vh",
          background: "#f8f9fa",
        }}
      >
        <div className="d-flex justify-content-between align-items-center p-3">
          <h4 className="mb-0">Wszystkie Artykuły</h4>
          <AddArticleOffcanvas />
        </div>
        {loading && <Spinner animation="border" />}
        {error && <Alert variant="danger">{error}</Alert>}
        {articles.map((article) => (
          <div
            key={article.id}
            onClick={() => setSelectedArticle(article)}
            style={{ cursor: "pointer" }}
          >
            <ArticleCard article={article} />
          </div>
        ))}
      </Col>

      <Col md={8} style={{ padding: "2rem" }}>
        <AnimatePresence mode="wait">
          {selectedArticle ? (
            <motion.div
              key={selectedArticle.id}
              initial={{ opacity: 0, x: 50 }}
              animate={{ opacity: 1, x: 0 }}
              exit={{ opacity: 0, x: -50 }}
              transition={{ duration: 0.3 }}
            >
              <ArticleDetails article={selectedArticle} />
            </motion.div>
          ) : (
            <motion.div
              key="empty"
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              transition={{ duration: 0.2 }}
              className="text-muted text-center"
              style={{ marginTop: "30%" }}
            >
              <h5>Wybierz artykuł, aby zobaczyć szczegóły</h5>
            </motion.div>
          )}
        </AnimatePresence>
      </Col>
    </Row>
  );
};

export default ArticlesPage;
