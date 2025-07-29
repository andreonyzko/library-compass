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
function loadBooksPage() {
  router.render([new ToolBar(loadBookForm).element, new Books(loadBooksPage).element]);
}

function loadUsersPage() {
  router.render([new ToolBar(loadUserForm).element, new Users().element]);
}

function loadBookForm(){
  router.render([new BookForm().element]);
}

function loadUserForm() {
  router.render([new UserForm().element])
}