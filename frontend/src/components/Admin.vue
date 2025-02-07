<template>
  <div class="body">
    <div class="globe">
        <aside>
           
           <div class="top">
             <div class="logo">
               <h2><span class="text-red-700">BookNest</span> </h2>
             </div>
             <div class="close" id="close_btn">
              <span class="material-symbols-sharp">
                <img src="../assets/svg/close.svg" alt="" srcset="">
                </span>
             </div>
           </div>
           <!-- end top -->
           <div class="sidebar">
              <router-link to="/admin" exact-active-class="active">
                  <span class="material-symbols-sharp"><img src="../assets/svg/grid_view.svg" alt=""></span>
                  <h3>Tableau de bord</h3>
              </router-link>
              <router-link to="/admin/users" exact-active-class="active">
                  <span class="material-symbols-sharp"><img src="../assets/svg/person.svg" alt=""></span>
                  <h3>Utilisateurs</h3>
              </router-link>
              <router-link to="/admin/admins" exact-active-class="active">
                  <span class="material-symbols-sharp"><img src="../assets/svg/person.svg" alt=""></span>
                  <h3>Administrateurs</h3>
              </router-link>
              <router-link to="/admin/authors" exact-active-class="active">
                  <span class="material-symbols-sharp"><img src="../assets/svg/person.svg" alt=""></span>
                  <h3>Auteurs</h3>
              </router-link>
              <router-link to="/admin/authors/add" exact-active-class="active">
                  <span class="material-symbols-sharp"><img src="../assets/svg/person_add.svg" alt=""></span>
                  <h3>Ajouter Auteur</h3>
              </router-link>
              <router-link to="/admin/books" exact-active-class="active">
                  <span class="material-symbols-sharp"><img src="../assets/svg/menu_book.svg" alt=""></span>
                  <h3>Livres</h3>
              </router-link>
              <router-link to="/admin/books/add" exact-active-class="active">
                  <span class="material-symbols-sharp"><img src="../assets/svg/add.svg" alt=""></span>
                  <h3>Livres</h3>
              </router-link>
              <router-link to="/logout" @click="logout()" exact-active-class="active">
                  <span class="material-symbols-sharp"><img src="../assets/svg/logout.svg" alt=""></span>
                  <h3>Logout</h3>
              </router-link>
            </div>
  
        </aside>
        <!-- --------------
            end aside
        -------------------- -->

        <!-- --------------
        start main part
      --------------- -->

          <router-view />
          

        <!------------------
         end main
        ------------------->
        <div class="right">

          <div class="top">
              <button id="menu_bar">
                  <span class="material-symbols-sharp"><img src="../assets/svg/menu.svg" alt="" srcset=""></span>
              </button>

              <div class="theme-toggler">
                  <span class="material-symbols-sharp active"><img src="../assets/svg/light_mode.svg" alt="" srcset=""></span>
                  <span class="material-symbols-sharp"><img src="../assets/svg/dark_mode.svg" alt="" srcset=""></span>
              </div>
              <div class="profile">
                  <div class="info">
                      <p><b>{{ user.surname }} {{ user.name }}</b></p>
                      <p>Admin</p>
                      <small class="text-muted"></small>
                  </div>
                  <div class="profile-photo">
                      <img src="../assets/images/97a41ca6085b70007d4b8e1d17b1eb0a.jpg" alt=""/>
                  </div>
              </div>
          </div>
          <div v-if="$route.path === '/admin'">

            <div class="recent_updates">
                <h2>Recent Update</h2>
                <div class="updates">
                    <div class="update">
                        <div class="profile-photo">
                            <img src="../assets/images/profile-4.jpg" alt=""/>
                        </div>
                        <div class="message">
                            <p><b>Babar</b> Recived his order of USB</p>
                        </div>
                    </div>
                    <div class="update">
                        <div class="profile-photo">
                            <img src="../assets/images/profile-3.jpg" alt=""/>
                        </div>
                        <div class="message">
                            <p><b>Ali</b> Recived his order of USB</p>
                        </div>
                    </div>
                    <div class="update">
                        <div class="profile-photo">
                            <img src="../assets/images/profile-2.jpg" alt=""/>
                        </div>
                        <div class="message">
                            <p><b>Ramzan</b> Recived his order of USB</p>
                        </div>
                    </div>
                </div>
            </div>
  
  
            <div class="sales-analytics">
                <h2>Sales Analytics</h2>
  
                <div class="item onlion">
                    <div class="icon">
                    <span class="material-symbols-sharp"><img src="../assets/svg/person.svg" alt="" srcset=""></span>
                    </div>
                    <div class="right_text">
                    <div class="info">
                        <h3>Onlion Orders</h3>
                        <small class="text-muted">Last seen 2 Hours</small>
                    </div>
                    <h5 class="danger">-17%</h5>
                    <h3>3849</h3>
                    </div>
                </div>
                <div class="item onlion">
                    <div class="icon">
                    <span class="material-symbols-sharp"><img src="../assets/svg/person.svg" alt="" srcset=""></span>
                    </div>
                    <div class="right_text">
                    <div class="info">
                        <h3>Onlion Orders</h3>
                        <small class="text-muted">Last seen 2 Hours</small>
                    </div>
                    <h5 class="success">-17%</h5>
                    <h3>3849</h3>
                    </div>
                </div>
                <div class="item onlion">
                    <div class="icon">
                    <span class="material-symbols-sharp"><img src="../assets/svg/person.svg" alt="" srcset=""></span>
                    </div>
                    <div class="right_text">
                    <div class="info">
                        <h3>Onlion Orders</h3>
                        <small class="text-muted">Last seen 2 Hours</small>
                    </div>
                    <h5 class="danger">-17%</h5>
                    <h3>3849</h3>
                    </div>
                </div>
            </div>
  
            <div class="item add_product">
                <div>
                    <span class="material-symbols-sharp">add</span>
                </div>
            </div>
          </div>

    </div>
        
    </div>
  </div>
