<template>
    <nav class="container flex justify-between items-center h-16 text-white text-2xl shadow-sm font-mono absolute" role="navigation">
        <div class="flex items-center pl-8">
            <a href="#" class="pl-2 text-red-700 font-bold text-2xl">Booknest</a>
        </div>
        <div class="pr-8">
            <router-link to="/login" class="pr-2 hover:text-blue-700 transition-colors">Login</router-link>
        </div>
    </nav>
    <main class="container h-screen max-w-full flex justify-center text-white">
        <section id="left" class="container text-white h-screen items-center justify-center flex flex-col z-0">
            <h1 class="text-4xl font-bold">Bienvenue à  Booknest</h1>
            <p class="text-lg">Nous proposons un large choix de livres parmi lesquels vous pouvez choisir.</p>
        </section>
        <section class="container h-screen max-w-full items-center justify-center flex overflow-hidden text-black">
            <div class="book">
                <div class="cover">
                    <img src="../assets/images/cover.webp" alt="">
                    <img src="../assets/images/back.webp" alt="">
                </div>
                <div class="content">
                    <h2 class="text-2xl font-bold">BookNest</h2>
                    <p>BookNest est votre destination en ligne pour découvrir, lire et explorer une vaste collection de livres numériques. 
                        Que vous soyez amateur de romans captivants, passionné de science-fiction, ou curieux des derniers ouvrages éducatifs, 
                        notre bibliothèque a quelque chose pour chaque lecteur. Grâce à une interface conviviale et des recommandations personnalisées, 
                        plongez dans des histoires fascinantes et enrichissez vos connaissances à tout moment. 
                        Rejoignez-nous et transformez votre manière de lire avec BookNest, là où la littérature rencontre la technologie.</p>
                </div>
            </div>
        </section>
        
    </main>
</template>

<script setup>

</script>

<style scoped>
    main {
        background: url('../assets/images/shelf.jpg') no-repeat center center/cover rgba(255, 255, 255, 0.3);
    }

    #left {
        z-index: 0;
    }

    .book {
        position: absolute;
        width: 300px;
        height: 400px;
        background-color: #fff;
        border-top-right-radius: 10px;
        border-bottom-right-radius: 10px;
        cursor: pointer;
        box-shadow: 10px 0 20px rgba(0, 0, 0, 0.4),
        inset 300px 0 50px rgba(0, 0, 0, 0.4);
        transform: perspective(2000px) rotateZ(0);
        transform-style: preserve-3d;
        transition: all 1s ease;
    }

    .cover {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background: #fff;
        border-top-right-radius: 10px;
        border-bottom-right-radius: 10px;
        transform-origin: left;
        transform-style: preserve-3d;
        transition: all 1s ease;
    }

    .cover img {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        object-fit: cover;
        backface-visibility: hidden;
    }

    .cover img:first-child {
        border-top-right-radius: 10px;
        border-bottom-right-radius: 10px;
    }

    .cover img:last-child {
        transform: rotateY(180deg);
        border-top-left-radius: 10px;
        border-bottom-left-radius: 10px;
    }

    .book:hover {
        transform: perspective(2000px) rotateZ(-10deg);
        box-shadow: 10px 0 20px rgba(0, 0, 0, 0.4),
         inset 20px 0 50px rgba(0, 0, 0, 0.4);
        
    }

    .book:hover .cover {
        transform: rotateY(-135deg);
        box-shadow: 10px 0 20px rgba(0, 0, 0, 0.4)
    }

    .content {
        width: 100%;
        height: 100%;
        padding: 1rem;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        font-size: 12px;
    }

    .content h2 {
        margin-bottom: 1rem;
    }

    @media screen and (max-width: 758px) {
        main {
            flex-direction: column;
        }
        .book {
            width: 200px;
            height: 300px;
        }
        
        .book:hover {
            transform: perspective(2000px);
            box-shadow: 10px 0 20px rgba(0, 0, 0, 0.4),
            inset 20px 0 50px rgba(0, 0, 0, 0.4);
            margin-left: 135px;
            
        }

        .book:hover .cover {
            transform: rotateY(-130deg);
            box-shadow: 10px 0 20px rgba(0, 0, 0, 0.4)
        }

        .content {
            width: 100%;
            height: 100%;
            padding: 1rem;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            font-size: 10px;
        }

        .content p {
            text-overflow: ellipsis;
        }
    }
</style>