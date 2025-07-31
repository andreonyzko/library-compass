import Component from "../base/Component";
import BookLoaned from "../models/BookLoaned";
import UserService from "../services/UserService";
import { loadUsersPage } from "../main";
import type { BookType } from "../services/Types";
import { AutoBind } from "../utils/Autobind";
import { showErrorMsg } from "../utils/Feedback";

export default class UserLoans extends Component {
    private loans: BookType[] = [];
    private userId: number

    constructor(userId: number) {
        super('books-template');
        this.userId = userId;

        this.getLoans();
        window.addEventListener('search', (e) => this.filter(e))
    }

    // Get all books loaned by user
    private async getLoans() {
        try {
            this.loans = await UserService.getLoans(this.userId);
            this.renderLoans(this.loans);
        }
        catch (error) {
            showErrorMsg((error as Error).message);
        }
    }

    // Filter books loaned by title when search input entered
    private filter(e: Event){
        const query = (e as CustomEvent).detail;
        const result = this.loans.filter(book => book.title.toLowerCase().includes(query));
        this.renderLoans(result);
    }

    // Render books loaned
    private renderLoans(loans: BookType[]) {
        if (loans.length === 0) {
            this.element.innerHTML = '<p class="load-message">No books found!</p>';
            return;
        };

        this.element.innerHTML = '';
        loans.forEach(book => {
            new BookLoaned(this.element, book, this.onGiveback);
        })
    }

    // Function called after return a book loaned
    @AutoBind
    private async onGiveback(){
        try {
            const updatedLoans = await UserService.getLoans(this.userId);
            if(updatedLoans.length > 0){
                this.renderLoans(updatedLoans); // If there isn't more loans, redirect users page
            }
            else{
                loadUsersPage(); // Reload books loaned page
            }
        }
        catch (error) {
            showErrorMsg((error as Error).message);
        }
    }
}