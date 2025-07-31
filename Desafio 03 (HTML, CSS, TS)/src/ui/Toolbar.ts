import Component from "../base/Component";
import Router from "../router/Router";
import BookForm from "../forms/BookForm";
import UserForm from "../forms/UserForm";

export default class ToolBar extends Component {
    private searchInput: HTMLInputElement;
    private addBtn: HTMLButtonElement;
    private page: 'books' | 'users';

    constructor(page: 'books' | 'users') {
        super('toolbar-template');
        this.page = page;

        this.searchInput = this.element.querySelector('#search')! as HTMLInputElement;
        this.addBtn = this.element.querySelector('#add-btn')! as HTMLButtonElement;

        this.configure();
    }

    private configure() {
        // Cancel page reload when submit search form
        this.searchInput.addEventListener('submit', (e) => {
            e.preventDefault();
        })

        // Listen to search input and shoot custom event when entered
        this.searchInput.addEventListener('input', (e: Event) => {
            const query = (e.target as HTMLInputElement).value.toLowerCase();
            window.dispatchEvent(new CustomEvent('search', { detail: query }))
        })

        // Show book or user form when click "+" btn
        this.addBtn.addEventListener('click', () => {
            if(this.page === 'books') Router.render([new BookForm()]);
            else Router.render([new UserForm()]);
        });
        
        // Hidden status filter at users page
        if (this.page === 'users') {
            this.element.querySelector('#filter-status')!.remove();
        }
    }
}