<template>
    <h2>Modifier un auteur</h2>
    <div class="container">
        <header>Formulaire d'édition d'un auteur</header>
        <form @submit.prevent="submitForm" enctype="multipart/form-data">
            <div class="form first">
                <div class="details personal">
                    <span class="title">Details de l'auteur</span>
                    <div class="fields">
                        <div class="input-field">
                            <label for="name">Nom <span class="require">*</span> :</label>
                            <input type="text" placeholder="Entrer un nom:" name="name" id="name" v-model="author.name" required>
                        </div>
                        <div class="input-field">
                            <label for="photo">Photo:</label>
                            <input type="file" name="photo" accept="image/*" style="border: none;" @change="handleFileUpload">
                        </div>
                    </div>
                </div>

                <div class="details ID">
                    <div class="fields">
                    </div>
            
                    <button type="submit" class="nextBtn">
                        <span class="btnText">Valider</span>
                    </button>
                </div> 
            </div>
        </form>
    </div>
</template>
  
<script setup>
import { ref, onMounted } from 'vue';
import router from '@/router';
import server from '@/lib/server';

const author = ref({});
const image = ref(null);
const previewImage = ref(null);

const props = defineProps({
  id: Number
});

const fetchAuthor = async () => {
    try {
        console.log("Props reçus:", props.id);
        const response = await server().get(`/admin/authors/${props.id}`);
        console.log("param", props.id)
        author.value = response.data;
        if (author.value.image) {
            previewImage.value = `http://localhost:8080/api${author.value.image}`;
        }
    } catch (error) {
        console.error('Erreur lors de la récupération de l\'auteur:', error);
    }
};

const handleFileUpload = (event) => {
    const file = event.target.files[0];
    if (file) {
        if (!file.type.startsWith("image/")) {
            alert("Veuillez sélectionner une image valide !");
            return;
        }
        image.value = file;
        previewImage.value = URL.createObjectURL(file);
    }
};

const submitForm = async () => {
    try {
        const formData = new FormData();
        formData.append('name', author.value.name);
        if (image.value) {
            formData.append('image', image.value);
        }

        await server().put(`/admin/authors/${props.id}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });

        alert('Auteur mis à jour avec succès');
        router.push('/admin/authors');
    } catch (error) {
        console.error('Erreur lors de la mise à jour de l\'auteur:', error);
        alert("Erreur lors de la mise à jour !");
    }
};

onMounted(fetchAuthor);
</script>

  
<style scoped>
.container{
    position: relative;
    overflow: hidden;
    width: 100%;
    border-radius: 6px;
    padding: 40px;
    margin: 0 15px;
    background-color: var(--bg-ctn-form);
    box-shadow: 0 5px 10px rgba(0,0,0,0.1);
}

.hidden {
    display: none;
}

.container header{
    position: relative;
    font-size: 20px;
    font-weight: 600;
    color: var(--clr-header);
}
.container header::before{
    content: "";
    position: absolute;
    left: 0;
    bottom: -2px;
    height: 3px;
    width: 27px;
    border-radius: 8px;
    background-color: #4070f4;
}
.container form{
    position: relative;
    margin-top: 16px;
    min-height: 300px;
    background-color: var(--bg-form);
    overflow: hidden;
}
.container form .form{
    position: absolute;
    background-color: var(--bg-form);
    transition: 0.3s ease;
}


.container .form.second{
    /* opacity: 0; */
    pointer-events: none;
    transform: translateX(100%);
    /* display: none; */
}
form.secActive .f1{
    opacity: 0;
    pointer-events: none;
    transform: translateY(-100%);
}
form.secActive .f2{
    opacity: 1;
    pointer-events: auto;
    transform: translateY(-100%);
}
.container form .title{
    display: block;
    margin-bottom: 8px;
    font-size: 16px;
    font-weight: 500;
    margin: 6px 0;
    color: var(--clr-header);
}
.container form .fields{
    display: flex;
    align-items: center;
    justify-content: space-between;
    flex-wrap: wrap;
}
form .fields .input-field{
    display: flex;
    width: calc(100% / 3 - 15px);
    flex-direction: column;
    margin: 4px 0;
}
.input-field label{
    font-size: 12px;
    font-weight: 500;
    color: var(--clr-label);
}
.input-field input, select{
    outline: none;
    font-size: 14px;
    font-weight: 400;
    color: var(--clr-header);
    border-radius: 5px;
    border: 1px solid #aaa;
    padding: 0 15px;
    height: 42px;
    margin: 8px 0;
}
.input-field input :focus,
.input-field select:focus{
    box-shadow: 0 3px 6px rgba(0,0,0,0.13);
}
.input-field select{
    color: #707070;
}
.container form button, .backBtn{
    display: flex;
    align-items: center;
    justify-content: center;
    height: 45px;
    max-width: 200px;
    width: 100%;
    border: none;
    outline: none;
    color: #fff;
    border-radius: 5px;
    margin: 25px 0;
    background-color: #4070f4;
    transition: all 0.3s linear;
    cursor: pointer;
}
.container form .btnText{
    font-size: 14px;
    font-weight: 400;
}
form button:hover{
    background-color: #265df2;
}
form button i,
form .backBtn i{
    margin: 0 6px;
}
form .backBtn i{
    transform: rotate(180deg);
}
form .buttons{
    display: flex;
    align-items: center;
}
form .buttons button , .backBtn{
    margin-right: 14px;
}
.require{
color: #f00;
}

@media (max-width: 800px) {
    .fields {
        display: flex;
        flex-wrap: wrap;
    }
    .input-field {
        flex: 1 1 calc(50% - 10px); /* Prend 50% de la largeur du conteneur moins l'espace entre les éléments */
        box-sizing: border-box;
        gap: 10px;
    }
    .container form {
        min-height: 750px;
    }
}
@media (max-width: 570px) {
    .input-field {
        flex: 1 calc(100%); /* Prend 50% de la largeur du conteneur moins l'espace entre les éléments */
        box-sizing: border-box;
    }
    .container form {
        min-height: 1450px;
    }
    .container form input, select, .select-multiple-container {
        width: 90%;
    }
}

</style>