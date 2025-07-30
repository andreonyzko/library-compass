import Component from "../base/Component";

import BookService from "../services/BookService";
import UserService from "../services/UserService";

import { loadBooksPage } from "../main";
import { showErrorMsg } from "../utils/ErrorHandler";
import Feedback from "../utils/Feedback";

export default
    class LoanForm extends Component {
    private form: HTMLFormElement;
    private userSelect: HTMLSelectElement;

    constructor(private bookId: number) {
        super('loan-form-template');
        this.form = this.element.querySelector('form')! as HTMLFormElement;
        this.userSelect = this.element.querySelector('select')! as HTMLSelectElement;

        this.renderContent();
    }

    async renderContent() {
        const users = await UserService.getAll();

        users.forEach(user => {
            const optionEl = document.createElement('option');
            optionEl.value = user.id.toString();
            optionEl.textContent = user.name;
            this.userSelect.appendChild(optionEl);
        });

        this.configure();
    }

    configure() {
        this.form.addEventListener('submit', async e => {
            try {
                e.preventDefault();
                const userId = +this.userSelect.value;

                await BookService.loan(this.bookId, userId);

                loadBooksPage();
                Feedback('Book loaned successfully!');
            }
            catch (error) {
                showErrorMsg((error as Error).message);
            }
        })
    }
}