import Component from "../base/Component";
import Book from "../models/Book";
import BookService from "../services/BookService";
import type { BookType } from "../services/Types";
import { AutoBind } from "../utils/Autobind";
import { BookStatus } from "../utils/BookStatus";
import { showErrorMsg } from "../utils/Feedback";

export default class Books extends Component {
    private books: BookType[] = [];

    constructor() {
        super('books-template');
        this.getBooks();
        window.addEventListener('search', e => this.filter((e as CustomEvent).detail))
    }

    // Get all book from API
    private async getBooks() {
        try {
            this.books = await BookService.getAll();
            this.renderBooks(this.books);
        }
        catch (error) {
            showErrorMsg((error as Error).message);
        }

        this.configureFilters();
    }

    // Listen to checkboxes status filters
    @AutoBind
    private configureFilters() {
        const availableCheckbox = document.querySelector('#filter-available') as HTMLInputElement;
        const loanedCheckbox = document.querySelector('#filter-loaned') as HTMLInputElement;

        [availableCheckbox, loanedCheckbox].forEach(checkbox => {
            checkbox.addEventListener('change', () => this.filter())
        })
    }

    // Filter by title and status based on checkboxes and search inputs
    @AutoBind
    private filter(query = '') {
        let result = this.books;

        if (query.trim() !== '') {
            result = result.filter(book => book.title.toLowerCase().includes(query));
        }

        let availableCheckbox = document.querySelector('#filter-available') as HTMLInputElement;
        let loanedCheckbox = document.querySelector('#filter-loaned') as HTMLInputElement;
        if (availableCheckbox && loanedCheckbox) {
            const availableFilter = availableCheckbox.checked;
            const loanedFilter = loanedCheckbox.checked;

            result = result.filter(book => {
                if (book.status === BookStatus.AVAILABLE && availableFilter) return true;
                if (book.status === BookStatus.LOANED && loanedFilter) return true;
                return false;
            });
        }

        this.renderBooks(result);
    }

    // Display books at screen
    private renderBooks(books: BookType[]) {
        if (books.length === 0) {
            this.element.innerHTML = '<p class="load-message">No books found!</p>';
            return;
        };

        this.element.innerHTML = ''
        books.forEach(book => {
            new Book(this.element, book);
        })
    }
}