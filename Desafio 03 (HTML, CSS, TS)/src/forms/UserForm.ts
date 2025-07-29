import Component from "../base/Component";

import UserService from "../services/UserService";

import { loadUsersPage } from "../main";
import type { UserType } from "../services/Types";

export default
class UserForm extends Component{
    private form: HTMLFormElement;
    private nameInput: HTMLInputElement;

    constructor(private userData?: UserType){
        super('user-form-template');

        this.form = this.element.querySelector('form')! as HTMLFormElement;
        this.nameInput = this.form.querySelector('#username')! as HTMLInputElement;

        this.configure();
        if(userData) this.renderContent();
    }

    renderContent(){
        const nameInput = this.element.querySelector('#username')! as HTMLInputElement;
        nameInput.value = this.userData!.name;
    }

    configure(){
        this.form.addEventListener('submit', async e => {
            e.preventDefault();

            const name = this.nameInput.value;
            const userJSON = JSON.stringify({name});
           
            if(this.userData){
                await UserService.update(this.userData.id, userJSON);
                loadUsersPage();
            }
            else{
                await UserService.register(userJSON);
                loadUsersPage();
            }
        })
    }
}