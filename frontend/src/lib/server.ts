import axios from "axios";

const server = (Headers) => axios.create({
    baseURL: "http://localhost:8080/api",
    headers: {
        "Accept": "application/json",
        ...Headers,
    },
});

export default server;
