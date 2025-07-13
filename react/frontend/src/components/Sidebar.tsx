import { NavLink } from "react-router-dom";
import { Nav } from "react-bootstrap";

const Sidebar = () => {
  return (
    <>
      <h4 className="mb-4">🚀 TechBlog</h4>
      <Nav className="flex-column">
        <NavLink to="/dashboard" className="nav-link text-white">
          🏠 Główna
        </NavLink>
        <NavLink to="/dashboard/articles" className="nav-link text-white">
          📰 Artykuły
        </NavLink>
        <NavLink to="/dashboard/comments" className="nav-link text-white">
          💬 Komentarze
        </NavLink>
        <NavLink to="/dashboard/profile" className="nav-link text-white">
          💬 Mój Profil
        </NavLink>
      </Nav>
    </>
  );
};

export default Sidebar;
