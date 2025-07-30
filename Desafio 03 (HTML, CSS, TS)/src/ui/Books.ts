import Component from "../base/Component";

import Book from "../models/Book";
import BookService from "../services/BookService";
import { showErrorMsg } from "../utils/ErrorHandler";

export default
    class Books extends Component {
    constructor() {
        super('books-template');
        this.getBooks();
    }

    private async getBooks() {
        try {
            const books = await BookService.getAll();
            this.renderBooks(books);
        }
        catch (error) {
            showErrorMsg((error as Error).message);
        }
    }

    private renderBooks(books: any[]) {
        if (books.length === 0) {
            this.element.querySelector('.load-message')!.textContent = 'No books found';
            return;
        };

        this.element.innerHTML = ''
        books.forEach(book => {
            new Book(this.element, book);
        })
    }
}