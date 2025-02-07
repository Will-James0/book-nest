<template>
    <div>
        <h2 class="text-2xl font-bold mb-4">Liste des livres</h2>

        <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
            <table class="w-full text-sm text-left rtl:text-right">
                <thead class="text-xs uppercase bg-gray-200">
                    <tr>
                        <th scope="col" class="px-6 py-3">Titre</th>
                        <th scope="col" class="px-6 py-3">Auteur</th>
                        <th scope="col" class="px-6 py-3">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="book in books" :key="book.id" class="bg-white border-b">
                        <td class="px-6 py-4 flex items-center">
                            <img v-if="book.image" :src="getBookImageUrl(book.image)" alt="Couverture" class="w-12 h-12 mr-3 rounded">
                            <span>{{ book.title }}</span>
                        </td>
                        <td class="px-6 py-4">{{ book.author.name }}</td>
                        <td class="px-6 py-4 flex gap-2">
                            <button @click="editBook(book.id)" class="text-blue-600 hover:underline">Modifier</button>
                            <button @click="deleteBook(book.id)" class="text-red-600 hover:underline">Supprimer</button>
                            <button @click="downloadBook(book.file)" class="text-green-600 hover:underline">Télécharger</button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import server from "@/lib/server";
import router from "@/router";

const books = ref([]);
const isLoading = ref(true);

const fetchBooks = async () => {
    try {
        const response = await server().get("/admin/books");
        books.value = response.data;
    } catch (error) {
        console.error("Erreur lors de la récupération des livres:", error);
    } finally {
        isLoading.value = false;
    }
};

const editBook = (id) => {
    router.push(`/admin/books/edit/${id}`);
};

const deleteBook = async (id) => {
    try {
        await server().delete(`/admin/books/${id}`);
        fetchBooks(); // Mettre à jour la liste après suppression
    } catch (error) {
        console.error("Erreur lors de la suppression du livre:", error);
    }
};

const downloadBook = (filePath) => {
    window.open(`http://localhost:8080/api/${filePath}`, "_blank");
};

const getBookImageUrl = (imagePath) => {
    return `http://localhost:8080/api/${imagePath}`;
};

onMounted(fetchBooks);
</script>


<style scoped>
table {
    padding: 1px;
    position: relative;
    top: 0;
    /* left: 2%; */
    width: 100%;
    /* margin-top: 8%; */
}

.select_list.active{
    background: #000;
    color: white;
    border: 5px;
    border-bottom-left-radius: 0px;
    border-top-right-radius: 5px;
}

.contain{
    position: relative;
    width: 100%;
    border-radius: 6px;
    overflow: hidden;
    padding: 0px 20px 0px 20px;
    margin-top: 11%;
    max-height: 700px;
    /* overflow-y: scroll; */

}

.home-section::-webkit-scrollbar{
    display: none;
}
.table-container{
    /* position: absolute; */
    position: relative;
    top: 0;
    max-height: 525px;
    overflow-y: scroll;
    background-color: var(--bg-form);
    color: var(--clr-form);
}
thead{
    position: sticky;
    top: 0;
    z-index: 1;
}
tbody{
    margin-top: 100px;
}

td {
  align-items: center;
  justify-content: center;
}
</style>