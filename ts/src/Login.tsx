import React from 'react';
import { Container, Overlay, OverlayPanel, GhostButton, Wrapper, Form } from './style/AppStyle';

export const Login = () => {
  const clinetId = '&client_id=JHVVZpQBC_FzZWy_W5VO'
  const redirectURI = '&redirect_uri=http://localhost:3000/callback'
  const state = '&state=' + Math.random().toString(36).substr(2, 11);
  const api = 'https://nid.naver.com/oauth2.0/authorize?response_type=code' + clinetId + redirectURI + state;
  

  const handleClick = (e : any) => {
    e.preventDefault();
    window.location.href = api;
  }

  return (
    <Wrapper>
      <Container>
        <Overlay>
          <OverlayPanel>
            <Form>
              <GhostButton onClick={handleClick}>Login</GhostButton>
            </Form>
          </OverlayPanel>
        </Overlay>
      </Container>
    </Wrapper>
  )
}