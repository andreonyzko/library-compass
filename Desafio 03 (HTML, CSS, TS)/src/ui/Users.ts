import Component from "../base/Component";

import User from "../models/User";
import type { UserType } from "../services/Types";
import UserService from "../services/UserService";
import { AutoBind } from "../utils/Autobind";
import { showErrorMsg } from "../utils/ErrorHandler";

export default class Users extends Component {
    private users: UserType[] = [];

    constructor() {
        super('users-template');
        this.getUsers();
        window.addEventListener('search', (e) => this.filter(e));
    }

    private async getUsers() {
        try {
            this.users = await UserService.getAll();
            this.renderUsers(this.users);
        }
        catch (error) {
            showErrorMsg((error as Error).message);
        }
    }

    @AutoBind
    private filter(e: Event){
        const query = (e as CustomEvent).detail;
        const result = this.users.filter(user => user.name.toLowerCase().includes(query));
        this.renderUsers(result);
    }

    private renderUsers(users: any[]) {
        if (users.length === 0) {
            this.element.innerHTML= '<p class="load-message">No users found!</p>';
            return;
        };

        this.element.innerHTML = ''
        users.forEach(user => {
            new User(this.element, user);
        })
    }
}