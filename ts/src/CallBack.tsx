import React, { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { AuthPost } from './axios/axios';
import { CommonResponse } from './axios/commonResponse';

export const CallBack = () => {
  const location = useLocation();
  const navigate = useNavigate();

  const authAxios = async (code : string, state : string) => {    
    const response = await AuthPost(
      '/oauth2',
      {
        code: code,
        state: state,
      }
    )
    return response
  }

  useEffect(() => {
    const query = new URLSearchParams(location.search)
    const code = query.get('code')
    const state = query.get('state')

    if (!code || !state) {
      console.error('Code or state parameter is missing')
      return
    }

    authAxios(code, state).then((res : CommonResponse<any>) => {
      if (res.status === 200) {
        const isNew = res.data.new
        localStorage.setItem('token', res.data.token)
        if (isNew) {
          navigate('/sign', { state: { data: res.data } })
        } else {
          navigate('/main')
        }
      } else {
        alert(res.status + ' ' + res.statusText)
      }
    }).catch((err) => {
      alert(err)
    })
  }, [])
  
  return null
}