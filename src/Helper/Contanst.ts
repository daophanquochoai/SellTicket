
export const env = {
    url : {
        API_BASE_URL: import.meta.env.VITE_REACT_APP_API_BASE_URL,
        OAUTH2_REDIRECT_URI:  import.meta.env.VITE_REACT_APP_OAUTH2_REDIRECT_URI
    }
}
export const PUBLIC_KEY = import.meta.env.VITE_REACT_APP_PUBLIC_KEY;