</template>

<script setup>
import { onMounted, onBeforeUnmount, computed } from 'vue';
import router from '@/router';
import { useStore } from 'vuex';
import { useAuth } from '@/store/auth';

let sideMenu, menuBtn, closeBtn, themeToggler;

const openMenu = () => {
  if (sideMenu) sideMenu.style.display = "block";
};

const closeMenu = () => {
  if (sideMenu) sideMenu.style.display = "none";
};

const toggleTheme = () => {
  document.body.classList.toggle('dark-theme-variables');
  
  // Active/désactive les spans de l'icône
  themeToggler?.querySelector('span:nth-child(1)')?.classList.toggle('active');
  themeToggler?.querySelector('span:nth-child(2)')?.classList.toggle('active');

  // Sauvegarde le choix du thème
  const isDarkMode = document.body.classList.contains('dark-theme-variables');
  localStorage.setItem('darkMode', isDarkMode ? 'enabled' : 'disabled');
};

onMounted(() => {
  sideMenu = document.querySelector('aside');
  menuBtn = document.querySelector('#menu_bar');
  closeBtn = document.querySelector('#close_btn');
  themeToggler = document.querySelector('.theme-toggler');

  if (menuBtn) menuBtn.addEventListener('click', openMenu);
  if (closeBtn) closeBtn.addEventListener('click', closeMenu);
  if (themeToggler) themeToggler.addEventListener('click', toggleTheme);

  // Vérifie et applique le thème stocké
  const darkMode = localStorage.getItem('darkMode');
  if (darkMode === 'enabled') {
    document.body.classList.add('dark-theme-variables');
    themeToggler?.querySelector('span:nth-child(1)')?.classList.add('active');
    themeToggler?.querySelector('span:nth-child(2)')?.classList.remove('active');
  }
});

onBeforeUnmount(() => {
  if (menuBtn) menuBtn.removeEventListener('click', openMenu);
  if (closeBtn) closeBtn.removeEventListener('click', closeMenu);
  if (themeToggler) themeToggler.removeEventListener('click', toggleTheme);
});

const store = useAuth();

const user = store.getUser();

const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('is_superuser');
  router.push('/')
};

</script>


<style >

/* variables */


:root {
  --clr-primary: #7380ec;
  --clr-danger: #ff7782;
  --clr-success: #41f1b6;
  --clr-white: #fff;
  --clr-info-dark: #7d8da1;
  --clr-info-light: #dce1eb;
  --clr-dark: #363949;
  --clr-warnig: #ff4edc;
  --clr-light: rgba(132, 139, 200, 0.18);
  --clr-primary-variant: #111e88;
  --clr-dark-variant: #677483;
  --clr-color-background: #f6f6f9;

  --card-border-radius: 2rem;
  --border-radius-1: 0.4rem;
  --border-radius-2: 0.8rem;
  --border-radius-3: 1.2rem;

  --card-padding: 1.8rem;
  --padding-1: 1.2rem;
  --box-shadow: 0 2rem 3rem var(--clr-light);

  --bg-ctn-form: #fff;
  --bg-form: #fff;
  --clr-form: #000;
  --clr-header: #333;
  --clr-label:#2e2e2e;
  --clr-thead: rgb(55, 65, 81);
  --bg-thead: rgb(249, 250, 251);
  --bg-tr: #fff;
  --border-clr-tr: rgb(229, 231, 235);
  --clr-th: rgb(17, 24, 39);

  
}

