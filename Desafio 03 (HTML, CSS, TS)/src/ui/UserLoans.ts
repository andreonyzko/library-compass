import Component from "../base/Component";
import BookLoaned from "../models/BookLoaned";
import type { BookType } from "../services/Types";
import UserService from "../services/UserService";
import { showErrorMsg } from "../utils/ErrorHandler";

export default class UserLoans extends Component {
    private loans: BookType[] = [];

    constructor(private userId: number) {
        super('books-template');
        this.getLoans();
        window.addEventListener('search', (e) => this.filter(e))
    }

    private async getLoans() {
        try {
            this.loans = await UserService.getLoans(this.userId);
            this.renderLoans(this.loans);
        }
        catch (error) {
            showErrorMsg((error as Error).message);
        }
    }

    private filter(e: Event){
        const query = (e as CustomEvent).detail;
        const result = this.loans.filter(book => book.title.toLowerCase().includes(query));
        this.renderLoans(result);
    }

    private renderLoans(loans: BookType[]) {
        if (loans.length === 0) {
            this.element.innerHTML = '<p class="load-message">No books found!</p>';
            return;
        };
        
        this.element.innerHTML = '';
        loans.forEach(book => {
            new BookLoaned(this.element, book);
        })
    }
}