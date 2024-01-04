const API_BASE_URL = process.env.REACT_APP_RESERVATION_API_URL ||'http://localhost:8080/';

console.log("env var: REACT_APP_RESERVATION_API_URL = " + process.env.REACT_APP_RESERVATION_API_URL);
console.log("global var: API_BASE_URL = " + API_BASE_URL);
const PAYMENT_API_BASE_URL = process.env.REACT_APP_PAYMENT_API_URL ||'http://localhost:8095/';

export { API_BASE_URL , PAYMENT_API_BASE_URL};