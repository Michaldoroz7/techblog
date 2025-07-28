import React, { useEffect, useState } from "react";
import {
  Form,
  Button,
  Card,
  Container,
  Row,
  Col,
  Alert,
  Spinner,
} from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useAppDispatch, useAppSelector } from "../hooks";
import { login } from "../features/auth/authSlice";

const LoginPage: React.FC = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { loading, error, token, justLoggedIn } = useAppSelector(
    (state) => state.auth
  );

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  useEffect(() => {
    if (justLoggedIn) {
      const timer = setTimeout(() => {
        navigate('/dashboard');
      }, 1500); 
      return () => clearTimeout(timer);
    }
  }, [justLoggedIn, navigate]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    dispatch(login({ username, password }));
  };

  return (
    <Container className="d-flex justify-content-center align-items-center vh-100">
      <Card style={{ width: '24rem' }} className="shadow p-4">
        <h2 className="text-center mb-4">Zaloguj się</h2>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Nazwa Użytkownika</Form.Label>
            <Form.Control
              type="username"
              placeholder="Podaj nazwę użytkownika"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </Form.Group>

          <Form.Group className="mb-4">
            <Form.Label>Hasło</Form.Label>
            <Form.Control
              type="password"
              placeholder="Podaj hasło"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </Form.Group>

          <Button type="submit" variant="primary" className="w-100" disabled={loading}>
            {loading ? <Spinner size="sm" animation="border" /> : 'Zaloguj się'}
          </Button>
        </Form>

        {justLoggedIn && (
          <Alert variant="success" className="mt-3 text-center">
            Zalogowano pomyślnie! Przekierowuję...
          </Alert>
        )}

        {error && (
          <Alert variant="danger" className="mt-3 text-center">
            {error}
          </Alert>
        )}

        <div className="text-center mt-3">
          <span>Nie masz konta?</span>{' '}
          <Button
            variant="link"
            onClick={() => navigate('/register')}
            style={{ padding: 0, fontWeight: 'bold' }}
          >
            Zarejestruj się
          </Button>
        </div>
      </Card>
    </Container>
  );
};

export default LoginPage;
