import { Card, Badge } from "react-bootstrap";
import { Article } from "../../types/Article.type";

const CARD_STYLE: React.CSSProperties = {
  cursor: "pointer",
  transition: "transform 0.2s ease, box-shadow 0.2s ease",
};

const HOVER_STYLE = {
  transform: "scale(1.03)",
  boxShadow: "0 8px 20px rgba(0, 0, 0, 0.15)",
  backgroundColor: "#f8f9fa",
};

const DEFAULT_STYLE = {
  transform: "scale(1)",
  boxShadow: "0 0 0 rgba(0,0,0,0)",
  backgroundColor: "white",
};

const formatDate = (date: string | Date) =>
  new Date(date).toLocaleDateString();

const ArticleCard = ({
  article,
  onClick,
}: {
  article: Article;
  onClick?: () => void;
}) => {
  const handleMouseEnter = (e: React.MouseEvent<HTMLDivElement>) => {
    Object.assign(e.currentTarget.style, HOVER_STYLE);
  };

  const handleMouseLeave = (e: React.MouseEvent<HTMLDivElement>) => {
    Object.assign(e.currentTarget.style, DEFAULT_STYLE);
  };

  return (
    <Card
      onClick={onClick}
      className="shadow-sm"
      style={CARD_STYLE}
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
    >
      <Card.Body>
        <Card.Title className="fs-4 fw-bold">{article.title}</Card.Title>
        <div className="mb-2 text-muted small">
          <span>📅 {formatDate(article.createdAt)}</span>
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
      <Card.Footer className="d-flex justify-content-between align-items-center bg-light">
        <small className="text-muted">
          {article.commentsIds?.length ? (
            <span>💬 {article.commentsIds.length}</span>
          ) : null}
        </small>
        <small className="text-muted">
          {article.views ? <span>👁️ {article.views}</span> : null}
        </small>
      </Card.Footer>
    </Card>
  );
};

export default ArticleCard;
