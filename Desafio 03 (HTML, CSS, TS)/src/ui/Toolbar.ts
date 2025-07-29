import Component from "../base/Component";
import BookForm from "../forms/BookForm";
import UserForm from "../forms/UserForm";
import Router from "../router/Router";

export default
class ToolBar extends Component{
    private addBtn: HTMLButtonElement;

    constructor(private page: 'books'|'users'){
        super('toolbar-template');
        this.addBtn = this.element.querySelector('#add-btn')! as HTMLButtonElement;
        
        if(this.page === 'books') this.configureBooksPage();
        else if(this.page === 'users') this.configureUsersPage();
    }

    configureBooksPage(){
        this.addBtn.addEventListener('click', () => {
            Router.render([new BookForm().element]);
        });
    }

    configureUsersPage(){
        this.addBtn.addEventListener('click', () => {
            Router.render([new UserForm().element]);
        });
    }
}