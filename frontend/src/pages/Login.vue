<template>
    <div class="full h-screen w-screen flex items-center justify-center">
        <div class="contain bg-white overflow-hidden relative w-[768px] max-w-full min-h-[480px]" id="container">
            <div class="form-container sign-in absolute top-0 h-full left-0 w-1/2 z-[2]">
                <form @submit.prevent="handleLogin" class="bg-white flex items-center justify-center flex-col h-full">
                    <h1>Se connecter</h1>
                    <input v-model="loginData.email" type="email" name="email" id="" placeholder="email">
                    <input v-model="loginData.password" type="password" name="password" id="" placeholder="Password">
                    <button type="submit" ::disabled="isLoading">
                        {{ isLoading ? 'Connexion en cours...' : 'Se connecter' }}
                    </button>
                </form>
                <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
            </div>
            <div class="form-container sign-up absolute top-0 h-full left-0 w-1/2 opacity-0 z-[1]">
                <form @submit.prevent="handleRegister" class="bg-white flex items-center justify-center flex-col h-full">
                    <h1>Créer un compte</h1>
                    <input v-model="registerData.name" type="text" name="name" id="" placeholder="Nom">
                    <input v-model="registerData.surname" type="text" name="surname" id="" placeholder="surname">
                    <input v-model="registerData.email" type="email" name="email" id="" placeholder="Email">
                    <input v-model="registerData.password" type="password" name="password" id="" placeholder="Password">
                    <button type="submit">S'inscrire</button>
                </form>
            </div>
            <div class="toggle-container absolute top-0 left-1/2 w-1/2 h-full overflow-hidden z-[1000]">
                <div class="toggle h-full text-white relative left-[-100%] w-[200%]">
                    <div class="toggle-panel toggle-left">
                        <h1>Bon Retour!</h1>
                        <p>Entrez vous informations personnelles pour accéder à notre site
                        </p>
                        <button class="hidden-btn" id="login" @click="handleLoginClick">Se connecter</button>
                    </div>
                    <div class="toggle-panel toggle-right">
                        <h1>Bienvenue!</h1>
                        <p>Enregistrez vous avec vos informations personnelles
                        </p>
                        <button class="hidden-btn" id="register" @click="handleRegisterClick">S'inscrire</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import server from '@/lib/server';
import router from '@/router';
import { useAuth } from '@/store/auth';


const container = ref(null);

onMounted(() => {
  container.value = document.getElementById('container');
});

const handleRegisterClick = () => {
  if (container.value) {
    container.value.classList.add('active');
  } else {
    console.error('Element with ID "container" not found.');
  }
};

const handleLoginClick = () => {
  if (container.value) {
    container.value.classList.remove('active');
  } else {
    console.error('Element with ID "container" not found.');
  }
};


const loginData = ref({
  email: '',
  password: '',
});
const registerData = ref({
  name: '',
  surname: '',
  email: '',
  password: '',
  notificationsEnabled: false,
});

const isLoading = ref(false);
const errorMessage = ref('');

const handleLogin = async () => {
  if (!loginData.value.email || !loginData.value.password) {
    errorMessage.value = 'Veuillez remplir tous les champs.';
    return;
  }

  isLoading.value = true;
  errorMessage.value = '';

  const authStore = useAuth();

  try {
    const response = await server().post('/auth/login', loginData.value)
    localStorage.setItem('token', response.data.token);
    localStorage.setItem('is_superuser', response.data.is_superuser)

    // Mettez à jour l'état de l'utilisateur dans le store Pinia
    const user = response.data;
    authStore.setUser(user);

    if (response.data.redirectUrl) {
      router.push(response.data.redirectUrl);
    } else {
      router.push('/user');
    }
  } catch (error) {
    console.error('Login failed:', error.response?.data || error.message);
    errorMessage.value = 'Échec de la connexion : ' + (error.response?.data?.message || error.message);
  } finally {
    isLoading.value = false;
  }
};

const handleRegister = async () => {
  try {
    const response = await server().post('/auth/register', registerData.value);
    console.log('Registration successful:', response.data);
    
  } catch (error) {
    console.error('Registration failed:', error.response?.data || error.message);
  }
};

</script>


<style scoped>
    .full {
        background-color: #c9d6ff;
        background: linear-gradient(to right, #e2e2e2, #c9d6ff);
    }

    .contain {
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
        border-radius: 30px;
    }

    .contain h1 {
        font-size: 30px;
        font-weight: bold;
    }

    .contain p {
        font-size: 14px;
        line-height: 20px;
        letter-spacing: 0.3px;
        margin: 20px 0;
    }

    .contain span {
        font-size: 12px;
    }

    .contain form {
        padding: 20px;
    }

    .contain a {
        color: #333;
        font-size: 13px;
        text-decoration: none;
        margin: 15px 0 10px;
    }

    .contain button {
        background-color: #512da8;
        color: #fff;
        font-size: 12px;
        padding: 10px 45px;
        border: 1px solid transparent;
        border-radius: 8px;
        font-weight: 600;
        letter-spacing: 0.5px;
        text-transform: uppercase;
        margin-top: 10px;
        cursor: pointer;
    }
    .contain button.hidden-btn {
        background-color: transparent;
        border-color: #fff;
    }

    .contain input {
        background-color: #eee;
        border: none;
        margin: 8px 0;
        padding: 10px 15px;
        font-size: 13px;
        border-radius: 8px;
        width: 100%;
        outline: none;
    }

    .form-container {
        transition: all 0.6s ease-in-out;
    }

    .contain.active .sign-in {
        transform: translateX(100%);
    }

    .contain.active .sign-up {
        transform: translateX(100%);
        opacity: 1;
        z-index: 5;
        animation: move 0.6s;
    }

    @keyframes move {
        0%, 49.99% {
            opacity: 0;
            z-index: 1;
        }
        50%, 100% {
            opacity: 1;
            z-index: 5;
        }
    }

    .toggle-container{
        transition: all 0.6s ease-in-out;
        border-radius: 150px 0 0 100px;
    }

    .contain.active .toggle-container{
        transform: translateX(-100%);
        border-radius: 0px 150px 100px 0;
    }

    .toggle {
        background-color: #512da8;
        background: linear-gradient(to right, #5c6bc0, #512da8);
        transform: translateX(0);
        transition: all 0.6s ease-in-out;
    }

    .contain.active .toggle {
        transform: translateX(50%);
    }

    .toggle-panel {
        position: absolute;
        width: 50%;
        height: 100%;
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        padding: 0 30px;
        text-align: center;
        top: 0;
        transform: translateX(0);
        transition: all 0.6s ease-in-out;
    }

    .toggle-left {
        transform: translateX(-200%);
    }

    .contain.active .toggle-left {
        transform: translateX(0);
    }

    .toggle-right {
        right: 0;
        transform: translateX(0);
    }

    .contain.active .toggle-right {
        transform: translateX(200%);
    }
</style>
