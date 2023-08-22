import axios from 'axios';
import provider from "../Provider.jsx";

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
})

export const getProviders = async () => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/providers`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}
export const saveProvider = async (provider) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/providers`,
            provider)
    } catch (e) {
        throw e;
    }
}
export const updateProvider = async (id, update) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/providers`,
            update,
            getAuthConfig())
    } catch (e) {
        throw e;
    }
}


export const deleteProvider = async (id) => {
    try {
        return await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/providers/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const login = async (providernameAndPassword) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`,
            providernameAndPassword
        )
    } catch (e) {
        throw e;
    }
}