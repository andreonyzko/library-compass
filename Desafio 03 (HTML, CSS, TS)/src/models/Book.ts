import Component from "../base/Component";
import Router from "../router/Router";
import BookForm from "../forms/BookForm";
import LoanForm from "../forms/LoanForm";
import BookService from "../services/BookService";
import { loadBooksPage } from "../main";
import type { BookType } from "../services/Types";
import { BookStatus } from "../utils/BookStatus";
import { showSucessMessage, showErrorMsg, showConfirmModal } from "../utils/Feedback";

export default class Book extends Component {
    private root: HTMLElement;
    protected bookData: BookType;
    protected giveBackBtn: HTMLButtonElement;
    protected loanBtn: HTMLButtonElement;
    protected editBtn: HTMLButtonElement;
    protected deleteBtn: HTMLButtonElement;

    constructor(root: HTMLElement, bookData: BookType) {
        super('book-template');
        this.root = root;
        this.bookData = bookData;

        // Get action buttons
        this.giveBackBtn = this.element.querySelector('.giveback-book-btn')! as HTMLButtonElement;
        this.loanBtn = this.element.querySelector('.loan-book-btn')! as HTMLButtonElement;
        this.editBtn = this.element.querySelector('.edit-book-btn')! as HTMLButtonElement;
        this.deleteBtn = this.element.querySelector('.delete-book-btn')! as HTMLButtonElement;

        this.renderContent();
    }

    // Fill in the fields with book data, and remove buttons according to the status
    protected renderContent() {
        this.element.querySelector('.book-title')!.textContent = this.bookData.title;
        this.element.querySelector('.book-author')!.textContent = this.bookData.author;
        this.element.querySelector('.book-year')!.textContent = this.bookData.yearPublication.toString();

        if (this.bookData.status === BookStatus.AVAILABLE) {
            this.element.querySelector('.book-status')!.textContent = 'AVAILABLE';
            this.giveBackBtn.remove(); // Remove return btn if book is available
        }
        else {
            this.element.querySelector('.book-status')!.textContent = 'LOANED';
            this.loanBtn.remove(); // Remove loan btn if book is loaned
            this.deleteBtn.remove(); // Loaned book can't be delete
        }

        this.configure();
    }

    // Listen to actions buttons and execute appropriate functionalities
    protected configure() {
        // Loan button
        if (this.loanBtn) {
            this.loanBtn.addEventListener('click', async () => {
                Router.render([new LoanForm(this.bookData.id)]);
            })
        }

        // Return button
        if (this.giveBackBtn) {
            this.giveBackBtn.addEventListener('click', async () => {
                try {
                    await BookService.giveback(this.bookData.id);
                    loadBooksPage();
                    showSucessMessage('Book returned successfully!');
                }
                catch (error) {
                    showErrorMsg((error as Error).message);
                }
            })
        }

        // Edit button
        this.editBtn.addEventListener('click', () => {
            Router.render([new BookForm(this.bookData)]);
        })

        // Delete button
        if (this.deleteBtn) {
            this.deleteBtn.addEventListener('click', async () => {
                try {
                    if (await showConfirmModal()) {
                        await BookService.delete(this.bookData.id);
                        loadBooksPage();
                        showSucessMessage('Book deleted successfully!');
                    }
                }
                catch (error) {
                    showErrorMsg((error as Error).message);
                }
            })
        }

        this.attach();
    }

    // Render element
    protected attach() {
        this.root.appendChild(this.element);
    }
}