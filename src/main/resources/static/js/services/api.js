const API_BASE = 'http://localhost:8080/api';

const api = {
    // Account
    getAccountBalance: () => axios.get(`${API_BASE}/account/balance`),

    // Towns
    getTowns: () => axios.get(`${API_BASE}/towns`),
    searchTowns: (query) => axios.get(`${API_BASE}/towns/search?q=${query}`),

    // Keywords
    getKeywords: () => axios.get(`${API_BASE}/keywords`),
    searchKeywords: (query) => axios.get(`${API_BASE}/keywords/search?q=${query}`),

    // Campaigns
    getCampaigns: () => axios.get(`${API_BASE}/campaigns`),
    getCampaign: (id) => axios.get(`${API_BASE}/campaigns/${id}`),
    createCampaign: (data) => axios.post(`${API_BASE}/campaigns`, data),
    updateCampaign: (id, data) => axios.put(`${API_BASE}/campaigns/${id}`, data),
    deleteCampaign: (id) => axios.delete(`${API_BASE}/campaigns/${id}`)
};

axios.interceptors.response.use(
    response => response,
    error => {
        if (error.response) {
            console.error('API Error:', error.response.data);
        } else {
            console.error('API Error:', error.message);
        }
        return Promise.reject(error);
    }
);