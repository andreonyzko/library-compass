import Component from "../base/Component";

import User from "../models/User";
import UserService from "../services/UserService";
import { showErrorMsg } from "../utils/ErrorHandler";

export default
    class Users extends Component {
    constructor() {
        super('users-template');
        this.getUsers();
    }

    private async getUsers() {
        try {
            const users = await UserService.getAll();
            this.renderUsers(users);
        }
        catch (error) {
            showErrorMsg((error as Error).message);
        }
    }

    private renderUsers(users: any[]) {
        if (users.length === 0) {
            this.element.querySelector('.load-message')!.textContent = 'No users found';
            return;
        };

        this.element.innerHTML = ''
        users.forEach(user => {
            new User(this.element, user);
        })
    }
}