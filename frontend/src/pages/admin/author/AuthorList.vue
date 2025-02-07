<template>
    <div>
        <h2>Liste des auteurs</h2>
        
        <div class="relative overflow-x-auto shadow-md sm:rounded-lg">
            <table class="w-full text-sm text-left rtl:text-right">
                <thead class="text-xs">
                    <tr>
                        <th scope="col" class="px-6 py-3">
                            Name
                        </th>
                        <th scope="col" class="px-6 py-3" colspan="2">
                            Action
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="author in authors" :key="author.id">
                        <th scope="row" class="flex items-center px-6 py-4 whitespace-nowrap">
                            <img v-if="author.image" :src="getAuthorImageUrl(author.image)" alt="Photo de l'auteur" width="50" height="50">
                            <img v-else src="../../../assets/images/user_48px.png" alt="Photo de l'auteur" width="50" height="50">
                            <div class="ps-3">
                                <div class="text-base font-semibold">{{ author.name }}</div>
                            </div>  
                        </th>
                        <td class="px-6 py-4">
                            <button @click="editAuthor(author.id)"><img src="../../../assets/svg/edit.svg" alt="Edit" srcset=""></button>
                        </td>
                        <td class="px-6 py-4">
                            <button @click="deleteAuthor(author.id)"><img src="../../../assets/svg/delete.svg" alt="Delete" srcset=""></button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import server from '@/lib/server';
  import router from '@/router';
  
  const authors = ref([]);
  const isLoading = ref(true);
  
  const fetchAuthors = async () => {
      try {
          const response = await server().get('/admin/authors');
          authors.value = response.data;
      } catch (error) {
          console.error('Erreur lors de la récupération des auteurs:', error);
      } finally {
          isLoading.value = false;
      }
  };
  
  const editAuthor = (id) => {
    router.push(`/admin/authors/edit/${id}`);
  };
  
  const deleteAuthor = async (id) => {
      try {
          await server().delete(`/admin/authors/${id}`);
          fetchAuthors();
      } catch (error) {
          console.error('Erreur lors de la suppression de l\'auteur:', error);
      }
  };
  
  const getAuthorImageUrl = (imagePath) => {
      return `http://localhost:8080/api${imagePath}`;
  };
  
  onMounted(fetchAuthors);
  </script>


<style scoped>
table {
    padding: 1px;
    position: relative;
    top: 0;
    width: 100%;
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

}

.home-section::-webkit-scrollbar{
    display: none;
}
.table-container{
    position: relative;
    top: 0;
    max-height: 525px;
    overflow-y: scroll;
}
table {
    background-color: var(--bg-form);
    color: var(--clr-form);

}
thead{
    background-color: var(--bg-thead);
    color: var(--clr-thead);
}
tr {
    background-color: var(--bg-tr);
    border-color: var(--border-clr-tr);
}
tr:hover {
    background-color: var(--bg-thead);
}
tbody{
    margin-top: 100px;
}


td {
  align-items: center !important;
  justify-content: center !important;
}
</style>