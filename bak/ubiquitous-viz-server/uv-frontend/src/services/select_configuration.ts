import axios from "axios"

// select data from database with conditions
export const select = async (id: string, selection: API.Selection): Promise<any[]> => {
    return axios.post(`/api/query/select?db_id=${id}`, selection)
}
