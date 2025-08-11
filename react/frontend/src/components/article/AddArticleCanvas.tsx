import { Button, Offcanvas } from "react-bootstrap";
import { useState } from "react";
import AddArticleForm from "./AddArticleForm";

const AddArticleOffcanvas = () => {
  const [show, setShow] = useState(false);

  const handleToggle = () => setShow((prev) => !prev);

  return (
    <>
      <Button onClick={handleToggle} className="mb-3" variant="success">
        ➕ Dodaj artykuł
      </Button>

      <Offcanvas show={show} onHide={handleToggle} placement="end" className="w-50">
        <Offcanvas.Header closeButton>
          <Offcanvas.Title>📝 Nowy artykuł</Offcanvas.Title>
        </Offcanvas.Header>
        <Offcanvas.Body className="bg-light">
          <AddArticleForm onSuccess={handleToggle} />
        </Offcanvas.Body>
      </Offcanvas>
    </>
  );
};

export default AddArticleOffcanvas;
