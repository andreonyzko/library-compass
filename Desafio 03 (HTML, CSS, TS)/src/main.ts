import '../styles/style.css';

import Nav from "./ui/Nav";
import Router from "./router/Router";

import Title from "./ui/Title";
import ToolBar from "./ui/Toolbar";
import Books from "./ui/Books";
import Users from "./ui/Users";

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
  Router.render([new Title('Library').element, new ToolBar('books').element, new Books().element]);
}

export function loadUsersPage() {
  Router.render([new Title('Users').element, new ToolBar('users').element, new Users().element]);
}