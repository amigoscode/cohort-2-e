import axios from 'axios';

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
})

export const getUsers = async (name='',currentPage=0, itemsPerPage=5) => {
    try {
        const token = getAuthConfig()
        console.log(token)
        const url = `${import.meta.env.VITE_API_BASE_URL}/api/v1/users?name=${name}&page=${currentPage}&size=${itemsPerPage}`
        console.log('url: ', url)
        return await axios.get(
            url,
            token
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
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/users`,
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