import Nav from "./ui/Nav";
import Router from "./router/Router";
import Books from "./ui/Books";
import Users from "./ui/Users";
import ToolBar from "./ui/Toolbar";
import BookForm from "./forms/BookForm";
import UserForm from "./forms/UserForm";

const router = new Router;
loadBooksPage();

new Nav((route) => {
  switch (route) {
    case 'library':
      loadBooksPage();
      break;
  
    case 'users':
      loadUsersPage();
      break;
  }
});

// Functions
export function loadBooksPage() {
  router.render([new ToolBar('books').element, new Books().element]);
}

export function loadUsersPage() {
  router.render([new ToolBar('users').element, new Users().element]);
}