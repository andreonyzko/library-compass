import Component from "../base/Component";
import BookForm from "../forms/BookForm";
import LoanForm from "../forms/LoanForm";
import Router from "../router/Router";
import type { BookType } from "../services/BookService";
import { BookStatus } from "../utils/BookStatus";

export default
class Book extends Component{
    private router = new Router();
    private giveBackBtn: HTMLButtonElement;
    private loanBtn: HTMLButtonElement;
    private editBtn: HTMLButtonElement;
    private deleteBtn: HTMLButtonElement;
    
    constructor(
        private root: HTMLElement,
        private bookData: BookType
    ){
        super('book-template');
        this.giveBackBtn = this.element.querySelector('.giveback-book-btn')! as HTMLButtonElement;
        this.loanBtn = this.element.querySelector('.loan-book-btn')! as HTMLButtonElement;
        this.editBtn = this.element.querySelector('.edit-book-btn')! as HTMLButtonElement;
        this.deleteBtn = this.element.querySelector('.delete-book-btn')! as HTMLButtonElement;
        this.renderContent();
    }

    renderContent(){
        this.element.querySelector('.book-title')!.textContent = this.bookData.title;
        this.element.querySelector('.book-author')!.textContent = this.bookData.author;
        this.element.querySelector('.book-year')!.textContent = this.bookData.yearPublication.toString();

        if(this.bookData.status === BookStatus.AVAILABLE){
            this.element.querySelector('.book-status')!.textContent = 'AVAILABLE';
            this.giveBackBtn.style.display = 'none';
        }
        else{
            this.element.querySelector('.book-status')!.textContent = 'LOANED';
            this.loanBtn.style.display = 'none';
            this.deleteBtn.style.display = 'none';
        }

        this.configure();
    }

    configure(){
        if(this.loanBtn.style.display !== 'none'){
            this.loanBtn.addEventListener('click', () => {
                this.router.render([new LoanForm().element]);
            })
        }

        this.editBtn.addEventListener('click', () => {
            this.router.render([new BookForm(this.bookData).element]);
        })

        this.attach();
    }

    attach(){
        this.root.appendChild(this.element);
    }
}