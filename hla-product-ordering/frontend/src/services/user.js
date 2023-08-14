import axios from 'axios';

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
})

export const getUsers = async () => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/users`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}
export const saveUser = async (user) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/users`,
            user)
    } catch (e) {
        throw e;
    }
}
export const updateUser = async (id, update) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/users/${id}`,
            update,
            getAuthConfig())
    } catch (e) {
        throw e;
    }
}


export const deleteUser = async (id) => {
    try {
        return await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/users/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const login = async (usernameAndPassword) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`,
            usernameAndPassword
        )
    } catch (e) {
        throw e;
    }
}