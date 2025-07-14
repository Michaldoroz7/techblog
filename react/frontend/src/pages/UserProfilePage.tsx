import { Col, Container, Row } from "react-bootstrap";
import UserInfoCard from "../components/UserInfoCard";
import MyArticlesList from "../components/MyArticlesList";

const UserProfilePage = () => {
  return (
    <Container>
      <Row className="my-4">
        <Col md={8}>
          <UserInfoCard />
        </Col>
        <Col md={4}>
          <UserInfoCard />
        </Col>
      </Row>
      <Row className="my-4">
        <Col md={12}>
          <MyArticlesList />
        </Col>
      </Row>
    </Container>
  );
};

export default UserProfilePage;
