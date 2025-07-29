import Component from "../base/Component";
import BookForm from "../forms/BookForm";
import UserForm from "../forms/UserForm";
import Router from "../router/Router";

export default
class ToolBar extends Component{
    private router = new Router();
    private addBtn: HTMLButtonElement;

    constructor(private page: 'books'|'users'){
        super('toolbar-template');
        this.addBtn = this.element.querySelector('#add-btn')! as HTMLButtonElement;
        
        if(page === 'books') this.configureBooksPage();
        else if(page === 'users') this.configureUsersPage();
    }

    configureBooksPage(){
        this.addBtn.addEventListener('click', () => {
            this.router.render([new BookForm().element]);
        });
    }

    configureUsersPage(){
        this.addBtn.addEventListener('click', () => {
            this.router.render([new UserForm().element]);
        });
    }
}