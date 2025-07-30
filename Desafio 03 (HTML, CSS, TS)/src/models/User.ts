import Component from "../base/Component";
import Router from "../router/Router";

import UserForm from "../forms/UserForm";
import UserService from "../services/UserService";

import { loadUsersPage } from "../main";
import type { UserType } from "../services/Types";
import Title from "../ui/Title";
import UserLoans from "../ui/UserLoans";
import { showErrorMsg } from "../utils/ErrorHandler";
import Feedback from "../utils/Feedback";

export default
    class User extends Component {
    private loansBtn: HTMLButtonElement;
    private editBtn: HTMLButtonElement;
    private deleteBtn: HTMLButtonElement;

    constructor(
        private root: HTMLElement,
        private userData: UserType,
    ) {
        super('user-template');
        this.loansBtn = this.element.querySelector('.user-loans-btn')! as HTMLButtonElement;
        this.editBtn = this.element.querySelector('.edit-user-btn')! as HTMLButtonElement;
        this.deleteBtn = this.element.querySelector('.delete-user-btn')! as HTMLButtonElement;

        this.renderContent();
    }

    async renderContent() {
        this.element.querySelector('.username')!.textContent = this.userData.name;

        const loansAmount = (await UserService.getLoans(this.userData.id)).length;
        if (loansAmount > 0) {
            this.deleteBtn.remove();
        }
        else {
            this.loansBtn.remove();
        }

        this.configure();
    }

    configure() {
        if (this.loansBtn) {
            this.loansBtn.addEventListener('click', () => {
                Router.render([
                    new Title(`${this.userData.name}'s Loans`).element,
                    new UserLoans(this.userData.id).element
                ])
            })
        }

        this.editBtn.addEventListener('click', () => {
            Router.render([new UserForm(this.userData).element]);
        })

        if (this.deleteBtn) {
            this.deleteBtn.addEventListener('click', async () => {
                try {
                    await UserService.delete(this.userData.id);
                    loadUsersPage();
                    Feedback('User deleted successfully!');
                }
                catch (error) {
                    showErrorMsg((error as Error).message);
                }
            })
        }

        this.attach();
    }

    attach() {
        this.root.appendChild(this.element);
    }
}