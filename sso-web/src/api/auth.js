import request from "@/utils/request";

export function login(params) {
    return request.post(`/auth/login`,params);
}

export function logout() {
    return request.post(`/auth/logout`);
}