import axios from 'axios';

const getAuthConfig = () => ({
    headers: {
        Authorization: `Bearer ${localStorage.getItem("access_token")}`
    }
})

export const getPatients = async (currentPage=0,itemsPerPage=5) => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/patients?page=${currentPage}&size=${itemsPerPage}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}

export const getPatient = async (id) => {
    try {
        return await axios.get(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/patients/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}