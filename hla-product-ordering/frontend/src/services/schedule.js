import axios from 'axios';

axios.defaults.headers.common['Content-Type'] = 'application/json';


const getAuthConfig = () => ({
    headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem("access_token")}`
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
export const saveSchedule = async (user) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/schedules`,
            user)
    } catch (e) {
        throw e;
    }
}
export const updateSchedule = async (id, update) => {
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/schedules`,
            update,
            getAuthConfig())
    } catch (e) {
        throw e;
    }
}

/*export const updateSchedule = async (id, update) => {
    try {
        const response = await axios.put(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/schedules`,
            JSON.stringify(update), // Stringify the JSON data
            {
                headers: {
                    'Content-Type': 'application/json',
                },
                ...getAuthConfig(),
            }
        );

        return response.data; // Return the response data
    } catch (e) {
        throw e;
    }
};*/


export const deleteSchedule = async (id) => {
    try {
        return await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/schedules/${id}`,
            getAuthConfig()
        )
    } catch (e) {
        throw e;
    }
}