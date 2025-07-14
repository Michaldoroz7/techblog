import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../app/store";
import { fetchAllArticles } from "../features/articleSlice";
import { Row, Col, Spinner, Alert } from "react-bootstrap";
import ArticleCard from "../components/ArticleCard";
import AddArticleOffcanvas from "../components/AddArticleCanvas";
import { Article } from "../types/Article.type";
import ArticleDetailsCanvas from "../components/ArticleDetailsCanvas";

const ArticlesPage = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { articles, loading, error } = useSelector(
    (state: RootState) => state.article
  );
  const [selectedArticle, setSelectedArticle] = useState<Article | null>(null);
  const [showDetails, setShowDetails] = useState(false);

  useEffect(() => {
    dispatch(fetchAllArticles());
  }, [dispatch]);

  const handleCardClick = (article: Article) => {
    setSelectedArticle(article);
    setShowDetails(true);
  };

  if (loading) return <Spinner animation="border" />;
  if (error) return <Alert variant="danger">{error}</Alert>;

  return (
    <div>
      <Row className="align-items-center mb-4">
        <Col>
          <h2 className="m-0">Wszystkie Artykuły</h2>
        </Col>
        <Col xs="auto">
          <AddArticleOffcanvas />
        </Col>
      </Row>

      <Row xs={1} md={2} lg={3} className="g-4">
        {articles.map((article) => (
          <Col key={article.title}>
            <ArticleCard
              article={article}
              onClick={() => handleCardClick(article)}
            />
          </Col>
        ))}
      </Row>

      <ArticleDetailsCanvas
        show={showDetails}
        onHide={() => setShowDetails(false)}
        article={selectedArticle}
      />
    </div>
  );
};

export default ArticlesPage;
