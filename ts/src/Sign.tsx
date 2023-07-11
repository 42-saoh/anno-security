import React from 'react';
import { Container, Overlay, OverlayPanel, GhostButton, Wrapper, Form } from './style/AppStyle';
import { Post } from './axios/axios';
import { useNavigate, useLocation } from 'react-router-dom';
import { CommonResponse } from './axios/commonResponse';

export const Sign = () => {
  const location = useLocation();
  const data = location.state?.data;
  const navigate = useNavigate();

  const postAxios = async () => {
    const token = localStorage.getItem('token');
    localStorage.removeItem('token');
    const response = await Post(
      '/sign',
      {
        name: data.name,
        email: data.email,
        naverId: data.naverId,
        roles: 'ROLE_USER'
      },
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    )
    return response;
  }

  const handleClick = (e : any) => {
    e.preventDefault();
    postAxios().then((res : CommonResponse<any>) => {
      if (res.status === 200) {
        localStorage.setItem('token', res.data.token)
        navigate('/main')
      } else {
        alert(res.status + ' ' + res.statusText)
      }
    }).catch((err) => {
      alert(err)
    })
  }

  return (
    <Wrapper>
      <Container>
        <Overlay>
          <OverlayPanel>
            <Form>
              <input type="text" id="name" name="name" value={data.name} readOnly />
              <input type="text" id="email" name="email" value={data.email} readOnly />
              <input type="text" id="naverId" name="naverId" value={data.naverId} readOnly />
              <GhostButton onClick={handleClick}>Sign Up</GhostButton>
            </Form>
          </OverlayPanel>
        </Overlay>
      </Container>
    </Wrapper>
  )
}