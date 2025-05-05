import axios from "axios";

export const getItems = async () => {
  return axios.get("/v1/api/items").catch((error) => error.response);
};