/* dark theme */
.dark-theme-variables {
  --border-clr-tr: rgb(55, 65, 81);
  --bg-tr: rgb(31, 41, 55);
  --clr-thead: rgb(156, 163, 175);
  --bg-thead: rgb(55, 65, 81);
  --bg-ctn-form: #000;
  --bg-form: #000;
  --clr-form: #fff;
  --clr-header: #ddd;
  --clr-label: #e2e2e2;



  --clr-color-background: #181a1e;
  --clr-white: #202528;
  --clr-light: rgba(0, 0, 0, 0.4);
  --clr-dark: #edeffd;
  --clr-dark-variant: #677483;
  --box-shadow: 0 2rem 3rem var(--clr-light)
}


.body{
   width: 100vw;
   height: 100vh;
   font-size: .7rem;
   user-select: none;
   overflow-x: hidden;
   background: var(--clr-color-background);
   font-family: 'Poppins', sans-serif;
}

.body .globe  {
  display: flex;
  width: 96%;
  gap: 1.8rem;
  grid-template-columns: 14rem auto 16rem;
  margin: 0 auto;
}

.body a{
  color: var(--clr-dark);
}

.body h1{
  font-weight: 800;
  font-size: 2.8rem;
}
.body h2{
  font-size: 1.4rem;
}
.body h3{
  font-size: 0.87;
}
.body h4{
  font-weight: 0.8rem;
}
.body h5{
  font-size: 0.77rem;
}
.body small{
  font-size: 0.75rem;
}
.body .profile-photo img{
  width: 2.8rem;
  height: 2.8rem;
  overflow: hidden;
  border-radius: 50%;
}
.body .text-muted{
  color: var(--clr-info-dark);
}
.body p{
  color: var(--clr-dark-variant);
}
.body b{
  color: var(--clr-dark);
}
.body .primary {
  color: var(--clr-primary);
}

.body .success {
  color: var(--clr-success);
}

.body .danger {
  color: var(--clr-danger);
}

.body .warning {
  color: var(--clr-warnig);
}


/* aside */
.body aside{
  height: 100vh;
  flex-basis: 20%;
 }
 .body aside .top{
  background: var(--clr-white);
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 1.4rem;
 }

 .body aside .logo{
   display: flex;
   gap: 1rem;
 }
 .body aside .logo img{
   width: 2rem;
   height: 2rem;
 }
 .body aside .top div.close span{
   display: none;
 }

 /* ===================
 Sidebar
 ================== */

 .body aside .sidebar{
  background: var(--clr-white);
  display: flex;
  flex-direction: column;
  height: 86vh;
  position: relative;
  top: 1rem;
 }

 .body aside h3{
  font-weight: 500;
 }

 .body aside .sidebar a{
  display: flex;
  color: var(--clr-info-dark);
  margin-left: 2rem;
  gap: 1rem;
  align-items: center;
  height: 3.3rem;
  transition: all .1s ease;
 
 }

 .body aside .sidebar a span{
  font-size: 1.6rem;
  transition: all .3s ease-in-out;
  
 }

 .body aside .sidebar a:last-child{
  position: absolute;
  bottom: 1rem; 
  width: 100%;
 }

 .body aside .sidebar a.active{
   background-color: var(--clr-light);
   color: var(--clr-primary);
   margin-left: 0;
   border-left: 5px solid var(--clr-primary);
   margin-left: calc(1rem - 3px);
 }
 .body aside .sidebar a:hover span{
  margin-left: 1rem;
 }

 .body aside .sidebar a span.msg_count{
   background-color: var(--clr-danger);
   color: var(--clr-white);
   padding: 2px 5px;
   font-size: 11px;
   border-radius: var(--border-radius-1);
 }

 /* ----------------------------
 --------- Main--------------
 ------------------------------ */

 .body main{
  margin-top: 1.4rem;
  width: auto;
  flex-basis: 60%;
 }
 .body main input {
  background-color: transparent;
  border: 0;
  outline: 0;
  color: var(--clr-dark);
 }

 .body main .date{
  display: inline-block;
  background: var(--clr-white);
  border-radius: var(--border-radius-1);
  margin-top: 1rem;
  padding: 0.5rem 1.6rem;
 }

