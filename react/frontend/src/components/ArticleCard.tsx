import { Card, Badge } from "react-bootstrap";
import { Article } from "../types/Article.type";

const ArticleCard = ({
  article,
  onClick,
}: {
  article: Article;
  onClick?: () => void;
}) => {
  return (
    <Card
      onClick={onClick}
      className="shadow-sm"
      style={{
        cursor: "pointer",
        transition: "transform 0.2s ease, box-shadow 0.2s ease",
      }}
      onMouseEnter={(e) => {
        e.currentTarget.style.transform = "scale(1.03)";
        e.currentTarget.style.boxShadow = "0 8px 20px rgba(0, 0, 0, 0.15)";
        e.currentTarget.style.backgroundColor = "#f8f9fa"; // jasne podświetlenie
      }}
      onMouseLeave={(e) => {
        e.currentTarget.style.transform = "scale(1)";
        e.currentTarget.style.boxShadow = "0 0 0 rgba(0,0,0,0)";
        e.currentTarget.style.backgroundColor = "white";
      }}
    >
      <Card.Body>
        <Card.Title className="fs-4 fw-bold">{article.title}</Card.Title>

        <div className="mb-2 text-muted small">
          <span>📅 {new Date(article.createdAt).toLocaleDateString()}</span>
          {" • "}
          <Badge bg="info" className="text-dark">
            {article.category}
          </Badge>
        </div>

        <Card.Text className="text-secondary">{article.summary}</Card.Text>

        <div className="text-end">
          <small className="text-muted">
            ✍️ {article.authorName || "Nieznany autor"}
          </small>
        </div>
      </Card.Body>
    </Card>
  );
};

export default ArticleCard;
