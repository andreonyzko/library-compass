import Component from "../base/Component";
import User from "../models/User";
import UserService from "../services/UserService";

export default
class Users extends Component{
    constructor(private loadUsersPage: () => void){
        super('users-template');
        this.getUsers();
    }

    private async getUsers(){
        const users = await UserService.getAll();
        this.renderUsers(users);
    }

    private renderUsers(users: any[]){
        if(users.length === 0){
            this.element.querySelector('.load-message')!.textContent = 'No users found';
            return;
        };

        this.element.innerHTML = ''
        users.forEach(user => {
            new User(this.element, user, this.loadUsersPage);
        })
    }
}