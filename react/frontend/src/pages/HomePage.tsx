import React from 'react';
import { Container, Button, Row, Col, Image } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const HomePage: React.FC = () => {
  const navigate = useNavigate();

  return (
    <Container
      fluid
      className="d-flex flex-column justify-content-center align-items-center text-center vh-100 bg-light"
    >
      <Row>
        <Col>
          <Image
            src="/logo192.png"
            roundedCircle
            width={120}
            height={120}
            alt="TechBlog Logo"
            className="mb-4"
          />
          <h1 className="display-4 fw-bold">TechBlog</h1>
          <p className="lead mb-4">
            Najnowsze artykuły, komentarze i dyskusje ze świata technologii.
          </p>
          <div>
            <Button
              variant="primary"
              size="lg"
              className="me-3"
              onClick={() => navigate('/login')}
            >
              Zaloguj się
            </Button>
            <Button
              variant="outline-primary"
              size="lg"
              onClick={() => navigate('/register')}
            >
              Zarejestruj się
            </Button>
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default HomePage;
