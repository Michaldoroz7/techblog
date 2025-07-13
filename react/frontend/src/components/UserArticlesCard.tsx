import { Alert, Card, ListGroup, Row, Spinner } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../app/store";
import { useEffect } from "react";
import { fetchArticle } from "../features/articleSlice";
import { useNavigate } from "react-router-dom";

const UserArticlesCard = () => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();
  const { articles, loading, error } = useSelector(
    (state: RootState) => state.article
  );

  useEffect(() => {
    dispatch(fetchArticle());
  }, [dispatch]);

  if (loading) return <Spinner animation="border" />;
  if (error) return <Alert variant="danger">{error}</Alert>;
  if (!articles || articles.length === 0)
    return (
      <Card className="mt-3">
        <Card.Header>Twoje artykuły</Card.Header>
        <Card.Body>Brak artykułów.</Card.Body>
      </Card>
    );

  return (
    <>
      <Card>
        <Card.Header>Artykuły</Card.Header>
        <Card.Body>
            <Row md={2}>
            {articles.map((article) => (
                <Card>
                    <Card.Header>{article.title}</Card.Header>
                    <Card.Body></Card.Body>
                    <Card.Footer> {new Date(article.createdAt).toLocaleDateString()}</Card.Footer>
                </Card>
            ))}
            </Row>
        </Card.Body>
      </Card>
    </>
  );
};

export default UserArticlesCard;
