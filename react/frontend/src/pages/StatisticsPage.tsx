import { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "../app/store";
import { fetchStatistics } from "../features/statisticsSlice";
import {
  Alert,
  Card,
  Spinner,
  Container,
  Row,
  Col,
  Badge,
} from "react-bootstrap";
import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  BarElement,
  CategoryScale,
  LinearScale,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(BarElement, CategoryScale, LinearScale, Tooltip, Legend);

const StatisticsPage = () => {
  const dispatch = useDispatch<AppDispatch>();
  const {
    totalArticles,
    totalComments,
    totalUsers,
    lastArticle,
    lastComment,
    lastUser,
    loading,
    error,
  } = useSelector((state: RootState) => state.statistics);

  useEffect(() => {
    dispatch(fetchStatistics());
  }, [dispatch]);

  if (loading) return <Spinner animation="border" />;
  if (error) return <Alert variant="danger">{error}</Alert>;

  const chartData = {
  labels: ["Artykuły", "Komentarze", "Użytkownicy"],
  datasets: [
    {
      data: [totalArticles, totalComments, totalUsers],
      backgroundColor: ["#0d6efd", "#fd7e14", "#198754"],
    },
  ],
};

  console.log(chartData);

  const chartOptions = {
    responsive: true,
    plugins: {
      legend: {
        display: false,
      },
    },
    scales: {
      y: {
        beginAtZero: true,
        ticks: {
          stepSize: 1,
        },
      },
    },
  };

  return (
    <Container fluid className="py-4">
      <h2 className="mb-4">
        📊 Statystyki systemu <Badge bg="secondary">Aktualne</Badge>
      </h2>

      <Row className="mb-4">
        <Col>
          <Card className="shadow-sm border-0">
            <Card.Header className="bg-white fw-bold">Podsumowanie</Card.Header>
            <Card.Body>
              <Bar data={chartData} options={chartOptions} />
            </Card.Body>
          </Card>
        </Col>
      </Row>

      <Row xs={1} md={3} className="g-4">
        <Col>
          <Card className="border-0 shadow-sm h-100">
            <Card.Header className="bg-light fw-bold">
              📰 Ostatni artykuł
            </Card.Header>
            <Card.Body>
              <p>{lastArticle.title}</p>
              <p className="text-muted small">
                Dodano: {new Date(lastArticle.createdAt).toLocaleString()}
              </p>
            </Card.Body>
          </Card>
        </Col>

        <Col>
          <Card className="border-0 shadow-sm h-100">
            <Card.Header className="bg-light fw-bold">
              💬 Ostatni komentarz
            </Card.Header>
            <Card.Body>
              <p>{lastComment.content}</p>
              <p className="text-muted small">
                Dodano: {new Date(lastComment.createdAt).toLocaleString()}
              </p>
            </Card.Body>
          </Card>
        </Col>

        <Col>
          <Card className="border-0 shadow-sm h-100">
            <Card.Header className="bg-light fw-bold">
              👤 Ostatni użytkownik
            </Card.Header>
            <Card.Body>
              <p>{lastUser.username}</p>
              <p className="text-muted small">
                Dołączono: {new Date(lastUser.createdAt).toLocaleString()}
              </p>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default StatisticsPage;
