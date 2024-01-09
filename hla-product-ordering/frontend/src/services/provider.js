import axios from 'axios';

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
})

export const getProviders = async (name="",currentPage=1, itemsPerPage=1) => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/providers?name=${name}&page=${currentPage}&size=${itemsPerPage}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}
export const saveProvider = async (provider) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/providers`,
            provider,
            getAuthConfig())
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