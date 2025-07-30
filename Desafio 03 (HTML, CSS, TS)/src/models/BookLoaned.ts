import Book from "./Book";
import BookService from "../services/BookService";

import { showErrorMsg } from "../utils/ErrorHandler";
import Feedback from "../utils/Feedback";
import type { BookType } from "../services/Types";

export default
class BookLoaned extends Book {
    constructor(
        root: HTMLElement,
        bookData: BookType,
        private onGiveback: () => void
    ){
        super(root, bookData);
    }

    renderContent(): void {
        super.renderContent();
        this.loanBtn.remove();
        this.editBtn.remove();
        this.deleteBtn.remove();
    }

    configure(): void {
        if (this.giveBackBtn) {
            this.giveBackBtn.addEventListener('click', async () => {
                try {
                    await BookService.giveback(this.bookData.id);
                    this.onGiveback();
                    Feedback('Book returned successfully!');
                }
                catch (error) {
                    showErrorMsg((error as Error).message);
                }
            })
        }

        this.attach();
    }
}