import axios from 'axios'

const instance = axios.create({
    baseURL: "/sso",
    timeout: 20000,
    withCredentials: true,
})

instance.interceptors.request.use(
    config => {
        config.headers.Authorization = 'Bearer ' + (localStorage.getItem("token") || '');
        return config
    },
    error => {
        return Promise.reject(error)
    },
)

instance.interceptors.response.use(
    response => {
        const res = response.data

        // 如果自定义code不为0，则将其判断为错误。
        if (res.code !== undefined && res.code !== 200) {
            if (res.code === 403 || res.code === 401 || res.code === 404) {
                // TODO
            }
            return Promise.reject(new Error(res.message || 'System Error'))
        }

        return response
    },
    error => {
        return error
    },
)
export default instance
