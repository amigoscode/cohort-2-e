import axios from 'axios';

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
})

export const getEmails = async () => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/emails`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}
export const sendEmail = async (email) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/emails`,
            email,
            getAuthConfig())
    } catch (e) {
        throw e;
    }
}

