import httpRequester from "@/libs/httpRequester";

// 주문 생성
export const createOrder = (args: {}) => {
    return httpRequester.post("/v1/api/orders", args).catch((e => e.response));
};

// 주문 목록 조회
export const getOrders = (args: {}) => {
    return httpRequester.get("/v1/api/orders", args).catch((e => e.response));
};

// 주문 상세 조회
export const getOrder = (orderId: string) => {
    return httpRequester.get(`/v1/api/orders/${orderId}`).catch((e => e.response));
};