import axios from "axios";
export const join = (args: any) => {
  return axios.post("v1/api/account/join", args).catch((err) => { err.response});
}

export const login = (args: any) => {
  return axios.post("v1/api/account/login", args).catch((err) => { err.response});
}

export const check = () => {
  return axios.get("/v1/api/account/check").catch((err) => { err.response});
}

export const logout = () => {
  return axios.post("v1/api/account/logout").catch((err) => { err.response});
}