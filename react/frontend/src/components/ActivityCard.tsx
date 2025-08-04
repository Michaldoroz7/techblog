import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../app/store";
import { fetchActivity } from "../features/statisticsSlice";
import { Alert, Card, ListGroup, Spinner } from "react-bootstrap";

const getEmoji = (entity: string) => {
  switch (entity.toLowerCase()) {
    case "article":
      return "📝";
    case "comment":
      return "💬";
    case "user":
      return "👤";
    default:
      return "🔔";
  }
};

const ActivityCard = () => {
  const dispatch = useDispatch<AppDispatch>();
  const { activities, loading, error } = useSelector((state: RootState) => state.statistics);

  useEffect(() => {
    dispatch(fetchActivity());
  }, [dispatch]);

  if (loading) return <Spinner animation="border" />;
  if (error) return <Alert variant="danger">{error}</Alert>;

  return (
    <Card className="mt-3">
      <Card.Header>📋 Aktywność w systemie</Card.Header>
      <ListGroup variant="flush">
        {activities.length === 0 && (
          <ListGroup.Item>Brak zarejestrowanej aktywności.</ListGroup.Item>
        )}
        {activities.map((activity, index) => (
          <ListGroup.Item key={index} className="d-flex align-items-start">
            <span className="me-2" style={{ fontSize: "1.3rem" }}>{getEmoji(activity.entity)}</span>
            <div>
              <strong>{activity.username}</strong> {activity.action.toLowerCase()}{" "}
              {activity.entity.toLowerCase()} #{activity.entityId}
              <div className="text-muted" style={{ fontSize: "0.8rem" }}>
                {new Date(activity.timestamp).toLocaleString()}
              </div>
              <div>{activity.description}</div>
            </div>
          </ListGroup.Item>
        ))}
      </ListGroup>
    </Card>
  );
};

export default ActivityCard;
