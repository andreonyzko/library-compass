import type Component from "../base/Component";

export default class Router{
    static main = document.querySelector('main')! as HTMLElement;

    static render(components: Component[]){
        this.clearMain(); // Clear main content
        components.forEach(component => {
            this.main.appendChild(component.element);
        })
    }

    static clearMain(){
        this.main.innerHTML = '';
    }
}