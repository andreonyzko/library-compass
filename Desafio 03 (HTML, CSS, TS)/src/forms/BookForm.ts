import Component from "../base/Component";
import type { BookType } from "../services/BookService";

export default
class BookForm extends Component{
    constructor(private bookData?: BookType){
        super('book-form-template');
        if(bookData) this.renderContent();
    }

    private renderContent(){
        const titleInput = this.element.querySelector('#book-title')! as HTMLInputElement;
        const authorInput = this.element.querySelector('#book-author')! as HTMLInputElement;
        const yearInput = this.element.querySelector('#book-year')! as HTMLInputElement;

        titleInput.value = this.bookData!.title;
        authorInput.value = this.bookData!.author;
        yearInput.value = this.bookData!.yearPublication.toString();
    }
}