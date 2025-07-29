import Component from "../base/Component";
import UserForm from "../forms/UserForm";
import Router from "../router/Router";
import type { UserType } from "../services/UserService";
import UserService from "../services/UserService";
import { loadUsersPage } from "../main";

export default
class User extends Component{
    private loansBtn: HTMLButtonElement;
    private editBtn: HTMLButtonElement;
    private deleteBtn: HTMLButtonElement;
    private router: Router;

    constructor(
        private root: HTMLElement,
        private userData: UserType,
    ){
        super('user-template');
        this.loansBtn = this.element.querySelector('.user-loans-btn')! as HTMLButtonElement;
        this.editBtn = this.element.querySelector('.edit-user-btn')! as HTMLButtonElement;
        this.deleteBtn = this.element.querySelector('.delete-user-btn')! as HTMLButtonElement;
        this.router = new Router();

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
            this.router.render([new UserForm(this.userData).element]);
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