import { Col, Container, Row } from "react-bootstrap";
import UserInfoCard from "../components/UserInfoCard";
import UserArticlesCard from "../components/UserArticlesCard";

const UserProfilePage = () => {
  return (
    <Container>
      <Row>
        <Col md={8}>
          <UserInfoCard />
        </Col>
        <Col md={4}>
          <UserInfoCard />
        </Col>
      </Row>
      <Row>
        <Col md={12}>
          <UserArticlesCard />
        </Col>
      </Row>
    </Container>
  );
};

export default UserProfilePage;
