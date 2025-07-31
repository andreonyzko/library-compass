import Component from "../base/Component";
import BookService from "../services/BookService";
import UserService from "../services/UserService";
import { loadBooksPage } from "../main";
import { showSucessMessage, showErrorMsg } from "../utils/Feedback";

export default class LoanForm extends Component {
    private bookId: number;
    private form: HTMLFormElement;
    private userSelect: HTMLSelectElement;

    constructor(bookId: number) {
        super('loan-form-template');
        this.bookId = bookId;

        // Get form and select elements
        this.form = this.element.querySelector('form')! as HTMLFormElement;
        this.userSelect = this.element.querySelector('select')! as HTMLSelectElement;

        this.renderContent();
    }

    // Populate select with all users registered
    private async renderContent() {
        const users = await UserService.getAll();

        users.forEach(user => {
            const optionEl = document.createElement('option');
            optionEl.value = user.id.toString();
            optionEl.textContent = user.name;
            this.userSelect.appendChild(optionEl);
        });

        this.configure();
    }

    // Listen for form submit event and make API request
    private configure() {
        this.form.addEventListener('submit', async e => {
            try {
                e.preventDefault();
                const userId = +this.userSelect.value;

                await BookService.loan(this.bookId, userId);

                loadBooksPage();
                showSucessMessage('Book loaned successfully!');
            }
            catch (error) {
                showErrorMsg((error as Error).message);
            }
        })
    }
}