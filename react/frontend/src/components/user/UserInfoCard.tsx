import { useEffect } from "react";
import { Alert, Card, Spinner } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../../app/store";
import { fetchUserProfile } from "../../features/auth/userSlice";

const UserInfoCard = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { profile, loading, error } = useSelector(
    (state: RootState) => state.user
  );

  useEffect(() => {
    dispatch(fetchUserProfile());
  }, [dispatch]);

  if (loading) return <Spinner animation="border" />;
  if (error) return <Alert variant="danger">{error}</Alert>;
  if (!profile) return null;

  return (
    <>
      <Card>
        <Card.Header>Profil użytkownika</Card.Header>
        <Card.Body>
          <p>
            <strong>Nazwa użytkownika:</strong> {profile.username}
          </p>
          <p>
            <strong>Email:</strong> {profile.email}
          </p>
          <p>
            <strong>Rola:</strong> {profile.role}
          </p>
          <p>
            <strong>Dołączono:</strong>{" "}
            {new Date(profile.createdAt).toLocaleString()}
          </p>
        </Card.Body>
      </Card>
    </>
  );
};

export default UserInfoCard;
