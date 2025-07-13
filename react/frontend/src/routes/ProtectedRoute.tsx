import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";
import React, { useEffect, useState } from "react";
import { Alert, Container } from "react-bootstrap";
import { RootState } from "../app/store";

interface Props {
  children: React.ReactNode;
}

const ProtectedRoute = ({ children }: Props) => {
  const token = useSelector((state: RootState) => state.auth.token);
  const [showAlert, setShowAlert] = useState(false);
  const [redirect, setRedirect] = useState(false);

  useEffect(() => {
    if (!token) {
      setShowAlert(true);
      const timer = setTimeout(() => setRedirect(true), 2000); // po 2s przekieruj
      return () => clearTimeout(timer);
    }
  }, [token]);

  if (!token) {
    if (redirect) {
      return <Navigate to="/" replace />;
    }

    return (
      <Container className="mt-5">
        <Alert variant="warning">
          Musisz być zalogowany, aby uzyskać dostęp do tej strony.
        </Alert>
      </Container>
    );
  }

  return <>{children}</>;
};

export default ProtectedRoute;
