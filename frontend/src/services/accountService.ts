import httpRequester from "@/libs/httpRequester";

export const join = (args: any) => {
  return httpRequester.post("v1/api/account/join", args).catch((err) => { err.response });
}

export const login = (args: any) => {
  return httpRequester.post("v1/api/account/login", args).catch((err) => { err.response });
}

export const check = () => {
  return httpRequester.get("/v1/api/account/check").catch((err) => { err.response });
}

export const logout = () => {
  return httpRequester.post("v1/api/account/logout").catch((err) => { err.response });
}