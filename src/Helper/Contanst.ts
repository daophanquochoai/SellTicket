
export const env = {
    url : {
        API_BASE_URL: import.meta.env.VITE_REACT_APP_API_BASE_URL,
    }
}
export const PUBLIC_KEY = import.meta.env.VITE_REACT_APP_PUBLIC_KEY;
console.log("API_BASE_URL:", env.url.API_BASE_URL);
console.log("PUBLIC_KEY:", PUBLIC_KEY);
