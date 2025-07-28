import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../app/store";
import { fetchArticle } from "../features/articleSlice";
import ArticleCard from "./ArticleCard";
import { Spinner, Alert, Col, Row } from "react-bootstrap";
import { Article } from "../types/Article.type";

const MyArticlesList = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { articles, loading, error } = useSelector(
    (state: RootState) => state.article
  );
  const [selectedArticle, setSelectedArticle] = useState<Article | null>(null);
  const [showDetails, setShowDetails] = useState(false);

  useEffect(() => {
    dispatch(fetchArticle());
  }, [dispatch]);

  const handleCardClick = (article: Article) => {
    setSelectedArticle(article);
    setShowDetails(true);
  };

  if (loading) return <Spinner animation="border" />;
  if (error) return <Alert variant="danger">{error}</Alert>;

  return (
    <>
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
    </>
  );
};

export default MyArticlesList;
