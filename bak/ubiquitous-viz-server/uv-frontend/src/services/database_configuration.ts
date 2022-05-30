import axios from "axios"

// check whether database connection is valid
export const checkConnection = async (param: API.ConnInfo): Promise<boolean> => {
    return axios.post("/api/cfg/check_connection", param).then((res: {data: boolean}) => res.data)
}

// list all current connections in pool
export const listConn = async (): Promise<API.ConnInfo[]> => {
    return axios.get("/api/cfg/conn").then((res: {data: API.ConnInfo[]}) => res.data)
}

// create a new database connection
export const createConn = async (param: API.ConnInfo): Promise<void> => {
    return axios.post("/api/cfg/conn", param)
}

// update an existing connection
export const updateConn = async (id: string, param: API.ConnInfo): Promise<void> => {
    return axios.put(`/api/cfg/conn?db_id=${id}`, param)
}

// remove and disconnect from a database
export const deleteConn = async (id: string): Promise<void> => {
    return axios.delete(`/api/cfg/conn?db_id=${id}`)
}
