import axios from 'axios';

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
})

export const getSchedules = async () => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/schedules`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}
export const saveSchedules  = async (user) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/schedules`,
            user)
    } catch (e) {
        throw e;
    }
}
export const updateSchedules  = async (id, update) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/schedules`,
            update,
            getAuthConfig())
    } catch (e) {
        throw e;
    }
}


export const deleteSchedules  = async (id) => {
    try {
        return await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/schedules/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}