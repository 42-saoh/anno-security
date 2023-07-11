import React, { useEffect, useState } from 'react';
import { Container, Overlay, OverlayPanel, Wrapper, Form } from './style/AppStyle';
import { Get } from './axios/axios';
import { CommonResponse } from './axios/commonResponse';

type User = {
  name: string,
  email: string,
  naverId: string
}

export const Main = () => {
  const token = localStorage.getItem('token')
  const [user, setUser] = useState<User>()

  const getAxios = async () => {    
    const response = await Get(
      '/me',
      {
        headers: {
          Authorization: `Bearer ${token}`
        }
      }
    )
    return response
  }

  useEffect(() => {
    getAxios().then((res : CommonResponse<any>) => {
      if (res.status === 200) {
        setUser(res.data)
      } else {
        alert(res.status + ' ' + res.statusText)
      }
    }).catch((err) => {
      alert(err)
    })
  }, [])
  
  return (
  <Wrapper>
    <Container>
      <Overlay>
        <OverlayPanel>
          <Form>
            <input type="text" id="name" name="name" value={user?.name} readOnly />
            <input type="text" id="email" name="email" value={user?.email} readOnly />
            <input type="text" id="naverId" name="naverId" value={user?.naverId} readOnly />
          </Form>
        </OverlayPanel>
      </Overlay>
    </Container>
  </Wrapper>
  )
}