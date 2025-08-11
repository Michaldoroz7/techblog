import { Col, Container, Row } from "react-bootstrap";
import MyArticlesList from "../components/user/MyArticlesList";
import UserInfoCard from "../components/user/UserInfoCard";

const UserProfilePage = () => {
  return (
    <Container>
      <Row className="my-4">
        <Col>
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
