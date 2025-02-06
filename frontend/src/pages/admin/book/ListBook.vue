<template>
    <div>
        <h2>Liste des livres</h2>
    </div>
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>
                        Titre
                    </th>
                    <th>
                        Nom de l'auteur
                    </th>
                    <th>
                        Action
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>1</td>
                    <td>1</td>
                    <td>1</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup>

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