import Cookies from 'js-cookie'

const TokenKey = 'token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}
export function loginEmp() {
  let str = Cookies.get("employee")
  return JSON.parse(str)
}
export function clearCookie(name) {
  Cookies.remove(name)
}
