import request from "@/utils/request";

export function hello() {
    return request.get(`/test/hello`);
}