import Component from "../base/Component";

import Book from "../models/Book";
import BookService from "../services/BookService";
import type { BookType } from "../services/Types";
import { AutoBind } from "../utils/Autobind";
import { showErrorMsg } from "../utils/ErrorHandler";

export default class Books extends Component {
    private books: BookType[] = [];

    constructor() {
        super('books-template');
        this.getBooks();
        window.addEventListener('search', e => this.filter(e))
    }

    private async getBooks() {
        try {
            this.books = await BookService.getAll();
            this.renderBooks(this.books);
        }
        catch (error) {
            showErrorMsg((error as Error).message);
        }
    }

    @AutoBind
    private filter(e: Event) {
        const query = (e as CustomEvent).detail;
        const result = this.books.filter(book => book.title.toLowerCase().includes(query));
        this.renderBooks(result);
    }

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