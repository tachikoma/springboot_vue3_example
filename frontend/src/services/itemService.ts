import httpRequester from "@/libs/httpRequester";

export const getItems = async () => {
  return httpRequester.get("/v1/api/items").catch((error) => error.response);
};