import axios from 'axios';

const instance = axios.create();

instance.interceptors.response.use((res) => {
    return res;
}, async (error) => {
    switch (error.response.status) {
        case 400:
            window.alert('잘못된 요청입니다.');
            break;
        case 401:
            window.alert('권한이 없습니다.\n로그인이 필요합니다.');
            window.location.replace('/');
            break;
        case 500:
            window.alert('서버에 문제가 발생했습니다.\n관리자에게 문의해주세요.');
            break;
    }
    return Promise.reject(error);
});

export default{
    get: (url: string, params?: any) => {
        return instance.get(url, { params });
    },
    post: (url: string, params?: any) => {
        return instance.post(url, params);
    },
    put: (url: string, params?: any) => {
        return instance.put(url, params);
    },
    delete: (url: string) => {
        return instance.delete(url);
    }
}