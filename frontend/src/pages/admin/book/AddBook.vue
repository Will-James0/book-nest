<template>
    <div class="container">
        <header>Formulaire d'ajout d'un livre</header>
        <form @submit.prevent="submitForm" enctype="multipart/form-data">
            <div class="form first">
                <div class="details personal">
                    <span class="title">Détails du livre</span>
                    <div class="fields">
                        <div class="input-field">
                            <label for="title">Titre <span class="require">*</span> :</label>
                            <input type="text" placeholder="Entrer un titre:" id="title" v-model="title" required>
                        </div>

                        <div class="input-field">
                            <label for="author">Nom de l'auteur <span class="require">*</span> :</label>
                            <select id="author" v-model="selectedAuthorId" required>
                                <option value="" disabled>Choisissez un auteur</option>
                                <option v-for="author in authors" :key="author.id" :value="author.id">
                                    {{ author.name }}
                                </option>
                            </select>
                        </div>
                    </div>

                    <div class="fields">
                        <div class="input-field">
                            <label for="photo">Photo :</label>
                            <input type="file" id="photo" accept="image/*" @change="handleImageUpload">
                        </div>

                        <div class="input-field">
                            <label for="book">Fichier <span class="require">*</span> :</label>
                            <input type="file" id="book" @change="handleFileUpload" required>
                        </div>
                    </div>
                    <div class="fields">
                        <div class="input-field">
                            <textarea name="description" id="description" v-model="description"></textarea>
                        </div>
                    </div>
                </div>
        
                <div class="details ID">
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
import server from '@/lib/server';
import router from '@/router';

// Références pour les données du formulaire
const title = ref("");
const description = ref("");
const selectedAuthorId = ref("");
const authors = ref([]);
const bookFile = ref(null);
const bookImage = ref(null);

// Récupérer la liste des auteurs au chargement
onMounted(async () => {
    try {
        const response = await server().get('/admin/authors');
        authors.value = response.data;
    } catch (error) {
        console.error("Erreur lors du chargement des auteurs :", error);
    }
});

// Gestion de l'upload des fichiers
function handleFileUpload(event) {
    const file = event.target.files[0];
    if (file) {
        bookFile.value = file;
    }
}

function handleImageUpload(event) {
    const file = event.target.files[0];
    if (file && file.type.startsWith("image/")) {
        bookImage.value = file;
    } else {
        alert("Veuillez sélectionner une image valide !");
    }
}

// Fonction pour soumettre le formulaire
async function submitForm() {
    try {
        if (!title.value.trim() || !selectedAuthorId.value || !bookFile.value) {
            alert("Veuillez remplir tous les champs obligatoires !");
            return;
        }

        const formData = new FormData();
        formData.append("title", title.value);
        formData.append("authorId", selectedAuthorId.value);
        formData.append("description", description.value);
        formData.append("file", bookFile.value);
        if (bookImage.value) {
            formData.append("image", bookImage.value);
        }

        const response = await server().post('/admin/books', formData, {
            headers: { "Content-Type": "multipart/form-data" }
        });

        alert("Livre ajouté avec succès !");
        router.push('/admin/books');

        // Réinitialiser le formulaire
        title.value = "";
        selectedAuthorId.value = "";
        bookFile.value = null;
        bookImage.value = null;
    } catch (error) {
        console.error("Erreur lors de l'ajout du livre :", error);
        alert("Erreur lors de l'enregistrement !");
    }
}
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
    min-height: 400px;
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