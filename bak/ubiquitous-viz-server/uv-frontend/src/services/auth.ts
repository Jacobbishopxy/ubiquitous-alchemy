import axios from "axios"

// send an invitation to user email, param includes user email, nickname and password
export const sendInvitation = async (param: API.Invitation): Promise<void> => {
    return axios.post("/api/auth/invitation", param)
}

// confirm registration
export const register = async (id: string): Promise<void> => {
    return axios.get(`/api/auth/register/${id}`)
}

// user login
export const login = async (param: API.Login): Promise<void> => {
    return axios.post("/api/auth", param, {withCredentials: true})
}

// check user login status
export const check = async (): Promise<API.LoginCheck> => {
    return axios.get("/api/auth", {withCredentials: true})
}

// user logout
export const logout = async (): Promise<void> => {
    return axios.delete("/api/auth", {withCredentials: true})
}
