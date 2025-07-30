import Component from "../base/Component";
import BookLoaned from "../models/BookLoaned";
import type { BookType } from "../services/Types";
import UserService from "../services/UserService";
import { showErrorMsg } from "../utils/ErrorHandler";

export default
    class UserLoans extends Component {
    constructor(private userId: number) {
        super('books-template');
        this.getLoans();
    }

    async getLoans() {
        try {
            const loans = await UserService.getLoans(this.userId);
            if (loans.length > 0) this.renderLoans(loans);
        }
        catch (error) {
            showErrorMsg((error as Error).message);
        }
    }

    renderLoans(loans: BookType[]) {
        this.element.innerHTML = '';

        loans.forEach(book => {
            new BookLoaned(this.element, book);
        })
    }
}