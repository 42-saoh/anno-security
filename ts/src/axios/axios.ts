import axios, { AxiosRequestConfig } from 'axios' // 추가
import { CustomAxiosInterface } from './axiosInterface'
import { CommonResponse } from './commonResponse'

const client: CustomAxiosInterface = axios.create({
    baseURL: 'http://localhost:8080/',
})

type UserDTO = {
  name: String,
  email: String,
  naverId: String,
  roles: String,

}

type AuthDTO = {
  code: String,
  state: String,
}

export const Get = async <T>(
  url: string,
  config?: AxiosRequestConfig
): Promise<CommonResponse<T>> => {
  const response = await client.get<CommonResponse<T>>(url, config)
  return response
}

export const Put = async <T>(
  url: string,
  data?: T,
  config?: AxiosRequestConfig
): Promise<CommonResponse<T>> => {
  const response = await client.put<CommonResponse<T>>(url, data, config)
  return response
}

export const Post = async <T>(
  url: string,
  data: UserDTO,
  config?: AxiosRequestConfig
): Promise<CommonResponse<T>> => {
  const response = await client.post<CommonResponse<T>>(url, data, config)
  return response
}

export const AuthPost = async <T>(
  url: string,
  data: AuthDTO,
  config?: AxiosRequestConfig
): Promise<CommonResponse<T>> => {
  const response = await client.post<CommonResponse<T>>(url, data, config)
  return response
}
