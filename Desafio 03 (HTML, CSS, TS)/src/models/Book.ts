import Component from "../base/Component";
import Router from "../router/Router";

import BookForm from "../forms/BookForm";
import LoanForm from "../forms/LoanForm";
import BookService from "../services/BookService";

import { loadBooksPage } from "../main";
import type { BookType } from "../services/Types";
import { BookStatus } from "../utils/BookStatus";
import { showErrorMsg } from "../utils/ErrorHandler";


export default
    class Book extends Component {
    protected giveBackBtn: HTMLButtonElement;
    protected loanBtn: HTMLButtonElement;
    protected editBtn: HTMLButtonElement;
    protected deleteBtn: HTMLButtonElement;

    constructor(
        private root: HTMLElement,
        protected bookData: BookType,
    ) {
        super('book-template');
        this.giveBackBtn = this.element.querySelector('.giveback-book-btn')! as HTMLButtonElement;
        this.loanBtn = this.element.querySelector('.loan-book-btn')! as HTMLButtonElement;
        this.editBtn = this.element.querySelector('.edit-book-btn')! as HTMLButtonElement;
        this.deleteBtn = this.element.querySelector('.delete-book-btn')! as HTMLButtonElement;
        this.renderContent();
    }

    renderContent() {
        this.element.querySelector('.book-title')!.textContent = this.bookData.title;
        this.element.querySelector('.book-author')!.textContent = this.bookData.author;
        this.element.querySelector('.book-year')!.textContent = this.bookData.yearPublication.toString();

        if (this.bookData.status === BookStatus.AVAILABLE) {
            this.element.querySelector('.book-status')!.textContent = 'AVAILABLE';
            this.giveBackBtn.style.display = 'none';
        }
        else {
            this.element.querySelector('.book-status')!.textContent = 'LOANED';
            this.loanBtn.style.display = 'none';
            this.deleteBtn.style.display = 'none';
        }

        this.configure();
    }

    configure() {
        if (this.loanBtn.style.display !== 'none') {
            this.loanBtn.addEventListener('click', async () => {
                Router.render([new LoanForm(this.bookData.id).element]);
            })
        }

        if (this.giveBackBtn.style.display !== 'none') {
            this.giveBackBtn.addEventListener('click', async () => {
                try {
                    await BookService.giveback(this.bookData.id);
                    loadBooksPage();
                }
                catch (error) {
                    showErrorMsg((error as Error).message);
                }
            })
        }

        this.editBtn.addEventListener('click', () => {
            Router.render([new BookForm(this.bookData).element]);
        })

        if (this.deleteBtn.style.display !== 'none') {
            this.deleteBtn.addEventListener('click', async () => {
                try {
                    await BookService.delete(this.bookData.id);
                    loadBooksPage();
                }
                catch (error) {
                    showErrorMsg((error as Error).message);
                }
            })
        }

        this.attach();
    }

    attach() {
        this.root.appendChild(this.element);
    }
}