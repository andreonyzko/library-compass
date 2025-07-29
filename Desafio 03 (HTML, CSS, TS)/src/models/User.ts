import Component from "../base/Component";
import Router from "../router/Router";

import UserForm from "../forms/UserForm";
import UserService from "../services/UserService";

import { loadUsersPage } from "../main";
import type { UserType } from "../services/Types";

export default
class User extends Component{
    private loansBtn: HTMLButtonElement;
    private editBtn: HTMLButtonElement;
    private deleteBtn: HTMLButtonElement;

    constructor(
        private root: HTMLElement,
        private userData: UserType,
    ){
        super('user-template');
        this.loansBtn = this.element.querySelector('.user-loans-btn')! as HTMLButtonElement;
        this.editBtn = this.element.querySelector('.edit-user-btn')! as HTMLButtonElement;
        this.deleteBtn = this.element.querySelector('.delete-user-btn')! as HTMLButtonElement;

        this.renderContent();
    }

    async renderContent(){
        this.element.querySelector('.username')!.textContent = this.userData.name;

        const loansAmount = (await UserService.getLoans(this.userData.id)).length;
        if(loansAmount > 0){
            this.deleteBtn.style.display = 'none';
        }
        else{
            this.loansBtn.style.display = 'none'
        }

        this.configure();
    }

    configure(){
        this.editBtn.addEventListener('click', () => {
            Router.render([new UserForm(this.userData).element]);
        })

        if(this.deleteBtn.style.display !== 'none'){
            this.deleteBtn.addEventListener('click', async () => {
                await UserService.delete(this.userData.id);
                loadUsersPage();
            })
        }

        this.attach();
    }

    attach(){
        this.root.appendChild(this.element);
    }
}