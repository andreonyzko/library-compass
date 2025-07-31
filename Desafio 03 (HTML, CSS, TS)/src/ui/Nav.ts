import { loadBooksPage, loadUsersPage } from "../main";
import { AutoBind } from "../utils/Autobind";

export default class Nav {
    private navElement: HTMLElement;
    private menuBtn: HTMLButtonElement;
    private navItems: NodeListOf<HTMLLIElement>;

    constructor() {
        this.navElement = document.querySelector('nav')! as HTMLElement;
        this.menuBtn = document.getElementById('menu-btn')! as HTMLButtonElement;
        this.navItems = document.querySelectorAll('.nav-item')! as NodeListOf<HTMLLIElement>;

        this.configure();
    }

    // Configure click events on nav items and toggle menu button
    private configure() {
        this.menuBtn.addEventListener('click', this.toggleMenu);
        this.navItems.forEach(li => {
            li.addEventListener('click', () => this.changePage(li));
        });
    }

    // Show/Hidden menu in small screen devices
    @AutoBind
    private toggleMenu() {
        if (this.navElement.style.display === 'none') this.navElement.style.display = 'block';
        else this.navElement.style.display = 'none';
    }

    // Change highlight nav item and load page
    private changePage(li: HTMLLIElement) {
        const previousPage = document.querySelector('.current-page')! as HTMLLIElement;
        previousPage.classList.remove('current-page');

        li.classList.add('current-page');

        const route = li.dataset.route!;
        switch (route) {
            case 'library':
                loadBooksPage();
                break;

            case 'users':
                loadUsersPage();
                break;
        }
    }
}