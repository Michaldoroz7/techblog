import { Badge, Spinner, Button, Form, Card } from "react-bootstrap";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";

import ReactMarkdown from "react-markdown";
import { fetchArticleById } from "../../features/articleSlice";
import {
  fetchCommentsByIds,
  createComment,
  deleteComment,
} from "../../features/commentSlice";
import { Article } from "../../types/Article.type";
import { AppDispatch, RootState } from "../../app/store";
import "highlight.js/styles/github.css";
import rehypeHighlight from "rehype-highlight";
import CodeBlock from "./CodeBlock";
import CommentItem from "./CommentItem";

const ArticleDetails = ({ article }: { article: Article }) => {
  const dispatch = useDispatch<AppDispatch>();
  const [newComment, setNewComment] = useState("");
  const { comments, loading } = useSelector(
    (state: RootState) => state.comment
  );
  const [commentLoading, setCommentLoading] = useState(false);
  const loggedUsername = localStorage.getItem("username");

  useEffect(() => {
    if (article?.commentsIds?.length) {
      dispatch(fetchCommentsByIds(article.commentsIds));
    }
  }, [dispatch, article]);

  const refreshComments = async (articleId: number) => {
    const updatedArticle = await dispatch(fetchArticleById(articleId)).unwrap();
    if (updatedArticle.commentsIds?.length) {
      await dispatch(fetchCommentsByIds(updatedArticle.commentsIds));
    }
  };

  const handleAddComment = async () => {
    if (!article || !newComment.trim()) return;
    setCommentLoading(true);

    await dispatch(
      createComment({
        articleId: article.id,
        content: newComment.trim(),
      })
    );

    setNewComment("");
    await refreshComments(article.id);
    setCommentLoading(false);
  };

  const handleDeleteComment = async (commentId: number) => {
    if (!article) return;
    setCommentLoading(true);

    await dispatch(deleteComment(commentId));
    await refreshComments(article.id);
    setCommentLoading(false);
  };

  if (!article) return null;

  const formattedDate = new Date(article.createdAt).toLocaleDateString(
    "pl-PL",
    {
      day: "numeric",
      month: "long",
      year: "numeric",
    }
  );

  return (
    <>
      <p className="text-muted mb-2">
        Dodano: <strong>{formattedDate}</strong>
      </p>
      <p className="text-muted mb-2">
        Autor: <strong>{article.authorName}</strong>
      </p>
      <Badge bg="info" className="mb-3">
        {article.category}
      </Badge>

      <h5 className="mt-3">Opis</h5>
      <p className="text-secondary">{article.summary}</p>
      <hr />

      <h5 className="mt-4 mb-3">Treść artykułu</h5>
      <div style={{ lineHeight: "1.7", fontSize: "1rem" }}>
        <ReactMarkdown
          rehypePlugins={[rehypeHighlight]}
          components={{
            code: CodeBlock,
          }}
        >
          {article.content}
        </ReactMarkdown>
      </div>
      <hr className="my-4" />

      <h5 className="mb-3">💬 Komentarze ({comments.length})</h5>
      {(commentLoading || loading) && (
        <div className="d-flex justify-content-center my-2">
          <Spinner animation="border" size="sm" />
        </div>
      )}
      {!loading && !comments.length && (
        <p className="text-muted">Brak komentarzy.</p>
      )}
      {comments.map((comment) => (
        <CommentItem
          key={comment.id}
          id={comment.id}
          author={comment.authorUsername}
          createdAt={comment.createdAt}
          content={comment.content}
          canDelete={comment.authorUsername === loggedUsername}
          onDelete={handleDeleteComment}
        />
      ))}

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
    </>
  );
};

export default ArticleDetails;
