import Nav from "./ui/Nav";
import Router from "./router/Router";
import Books from "./ui/Books";
import Users from "./ui/Users";
import ToolBar from "./ui/Toolbar";

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
  Router.render([new ToolBar('books').element, new Books().element]);
}

export function loadUsersPage() {
  Router.render([new ToolBar('users').element, new Users().element]);
}