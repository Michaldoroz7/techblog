import React from "react";
import { Card, Spinner } from "react-bootstrap";
import { useSelector } from "react-redux";
import { RootState } from "../app/store";

const QuoteCard = () => {
  const { quote, loading, error } = useSelector(
    (state: RootState) => state.quote
  );

  return (
    <Card className="mb-4 shadow-sm">
      <Card.Body>
        <Card.Title className="mb-3">💡 Cytat programistyczny dnia</Card.Title>
        {loading ? (
          <Spinner animation="border" />
        ) : error ? (
          <p className="text-danger">{error}</p>
        ) : (
          <>
            <blockquote className="blockquote mb-3">
              <p className="mb-2" style={{ fontStyle: "italic" }}>
                "{quote?.content}"
              </p>
              <footer className="blockquote-footer">{quote?.author}</footer>
            </blockquote>
          </>
        )}
      </Card.Body>
    </Card>
  );
};

export default QuoteCard;
