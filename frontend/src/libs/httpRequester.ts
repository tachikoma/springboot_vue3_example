import axios from 'axios';
import { useAccountStore } from '@/stores/account';

const instance = axios.create();

instance.interceptors.response.use((res) => {
    return res;
}, async (error) => {
    switch (error.response.status) {
        case 400:
            window.alert('잘못된 요청입니다.');
            break;
        case 401:
            const config = error.config;
            if (config.retried) {
                window.alert('권한이 없습니다.\n로그인이 필요합니다.');
                window.location.replace('/');
                return;
            }
            const res = await axios.get('/v1/api/account/token');
            const accessToken = res.data;
            const accountStore = useAccountStore();
            accountStore.setAccessToken(accessToken);
            config.headers['Authorization'] = `Bearer ${accountStore.accessToken}`;
            config.retried = true;
            return instance(config);
        case 500:
            window.alert('서버에 문제가 발생했습니다.\n관리자에게 문의해주세요.');
            Promise.reject(error);
    }
    return Promise.resolve(error.response);
});

const generateConfig = (): { headers?: { authorization: string }; params?: any } => {
    const accountStore = useAccountStore();
    if (accountStore.accessToken) {
        return {
            headers: {
                authorization: `Bearer ${accountStore.accessToken}`
            }
        };
    }
    return {};
};

export default{
    get: (url: string, params?: any) => {
        const config = generateConfig();
        config.params = params;
        return instance.get(url, config);
    },
    post: (url: string, params?: any) => {
        return instance.post(url, params, generateConfig());
    },
    put: (url: string, params?: any) => {
        return instance.put(url, params, generateConfig());
    },
    delete: (url: string) => {
        return instance.delete(url, generateConfig());
    }
}