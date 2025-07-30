import Book from "./Book";
import BookService from "../services/BookService";

import { loadUsersPage } from "../main";
import { showErrorMsg } from "../utils/ErrorHandler";

export default
    class BookLoaned extends Book {
    renderContent(): void {
        super.renderContent();
        this.loanBtn.style.display = 'none';
        this.editBtn.style.display = 'none';
        this.deleteBtn.style.display = 'none';
    }

    configure(): void {
        if (this.giveBackBtn.style.display !== 'none') {
            this.giveBackBtn.addEventListener('click', async () => {
                try {
                    await BookService.giveback(this.bookData.id);
                    loadUsersPage();
                }
                catch (error) {
                    showErrorMsg((error as Error).message);
                }
            })
        }

        this.attach();
    }
}