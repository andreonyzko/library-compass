import Book from "./Book";
import BookService from "../services/BookService";

import { loadUsersPage } from "../main";
import { showErrorMsg } from "../utils/ErrorHandler";
import Feedback from "../utils/Feedback";

export default
    class BookLoaned extends Book {
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
                    loadUsersPage();
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