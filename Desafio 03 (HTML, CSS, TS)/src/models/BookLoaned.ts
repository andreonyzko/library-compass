import Book from "./Book";
import BookService from "../services/BookService";

import { loadUsersPage } from "../main";

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
                await BookService.giveback(this.bookData.id);
                loadUsersPage();
            })
        }

        this.attach();
    }
}