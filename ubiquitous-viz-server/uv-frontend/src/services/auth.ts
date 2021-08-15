import axios from "axios"

export const sendInvitation = async (param: API.Invitation): Promise<void> => {
    return axios.post("/api/auth/invitation", param)
}

export const register = async (id: string, param: API.Registration): Promise<void> => {
    return axios.post(`/api/auth/register/${id}`, param)
}

export const login = async (param: API.Login): Promise<void> => {
    return axios.post("/api/auth", param, {withCredentials: true})
}

export const check = async (): Promise<API.LoginCheck> => {
    return axios.get("/api/auth", {withCredentials: true})
}

export const logout = async (): Promise<void> => {
    return axios.delete("/api/auth", {withCredentials: true})
}
