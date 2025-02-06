<template>
    <div>
        <h2>Liste des auteurs</h2>
        <div class="table-container">
            <table class="table tab">
                <thead>
                    <tr>
                        <th>Photo</th>
                        <th>Nom Complet</th>
                        <th colspan="2">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="author in authors" :key="author.id">

                        <td>
                            <img v-if="author.image" :src="getAuthorImageUrl(author.image)" alt="Photo de l'auteur" width="50" height="50">
                            <img v-else src="../../../assets/images/user_48px.png" alt="Photo de l'auteur" width="50" height="50">
                        </td>
                        <td>{{ author.name }}</td>
                        <td>
                            <button @click="editAuthor(author.id)"><img src="../../../assets/svg/edit.svg" alt="Edit" srcset=""></button>
                        </td>
                        <td>
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
          await server().delete(`api/admin/authors/${id}`);
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