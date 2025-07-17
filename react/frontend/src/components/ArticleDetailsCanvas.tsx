import {
  Offcanvas,
  Badge,
  ListGroup,
  Spinner,
  Button,
  Form,
} from "react-bootstrap";
import { Article } from "../types/Article.type";
import { motion } from "framer-motion";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../app/store";
import {
  createComment,
  deleteComment,
  fetchCommentsByIds,
} from "../features/commentSlice";
import { fetchArticleById } from "../features/articleSlice";

interface Props {
  show: boolean;
  onHide: () => void;
  article: Article | null;
}

const ArticleDetailsCanvas = ({ show, onHide, article }: Props) => {
  const dispatch = useDispatch<AppDispatch>();
  const [newComment, setNewComment] = useState("");
  const { comments, loading } = useSelector(
    (state: RootState) => state.comment
  );
  const [commentLoading, setCommentLoading] = useState(false);
  const loggedUsername = localStorage.getItem("username");

  useEffect(() => {
    if (article?.commentsIds && article.commentsIds.length > 0) {
      dispatch(fetchCommentsByIds(article.commentsIds));
    }
  }, [dispatch, article]);

  const handleAddComment = async () => {
    if (!article || newComment.trim() === "") return;

    setCommentLoading(true);

    await dispatch(
      createComment({
        articleId: article.id,
        content: newComment.trim(),
      })
    );

    setNewComment("");

    const updatedArticle = await dispatch(
      fetchArticleById(article.id)
    ).unwrap();

    if (updatedArticle.commentsIds) {
      await dispatch(fetchCommentsByIds(updatedArticle.commentsIds));
    }

    setCommentLoading(false);
  };

  const handleDeleteComment = async (commentId: number) => {
    if (!article) return;
    setCommentLoading(true);
    await dispatch(deleteComment(commentId));
    const updatedArticle = await dispatch(
      fetchArticleById(article.id)
    ).unwrap();
    if (updatedArticle.commentsIds) {
      await dispatch(fetchCommentsByIds(updatedArticle.commentsIds));
    }
    setCommentLoading(false);
  };

  if (!article) return null;

  return (
    <Offcanvas show={show} onHide={onHide} placement="end" className="w-50">
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

          <hr className="my-4" />

          <h5 className="mb-3">💬 Komentarze ({comments.length})</h5>

          {commentLoading ? (
            <div className="d-flex justify-content-center my-2">
              <Spinner animation="border" size="sm" />
            </div>
          ) : loading ? (
            <Spinner animation="border" size="sm" />
          ) : comments.length === 0 ? (
            <p className="text-muted">Brak komentarzy.</p>
          ) : (
            <ListGroup variant="flush" className="mb-3">
              {comments.map((comment) => (
                <ListGroup.Item key={comment.id}>
                  <strong>{comment.authorUsername}</strong> –{" "}
                  <span className="text-muted small">
                    {new Date(comment.createdAt).toLocaleString("pl-PL")}
                  </span>
                  <div>{comment.content}</div>
                  {comment.authorUsername === loggedUsername && (
                    <Button
                      variant="danger"
                      size="sm"
                      className="mt-2"
                      onClick={() => handleDeleteComment(comment.id)}
                    >
                      X
                    </Button>
                  )}
                </ListGroup.Item>
              ))}
            </ListGroup>
          )}

          <Form>
            <Form.Group className="mb-2" controlId="commentInput">
              <Form.Control
                as="textarea"
                rows={3}
                placeholder="Dodaj komentarz..."
                value={newComment}
                onChange={(e) => setNewComment(e.target.value)}
              />
            </Form.Group>
            <Button
              variant="primary"
              onClick={handleAddComment}
              disabled={!newComment.trim()}
            >
              Dodaj komentarz
            </Button>
          </Form>
        </motion.div>
      </Offcanvas.Body>
    </Offcanvas>
  );
};

export default ArticleDetailsCanvas;
