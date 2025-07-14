import { Offcanvas, Badge } from "react-bootstrap";
import { Article } from "../types/Article.type";
import { motion } from 'framer-motion';

interface Props {
  show: boolean;
  onHide: () => void;
  article: Article | null;
}

const ArticleDetailsCanvas = ({ show, onHide, article }: Props) => {
  if (!article) return null;

  return (
    <Offcanvas
      show={show}
      onHide={onHide}
      placement="end"
      className="w-50"
    >
      <Offcanvas.Header closeButton className="bg-primary text-white">
        <Offcanvas.Title>{article.title}</Offcanvas.Title>
      </Offcanvas.Header>

      <Offcanvas.Body>
        <motion.div
          initial={{ opacity: 0, x: 50 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 0.3 }}
        >
          <p className="text-muted mb-2">
            Dodano:{" "}
            <strong>
              {new Date(article.createdAt).toLocaleDateString("pl-PL", {
                day: "numeric",
                month: "long",
                year: "numeric",
              })}
            </strong>
          </p>

          <Badge bg="info" className="mb-3">
            {article.category}
          </Badge>

          <h5 className="mt-3">Opis</h5>
          <p className="text-secondary">{article.summary}</p>

          <hr />

          <h5 className="mt-4 mb-3">Treść artykułu</h5>
          <div
            style={{
              whiteSpace: "pre-line",
              lineHeight: "1.7",
              fontSize: "1rem",
            }}
          >
            {article.content}
          </div>
        </motion.div>
      </Offcanvas.Body>
    </Offcanvas>
  );
};

export default ArticleDetailsCanvas;
