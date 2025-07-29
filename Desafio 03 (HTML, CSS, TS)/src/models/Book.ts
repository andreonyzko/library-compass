import Component from "../base/Component";
import type { BookType } from "../services/BookService";
import { BookStatus } from "../utils/BookStatus";

export default
class Book extends Component{
    constructor(
        public root: HTMLElement,
        public bookData: BookType
    ){
        super('book-template');
        this.renderContent();
    }

    renderContent(){
        this.element.querySelector('.book-title')!.textContent = this.bookData.title;
        this.element.querySelector('.book-author')!.textContent = this.bookData.author;
        this.element.querySelector('.book-year')!.textContent = this.bookData.yearPublication.toString();

        if(this.bookData.status === BookStatus.AVAILABLE){
            this.element.querySelector('.book-status')!.textContent = 'AVAILABLE';

            let givebackBtn = this.element.querySelector('.giveback-book-btn')! as HTMLButtonElement;
            givebackBtn.style.display = 'none';
        }
        else{
            this.element.querySelector('.book-status')!.textContent = 'LOANED';

            let loanBtn = this.element.querySelector('.loan-book-btn')! as HTMLButtonElement;
            loanBtn.style.display = 'none';

            let deleteBtn = this.element.querySelector('.delete-book-btn')! as HTMLButtonElement;
            deleteBtn.style.display = 'none';
        }

        this.attach();
    }

    attach(){
        this.root.appendChild(this.element);
    }
}