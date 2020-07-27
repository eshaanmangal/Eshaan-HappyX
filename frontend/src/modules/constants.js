const API_HOST_PATH = 'http://localhost:8090';

export const constants = {
    SUCCESS: 'success',
    FAILURE: 'failure',
    SERVICE_URLS: {
        LOGIN: `${API_HOST_PATH}/api/v1/generate-token`,
        GET_USERS: `${API_HOST_PATH}/api/v1/users`,
        GET_UERS_LIST: `${API_HOST_PATH}/api/v1/employee`,
        // SEND_EMAIL: `${API_HOST_PATH}/api/v1/sendmail`,
        SEND_EMAIL:`${API_HOST_PATH}/api/v1/postemail`,
        UPDATE_MOOD: `${API_HOST_PATH}/api/v1/moodcollector`,
        CHART: `${API_HOST_PATH}/api/v1/chart`,
        CHART_BY_DATE: `${API_HOST_PATH}/api/v1/chartByDate`,
        GET_DEPT_LOC_LIST: `${API_HOST_PATH}/api/v1/company/locOrDept`,
        DOWNLOAD_FILE: `${API_HOST_PATH}/api/v1/moodDownload`,
        GET_TEMPLATE_MOODS: `${API_HOST_PATH}/api/v1/TemplateMood`
    },
    NO_DATA_FOUND: 'No results found...',
    Template_OPTIONS: [
        {
            id: '1',
            value: 'Mail Template 1'
        },
        {
            id: '2',
            value: 'Mail Template 2'
        },
        {
            id: '3',
            value: 'Mail Template 3'
        }
    ],
    MONTHS: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"]
};

