import React from "react";
import logo from "./logo.svg";
import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import DashboardLayout from "./layout/DashboardLayout";
import DashboardHome from "./pages/DashboardHome";
import ProtectedRoute from "./routes/ProtectedRoute";
import UserProfile from "./pages/UserProfilePage";
import ArticlesPage from "./pages/ArticlesPage";
import StatisticsPage from "./pages/StatisticsPage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <DashboardLayout />
            </ProtectedRoute>
          }
        >
          <Route index element={<DashboardHome />} />
          <Route path="profile" element={<UserProfile />} />
          <Route path="articles" element={<ArticlesPage />} />
          <Route path="statistics" element={<StatisticsPage />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
