import { http } from "../http";
import { LOCAL_STORAGE_KEYS, localStorageService } from "../local-storage";

export const login = async ({ email, password }) => {
    const params = new URLSearchParams();
    params.append('email', email);
    params.append('password', password);

    const instance = http();
    instance.defaults.headers['Content-Type'] = 'application/x-www-form-urlencoded';

    const response = await instance.post('/auth', params);

    const accessToken = response.data['access_token'];
    const refreshToken = response.data['refresh_token'];
    localStorageService.setItem(LOCAL_STORAGE_KEYS.AUTH, { accessToken, refreshToken });
    return { status: response.status };
};
