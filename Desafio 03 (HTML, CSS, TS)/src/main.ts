import '../styles/style.css';

import Nav from "./ui/Nav";
import Router from "./router/Router";

import Title from "./ui/Title";
import ToolBar from "./ui/Toolbar";
import Books from "./ui/Books";
import Users from "./ui/Users";

// By default load books page
loadBooksPage();

// Start navegation menu
new Nav();

// Render books page
export function loadBooksPage() {
  Router.render([new Title('Library'), new ToolBar('books'), new Books()]);
}

// Render users page
export function loadUsersPage() {
  Router.render([new Title('Users'), new ToolBar('users'), new Users()]);
}