.body main .insights{
   display: grid;
   grid-template-columns: repeat(3,1fr);
   gap: 1.6rem;
}
.body main .insights>div{
   background-color: var(--clr-white);
   padding: var(--card-padding);
   border-radius: var(--card-border-radius);
   margin-top: 1rem;
   box-shadow: var(--box-shadow);
   transition: all .3s ease;
}

.body main .insights > div:hover{
  box-shadow: none;
}
.body main .insights>div img{
   background: coral;
    padding: 0.5rem;
     border-radius: 50%;
     color: var(--clr-white);
     font-size: 2rem; 
  }

.body main .insights > div.expenses span{
   background: var(--clr-danger);
}
.body main .insights > div.income span{
  background: var(--clr-success);
}

.body main .insights > div .middle{
   display: flex;
   align-items: center;
   justify-content: space-between;
}
.body main .insights > div .middle h1{
  font-size: 1.6rem;
}

.body main h1{
  color: var(--clr-dark);
}
.body main .insights h1{
  color: var(--clr-dark);
}
.body main .insights h3{
  color: var(--clr-dark);
}
.body main .insights p{
  color: var(--clr-dark);
}
.body main .insights h3{
  
    color: var(--clr-dark);
  
  
}

.body main .insights .progress{
  position: relative;
  height: 68px;
  width: 68px;
  border-radius: 50px;
}

.body main .insights svg{
  height: 150px;
  width: 150px;
  position: absolute;
  top: 0;
}

.body main .insights svg circle{
  fill: none;
  stroke: var(--clr-primary);
   transform: rotate(270,80,80);
   stroke-width: 5;
}
.body main .insights .sales svg circle{
   stroke-dashoffset: 0;
   stroke-dasharray: 150;
}

.body main .insights .expenses svg circle{
  stroke-dashoffset: 10;
  stroke-dasharray: 150;
}
.body main .insights .sales svg circle{
  stroke-dashoffset: 0;
  stroke-dasharray: 150;
}
.body main .insights .income svg circle{
  stroke: var(--clr-success);
}

