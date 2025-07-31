import Component from "../base/Component";
import BookService from "../services/BookService";
import { loadBooksPage } from "../main";
import type { BookType } from "../services/Types";
import { showSucessMessage, showErrorMsg } from "../utils/Feedback";

export default class BookForm extends Component {
    private form: HTMLFormElement;
    private titleInput: HTMLInputElement;
    private authorInput: HTMLInputElement;
    private yearInput: HTMLInputElement;
    private bookData?: BookType;

    constructor(bookData?: BookType) {
        super('book-form-template');

        // Get inputs fields
        this.form = this.element.querySelector('form')! as HTMLFormElement;
        this.titleInput = this.form.querySelector('#book-title')! as HTMLInputElement;
        this.authorInput = this.form.querySelector('#book-author')! as HTMLInputElement;
        this.yearInput = this.form.querySelector('#book-year')! as HTMLInputElement;

        if (bookData) {
            this.bookData = bookData;
            this.renderContent();
        }

        this.configure();
    }

    // Fill in the fields if it is an edition
    private renderContent() {
        this.titleInput.value = this.bookData!.title;
        this.authorInput.value = this.bookData!.author;
        this.yearInput.value = this.bookData!.yearPublication.toString();
    }

    // Listen for form submit event and make API request
    private configure() {
        this.form.addEventListener('submit', async e => {
            try {
                e.preventDefault();

                const title = this.titleInput.value;
                const author = this.authorInput.value;
                const yearPublication = this.yearInput.value;
                this.validate(title, author, +yearPublication);

                // Create book object and parse to JSON
                const bookJSON = JSON.stringify({ title, author, yearPublication });

                // Register or update book according if bookData exists
                if (this.bookData) {
                    await BookService.update(this.bookData.id, bookJSON);
                    loadBooksPage();
                    showSucessMessage('Book updated successfully!');
                }
                else {
                    await BookService.register(bookJSON);
                    loadBooksPage();
                    showSucessMessage('Book registered successfully!');
                }
            }
            catch (error) {
                showErrorMsg((error as Error).message);
            }
        })
    }

    // Validate input data
    validate(title: string, author: string, yearPublication: number) {
        if (!title.trim()) throw new Error('Title is required');
        if (!author.trim()) throw new Error('Author is required');
        if (!yearPublication) throw new Error('Year of publication is required');

        // Check if publication year input is future date
        const currentYear = new Date().getFullYear();
        if (yearPublication > currentYear) throw new Error('The year of publication cannot be in the future');
    }
}