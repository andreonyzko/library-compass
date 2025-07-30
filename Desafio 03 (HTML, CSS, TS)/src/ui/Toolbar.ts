import Component from "../base/Component";
import Router from "../router/Router";

import BookForm from "../forms/BookForm";
import UserForm from "../forms/UserForm";

export default
class ToolBar extends Component{
    private searchInput: HTMLInputElement;
    private addBtn: HTMLButtonElement;

    constructor(private page: 'books'|'users'){
        super('toolbar-template');
        this.searchInput = this.element.querySelector('#search')! as HTMLInputElement;
        this.addBtn = this.element.querySelector('#add-btn')! as HTMLButtonElement;
        
        if(this.page === 'books') this.configureBooksPage();
        else if(this.page === 'users') this.configureUsersPage();

        this.configureSearch();
    }

    private configureBooksPage(){
        this.addBtn.addEventListener('click', () => {
            Router.render([new BookForm().element]);
        });
    }

    private configureUsersPage(){
        this.addBtn.addEventListener('click', () => {
            Router.render([new UserForm().element]);
        });
    }

    private configureSearch(){
        this.searchInput.addEventListener('input', (e: Event) => {
            const query = (e.target as HTMLInputElement).value.toLowerCase();
            window.dispatchEvent(new CustomEvent('search', { detail: query }))
        })
    }
}