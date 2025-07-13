import { Navbar, Container, Button } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { logout } from "../features/auth/authSlice";
import { RootState } from "../app/store";
import { useNavigate } from "react-router-dom";

const Topbar = () => {
  const dispatch = useDispatch();
  const username = useSelector((state: RootState) => state.auth.username);
  const navigate = useNavigate();

  const handleLogout = () => {
    navigate("/")
    dispatch(logout());

  }

  return (
    <Navbar bg="light" expand="lg" className="shadow-sm px-4">
      <Container fluid>
        <Navbar.Brand className="fw-bold">Dashboard</Navbar.Brand>
        {username && (
          <div className="d-flex align-items-center gap-3 text-black">
            <span>
              Zalogowany jako: <strong>{username}</strong>
            </span>
            <Button variant="outline-dark" size="sm" onClick={handleLogout}>
              Wyloguj
            </Button>
          </div>
        )}
      </Container>
    </Navbar>
  );
};

export default Topbar;