.body main .insights .progress .number{
  position: absolute;
  top: 5%;
  left: 5%;
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

/* recent order */

.body main .recent_order{
   margin-top: 2rem;
}

.body main .recent_order h2{
    color: var(--clr-dark);
}

.body main .recent_order h2{
  margin-bottom: 0.8rem;
}
.body main .recent_order table{
  background: var(--clr-white);
  width: 100%;
  border-radius: var(--card-border-radius);
  padding: var(--card-padding);
  text-align: center;
  box-shadow: var(--box-shadow);
  transition: all .3s ease;
  color: var(--clr-dark);
}
.body main .recent_order table:hover{
  box-shadow: none;
}

.body main table tbody td{
   height: 3.8rem;
   border-bottom: 1px solid var(--clr-white);
   color:var(--clr-dark-variant)
}
.body main table tbody tr:last-child td{
   border: none;
}


.body main .recent_order a{
  text-align: center;
  display: block;
  margin: 1rem;
}


/*********************
Right Side
**********************/

.right{
  margin-top: 1.4rem;
}
.right h2{
  color: var(--clr-dark);
}
.right .top{
  display: flex;
  justify-content: start;
  gap: 2rem;
}

.right .top button{
  display: none;
}

.right .theme-toggler{
  background: var(--clr-white);
  display: flex;
  justify-content: space-between;
  height: 1.6rem;
  width: 4.2rem;
  cursor: pointer;
  border-radius: var(--border-radius-1);
}
.right .theme-toggler span{
  font-size: 1.2rem;
  width: 50%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.right .theme-toggler span.active{
  background: var(--clr-primary);
  color: #fff;
}


.right .top .profile{
  display: flex;
  gap: 2rem;
  text-align: right;
}
.right  .info h3{
  color: var(--clr-dark);
}
.right  .item h3{
  color: var(--clr-dark);
}

/* recent update */

.right .recent_updates{
  margin-top: 1rem;
  margin-left: -20px;
}
.right .recent_updates .updates{
  background-color: var(--clr-white);
  padding: var(--card-padding);
  border-radius: var(--card-border-radius);
  box-shadow: var(--box-shadow);
   transition: all .3s ease;
}
.right .recent_updates .updates:hover{
  box-shadow: none;
}
.right .recent_updates .update{
   display: grid;
   grid-template-columns: 2.6rem auto;
   gap: 1rem;
   margin-bottom: 1rem;
}


/* see analytics */

.right .sales-analytics{
  margin-top: 2rem;
}
.right .sales-analytics h2{
  margin-bottom: 0.8rem;
}
.right .sales-analytics .item{
  background-color: var(--clr-white);
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
  margin: 10px 0;
  width: 100%;
  margin-bottom: 0.8rem;
  padding: 1.4rem var(--card-padding);
  border-radius: var(--border-radius-3);
  box-shadow: var(--box-shadow);
}
.right .sales-analytics .item:hover{
  box-shadow: none;
}

.right .sales-analytics .item .icon{
  padding: 0.6rem;
  color: var(--clr-white);
  border-radius: 50%;
  height: 50px;
  width: 50px;
  line-height: 50px;
  align-items: center;
  background: coral;
}
.right .sales-analytics .item:nth-child(3) .icon
{
  background: var(--clr-success);
}
.right .sales-analytics .item:nth-child(4) .icon
{
  background: var(--clr-danger);
}

.right .sales-analytics .item .right{
  margin-left: 15px;
  display: flex;
  justify-content: space-around;
}
.right .sales-analytics .item .right h3{
  color: var(d);
}


.add_product div{
  display: flex;
  height: 60px;
  width: 100%;
  text-align: center;
  justify-content: center;
  align-items: center;
  border: 2px dashed;
  color: var(--clr-dark-variant);
  margin-bottom: 40px;

}




/* **********8
MEDIA QUERY
****************/

@media screen and (max-width:1200px) {
    .globe{
      width: 94%;
      grid-template-columns: 7rem auto 14rem;
    }
    .body aside .sidebar h3{
      display: none;
    }
    .body aside .sidebar a{
      width: 5.6rem;
    }
    .body aside .sidebar a:last-child{
      position: relative;
      margin-top: 1.8rem;
    }
    .body main .insights{
      display: grid;
      grid-template-columns: repeat(1,1fr);
    }
}

/* ****************
MEDIA QUERY PHONE
************************/

@media screen and (max-width:768px) {
  .globe{
    width: 100%;
    grid-template-columns: repeat(1,1fr);
  
  }
  
  .body aside{
    position: fixed;
    width: 18rem;
    z-index: 3;
    height: 100vh;
    background-color: var(--clr-white);
    display: none;
    left: -110px;
    animation:  menuLeft .3s ease forwards;
  }
  
  @keyframes menuLeft {
      to{
        left: 0;
      }
  }
  



  .body aside .logo h2{
       display: inline;
  }
  .body aside .sidebar h3{
    display: inline;
  }
  .body aside .sidebar a{
    width: 100%;
    height: 3.4rem;
  }
  .body aside .top div.close  span{
     display: inline;
     position: absolute;
     right: 0;
     margin-right:30px;
     font-size: 35px;
     cursor: pointer;
  }

    .right .top{
       position: fixed;
       top: 0;
       left: 0;
       display: flex;
       justify-content: center;
       align-items: center;
       padding: 0 0.8rem;
       background: var(--clr-white);
       height: 4.6rem;
       width: 100%;
       z-index: 2;
       box-shadow: 0 1rem 1rem var(--clr-light);
       margin: 0;
    }


 .body main .insights{
  display: grid;
  grid-template-columns: repeat(1,1fr);
  gap: 1.6rem;
  padding: 40px;
 }
 .body main .recent_order{
  padding: 30px;
  margin: 0 auto;
 }



  .right .profile{
    position: absolute;
    left: 70%;
  }

  .right .top .theme-toggler{
    width: 4.4rem;
    position: absolute;
    left: 50%;
  }
   .right .profile .info{
    display: none;
   }

   .right .top button{
     display: inline-block;
     background: transparent;
     cursor: pointer;
     color: var(--clr-dark);
     position: absolute;
     left: 1rem;

   }
   .right .recent_order{
    padding: 0 30px;
   }
   .right .recent_updates{
    padding: 0 40px;
   }
   .right .sales-analytics{
    padding: 0 40px;
   }
   .right .add_product{
    padding: 0 40px;
    margin-bottom: 40px;
   }


}
    
</style>
