import { acceptHMRUpdate, defineStore } from "pinia";
import { ref } from "vue";

export const useAuth = defineStore("auth", () => {
    const user = ref(null);

    const setUser = (userData) => {
        user.value = userData;
    };

    const getUser = () => {
        return user.value
    }

    return {
        user,
        setUser,
        getUser,
    };
},
{
    persist: {
        enabled: true,
        strategies: [
          {
            key: 'auth',
            storage: localStorage,
          }
        ]
    }
      
}
);

if (import.meta.hot) {
    import.meta.hot.accept(acceptHMRUpdate(useAuth, import.meta.hot));
}