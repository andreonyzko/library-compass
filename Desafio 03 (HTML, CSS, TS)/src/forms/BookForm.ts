import Component from "../base/Component";
import BookService from "../services/BookService";

import { loadBooksPage } from "../main";
import type { BookType } from "../services/Types";

export default
    class BookForm extends Component {
    private form: HTMLFormElement;
    private titleInput: HTMLInputElement;
    private authorInput: HTMLInputElement;
    private yearInput: HTMLInputElement;

    constructor(private bookData?: BookType) {
        super('book-form-template');

        this.form = this.element.querySelector('form')! as HTMLFormElement;
        this.titleInput = this.form.querySelector('#book-title')! as HTMLInputElement;
        this.authorInput = this.form.querySelector('#book-author')! as HTMLInputElement;
        this.yearInput = this.form.querySelector('#book-year')! as HTMLInputElement;

        if (bookData) this.renderContent();
        this.configure();
    }

    private renderContent() {
        const titleInput = this.element.querySelector('#book-title')! as HTMLInputElement;
        const authorInput = this.element.querySelector('#book-author')! as HTMLInputElement;
        const yearInput = this.element.querySelector('#book-year')! as HTMLInputElement;

        titleInput.value = this.bookData!.title;
        authorInput.value = this.bookData!.author;
        yearInput.value = this.bookData!.yearPublication.toString();
    }

    private configure() {
        this.form.addEventListener('submit', async e => {
            e.preventDefault();

            const title = this.titleInput.value;
            const author = this.authorInput.value;
            const yearPublication = this.yearInput.value;
            const bookJSON = JSON.stringify({title, author, yearPublication});

            if (this.bookData) {
                await BookService.update(this.bookData.id, bookJSON);
                loadBooksPage();
            }
            else {
                await BookService.register(bookJSON);
                loadBooksPage();
            }
        })
    }
}