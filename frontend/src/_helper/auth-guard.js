import router from "@/router"

export default {
    adminGuard(to) {
        let token = localStorage.getItem('token');
        let isSuperuser = localStorage.getItem('is_superuser') === 'true'; // Convertir en booléen
        console.log("token: ", token);
        console.log("Super: ", isSuperuser);

        if (token && isSuperuser) {
            return true;
        }

        router.push("/login");
    },

    authGuard(to) {
        let token = localStorage.getItem('token');
        console.log(token);

        if (token) {
            return true;
        }

        router.push("/login");
    }
};