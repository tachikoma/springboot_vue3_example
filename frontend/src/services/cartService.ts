import axios from "axios";

export const getItems = () => {
    return axios.get("/v1/api/cart/items").catch((e => e.response));
};

export const addItem = (itemId: any) => {
    return axios.post("/v1/api/carts", { itemId } ).catch((e => e.response));
};

export const removeItem = (itemId: any) => {
    return axios.delete(`/v1/api/cart/items/${itemId}`).catch((e => e.response));
};
