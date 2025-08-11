import { FormEvent, useState } from "react";
import { Form, Button, Alert } from "react-bootstrap";
import { useDispatch } from "react-redux";
import { AppDispatch } from "../../app/store";
import { createArticle } from "../../features/articleSlice";


const AddArticleForm = ({ onSuccess }: { onSuccess: () => void }) => {
  const dispatch = useDispatch<AppDispatch>();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [summary, setSummary] = useState("");
  const [category, setCategory] = useState("");
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();
    try {
      await dispatch(
        createArticle({ title, summary, content, category })
      ).unwrap();
      onSuccess();
    } catch (err: any) {
      setError(err);
    }
  };

  const titleInput = (
    <Form.Group className="form-floating mb-3">
      <Form.Control
        type="text"
        id="title"
        placeholder="Tytuł"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        required
        className="rounded shadow-sm"
      />
      <Form.Label htmlFor="title">Tytuł</Form.Label>
    </Form.Group>
  );

  const summaryInput = (
    <Form.Group className="form-floating mb-3">
      <Form.Control
        type="text"
        id="summary"
        placeholder="Krótki opis"
        value={summary}
        onChange={(e) => setSummary(e.target.value)}
        required
        className="rounded shadow-sm"
      />
      <Form.Label htmlFor="summary">Krótki opis</Form.Label>
    </Form.Group>
  );

  const contentInput = (
    <Form.Group className="mb-3">
      <Form.Label>Treść</Form.Label>
      <Form.Control
        as="textarea"
        rows={6}
        value={content}
        onChange={(e) => setContent(e.target.value)}
        required
        className="rounded shadow-sm"
      />
    </Form.Group>
  );

  const categoryInput = (
    <Form.Group className="form-floating mb-4">
      <Form.Control
        type="text"
        id="category"
        placeholder="Kategoria"
        value={category}
        onChange={(e) => setCategory(e.target.value)}
        required
        className="rounded shadow-sm"
      />
      <Form.Label htmlFor="category">Kategoria</Form.Label>
    </Form.Group>
  );

  const errorAlert = error && <Alert variant="danger">{error}</Alert>;

  const submitButton = (
    <div className="d-grid">
      <Button type="submit" variant="primary" size="lg">
        💾 Zapisz artykuł
      </Button>
    </div>
  );

  return (
    <Form onSubmit={handleSubmit} className="p-2">
      {errorAlert}
      {titleInput}
      {summaryInput}
      {contentInput}
      {categoryInput}
      {submitButton}
    </Form>
  );
};

export default AddArticleForm;
