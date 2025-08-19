import React from "react";
import { Card, Button } from "react-bootstrap";

interface CommentItemProps {
  id: number;
  author: string;
  createdAt: string;
  content: string;
  canDelete: boolean;
  onDelete: (id: number) => void;
}

const CommentItem: React.FC<CommentItemProps> = ({
  id,
  author,
  createdAt,
  content,
  canDelete,
  onDelete,
}) => (
  <Card className="mb-2 shadow-sm border-0 bg-light">
    <Card.Body className="p-3">
      <div className="d-flex justify-content-between align-items-center mb-2">
        <div>
          <strong>{author}</strong>{" "}
          <span className="text-muted small">
            {new Date(createdAt).toLocaleString("pl-PL")}
          </span>
        </div>
        {canDelete && (
          <Button
            variant="outline-danger"
            size="sm"
            onClick={() => {
              if (window.confirm("Na pewno usunąć komentarz?")) {
                onDelete(id);
              }
            }}
          >
            Usuń
          </Button>
        )}
      </div>
      <div style={{ whiteSpace: "pre-wrap" }}>{content}</div>
    </Card.Body>
  </Card>
);

export default CommentItem;
