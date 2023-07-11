import React from 'react';
import { Container, Overlay, OverlayPanel, GhostButton, Wrapper } from './style/AppStyle';
import { Link } from 'react-router-dom';
import { Row, Col } from 'react-bootstrap';

export const App = () => {
  return (
    <Wrapper>
      <Container>
        <Overlay>
          <OverlayPanel>
            <Row>
              <Col>
                <Link to="/login">
                  <GhostButton>Log in</GhostButton>
                </Link>
              </Col>
            </Row>
          </OverlayPanel>
        </Overlay>
      </Container>
    </Wrapper>
  )
}