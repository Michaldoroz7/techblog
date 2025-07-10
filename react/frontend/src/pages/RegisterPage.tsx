import React, { useEffect, useState } from 'react';
import { Alert, Button, Card, Container, Form, Spinner } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { useAppDispatch, useAppSelector } from '../hooks';
import { register } from '../features/auth/authSlice';

const RegisterPage: React.FC = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();
  const { loading, error, registered } = useAppSelector((state) => state.auth);

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [repeatPassword, setRepeatPassword] = useState('');
  const [username, setUsername] = useState('');

  useEffect(() => {
    if (registered) {
      const timer = setTimeout(() => navigate('/login'), 2000);
      return () => clearTimeout(timer);
    }
  }, [registered, navigate]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (password !== repeatPassword) return;
    dispatch(register({ username, email, password }));
  };

  return (
    <Container className="d-flex justify-content-center align-items-center vh-100">
      <Card style={{ width: '26rem' }} className="shadow p-4">
        <h2 className="text-center mb-4">Zarejestruj się</h2>
        <Form onSubmit={handleSubmit}>

            <Form.Group className="mb-3">
            <Form.Label>Nazwa Użytkownika</Form.Label>
            <Form.Control
              type="username"
              placeholder="wpisz nazwę użytkownika"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </Form.Group>
          <Form.Group className="mb-3">
            <Form.Label>Adres e-mail</Form.Label>
            <Form.Control
              type="email"
              placeholder="Wpisz e-mail"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Hasło</Form.Label>
            <Form.Control
              type="password"
              placeholder="Wpisz hasło"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </Form.Group>

          <Form.Group className="mb-4">
            <Form.Label>Powtórz hasło</Form.Label>
            <Form.Control
              type="password"
              placeholder="Powtórz hasło"
              value={repeatPassword}
              onChange={(e) => setRepeatPassword(e.target.value)}
              required
              isInvalid={password !== repeatPassword}
            />
            <Form.Control.Feedback type="invalid">Hasła się różnią.</Form.Control.Feedback>
          </Form.Group>

          <Button type="submit" variant="success" className="w-100" disabled={loading || password !== repeatPassword}>
            {loading ? <Spinner size="sm" animation="border" /> : 'Zarejestruj się'}
          </Button>
        </Form>

        {registered && (
          <Alert variant="success" className="mt-3 text-center">
            Rejestracja zakończona sukcesem! Przekierowanie...
          </Alert>
        )}

        {error && (
          <Alert variant="danger" className="mt-3 text-center">
            {error}
          </Alert>
        )}

        <div className="text-center mt-3">
          <span>Masz już konto?</span>{' '}
          <Button
            variant="link"
            onClick={() => navigate('/login')}
            style={{ padding: 0, fontWeight: 'bold' }}
          >
            Zaloguj się
          </Button>
        </div>
      </Card>
    </Container>
  );
};

export default RegisterPage;
