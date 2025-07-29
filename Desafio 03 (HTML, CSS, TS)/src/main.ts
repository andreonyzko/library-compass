// Import Nav and Router
import Nav from "./ui/Nav";
import Router from "./router/Router";

// Import UIs
import Books from "./ui/Books";
import Users from "./ui/Users";
import ToolBar from "./ui/Toolbar";

// Instantiate router
const router = new Router;

// First render -> library page
router.render([new ToolBar().element, new Books().element]);

// Instantiate nav passing function
new Nav((route) => {
  switch (route) {
    case 'library':
      router.render([new ToolBar().element, new Books().element]);
      break;
  
    case 'users':
      router.render([new ToolBar().element, new Users().element]);
      break;
  }
});