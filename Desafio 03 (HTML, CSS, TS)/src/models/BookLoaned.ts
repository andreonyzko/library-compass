import Book from "./Book";
import BookService from "../services/BookService";
import { showSucessMessage, showErrorMsg } from "../utils/Feedback";
import type { BookType } from "../services/Types";

export default class BookLoaned extends Book {
    private onGiveback: () => void; // Callback after return

    constructor(root: HTMLElement, bookData: BookType, onGiveback: () => void ){
        super(root, bookData);
        this.onGiveback = onGiveback;
    }

    // Remove actions buttons: loan, edit and delete
    protected renderContent(): void {
        super.renderContent();
        this.loanBtn.remove();
        this.editBtn.remove();
        this.deleteBtn.remove();
    }

    // Listen to book return btn and make API request
    protected configure(): void {
        if (this.giveBackBtn) {
            this.giveBackBtn.addEventListener('click', async () => {
                try {
                    await BookService.giveback(this.bookData.id);
                    this.onGiveback();
                    showSucessMessage('Book returned successfully!');
                }
                catch (error) {
                    showErrorMsg((error as Error).message);
                }
            })
        }

        this.attach();
    }
}