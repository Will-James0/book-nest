import Admin from "@/components/Admin.vue";
import User from "@/components/User.vue";
import AddAuthor from "@/pages/admin/author/AddAuthor.vue";
import Author from "@/pages/admin/author/Author.vue";
import AuthorList from "@/pages/admin/author/AuthorList.vue";
import AddBook from "@/pages/admin/book/AddBook.vue";
import Book from "@/pages/admin/book/Book.vue";
import EditBook from "@/pages/admin/book/EditBook.vue";
import ListBook from "@/pages/admin/book/ListBook.vue";
import Dashboard from "@/pages/admin/Dashboard.vue";
import Home from "@/pages/Home.vue";
import Login from "@/pages/Login.vue";
import UserHome from "@/pages/user/UserHome.vue";
import authGuard from "@/_helper/auth-guard";
import EditAuthor from "@/pages/admin/author/EditAuthor.vue";

export default [
    {
        path: '/',
        component: Home,
        name: 'home',
    },
    {
        path: '/login',
        component: Login,
        name: 'login',
    },
    {
        path: '/admin',
        component: Admin,
        name: 'admin',
        beforeEnter: authGuard.adminGuard,
        children: [
            {
                path: '',
                component: Dashboard,
                name: 'admin.dashboard',
            },
            {
                path: 'authors',
                component: Author,
                name: 'admin.authors',
                children: [
                    {
                        path: '',
                        component: AuthorList,
                        name: 'admin.authors.list',
                    },
                    {
                        path: 'add',
                        component: AddAuthor,
                        name: 'admin.authors.add',
                    },
                    {
                        path: 'edit/:id(\\d+)',
                        component: EditAuthor,
                        name: 'admin.authors.edit',
                        props: true
                    }
                ]
            },
            {
                path: 'books',
                component: Book,
                name: 'admin.books',
                children: [
                    {
                        path: '',
                        component: ListBook,
                        name: 'admin.books.list',
                    },
                    {
                        path: 'add',
                        component: AddBook,
                        name: 'admin.books.add',
                    },
                    {
                        path: 'edit/:id(\\d+)',
                        component: EditBook,
                        name: 'admin.books.edit',
                        props: true
                    },
                ]
            },
            {
                path: '/:pathMatch(.*)*',
                redirect: '/admin'
            }
        ]
    },
    {
        path: '/user',
        component: User,
        name: 'user',
        beforeEnter: authGuard.authGuard,
        children: [
            {
                path: '',
                component: UserHome,
                name: 'user.home',
            },
        ]
    },
    {
        path: '/:pathMatch(.*)*',
        redirect: '/'
    }
]