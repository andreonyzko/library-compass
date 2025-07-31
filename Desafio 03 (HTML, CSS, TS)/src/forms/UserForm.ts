import Component from "../base/Component";
import UserService from "../services/UserService";
import { loadUsersPage } from "../main";
import type { UserType } from "../services/Types";
import { showSucessMessage, showErrorMsg } from "../utils/Feedback";

export default class UserForm extends Component {
    private form: HTMLFormElement;
    private nameInput: HTMLInputElement;
    private userData?: UserType

    constructor(userData?: UserType) {
        super('user-form-template');

        // Get form and inputs elements
        this.form = this.element.querySelector('form')! as HTMLFormElement;
        this.nameInput = this.form.querySelector('#username')! as HTMLInputElement;

        if (userData) {
            this.userData = userData;
            this.renderContent();
        }

        this.configure();
    }

    // Fill in the fields if it is an edition
    private renderContent() {
        this.nameInput.value = this.userData!.name;
    }

    // Listen for form submit event and make API request
    private configure() {
        this.form.addEventListener('submit', async e => {
            try {
                e.preventDefault();

                const name = this.nameInput.value;
                this.validate(name);

                // Create user object and parse to JSON
                const userJSON = JSON.stringify({ name });

                // Register or update book according if userData exists
                if (this.userData) {
                    await UserService.update(this.userData.id, userJSON);
                    loadUsersPage();
                    showSucessMessage('User updated successfully!');
                }
                else {
                    await UserService.register(userJSON);
                    loadUsersPage();
                    showSucessMessage('User registered successfully!');
                }
            }
            catch (error) {
                showErrorMsg((error as Error).message);
            }
        })
    }

    // Validate input datas
    validate(username: string) {
        if (!username.trim()) throw new Error('Username is required');
    }
}