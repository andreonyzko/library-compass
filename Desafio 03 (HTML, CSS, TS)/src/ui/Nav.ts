import { AutoBind } from "../utils/Autobind";

export default
class Nav{
    navElement: HTMLElement;
    menuBtn: HTMLButtonElement;
    navItems: NodeListOf<HTMLLIElement>;

    constructor(private onNavigate: (route: string) => void){
        this.navElement = document.querySelector('nav')! as HTMLElement;
        this.menuBtn = document.getElementById('menu-btn')! as HTMLButtonElement;
        this.navItems = document.querySelectorAll('.nav-item')! as NodeListOf<HTMLLIElement>;

        this.configure();
    }

    private configure(){
        this.menuBtn.addEventListener('click', this.toggleMenu);
        this.navItems.forEach(li => {
            li.addEventListener('click', () => this.changeCurrentPage(li));
        });
    }

    @AutoBind
    private toggleMenu(){
        if(this.navElement.style.display === 'none') this.navElement.style.display = 'block';
        else this.navElement.style.display = 'none';
    }

    private changeCurrentPage(li: HTMLLIElement){
        const previousPage = document.querySelector('.current-page')! as HTMLLIElement;
        previousPage.classList.remove('current-page');

        li.classList.add('current-page');

        const route = li.textContent!.trim().toLowerCase();
        this.onNavigate(route);
    }
}