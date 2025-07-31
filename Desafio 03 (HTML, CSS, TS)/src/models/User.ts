import Component from "../base/Component";
import Router from "../router/Router";
import Title from "../ui/Title";
import ToolBar from "../ui/Toolbar";
import UserForm from "../forms/UserForm";
import UserService from "../services/UserService";
import UserLoans from "../ui/UserLoans";
import { loadUsersPage } from "../main";
import type { UserType } from "../services/Types";
import { showSucessMessage ,showErrorMsg } from "../utils/Feedback";

export default class User extends Component {
    private root: HTMLElement;
    private userData: UserType;
    private loansBtn: HTMLButtonElement;
    private editBtn: HTMLButtonElement;
    private deleteBtn: HTMLButtonElement;

    constructor(root: HTMLElement, userData: UserType) {
        super('user-template');
        this.root = root;
        this.userData = userData;

        // Get actions buttons
        this.loansBtn = this.element.querySelector('.user-loans-btn')! as HTMLButtonElement;
        this.editBtn = this.element.querySelector('.edit-user-btn')! as HTMLButtonElement;
        this.deleteBtn = this.element.querySelector('.delete-user-btn')! as HTMLButtonElement;

        this.renderContent();
    }

    // Fill in the username field and decide which buttons will be displayed according to the user loans
    private async renderContent() {
        this.element.querySelector('.username')!.textContent = this.userData.name;

        const loansAmount = (await UserService.getLoans(this.userData.id)).length;
        if (loansAmount > 0) {
            this.deleteBtn.remove(); // Can't delete who has loan
        }
        else {
            this.loansBtn.remove(); // Can't see loans if there's anyone
        }

        this.configure();
    }

    // Listen to action buttons
    private configure() {
        // See loans button
        if (this.loansBtn) {
            this.loansBtn.addEventListener('click', () => {
                Router.render([
                    new Title(`${this.userData.name}'s Loans`),
                    new ToolBar('books'),
                    new UserLoans(this.userData.id)
                ])

                // Remove toolbar elements which that dont make sense
                document.querySelector('#add-btn')!.remove();
                document.querySelector('#filter-status')!.remove();
            })
        }

        // Edit button
        this.editBtn.addEventListener('click', () => {
            Router.render([new UserForm(this.userData)]);
        })

        // Delete button
        if (this.deleteBtn) {
            this.deleteBtn.addEventListener('click', async () => {
                try {
                    await UserService.delete(this.userData.id);
                    loadUsersPage();
                    showSucessMessage('User deleted successfully!');
                }
                catch (error) {
                    showErrorMsg((error as Error).message);
                }
            })
        }

        this.attach();
    }

    // Render element
    private attach() {
        this.root.appendChild(this.element);
    }
}