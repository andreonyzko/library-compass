export default
class Router{
    static main = document.querySelector('main')! as HTMLElement;

    static render(elements: HTMLElement[]){
        this.clearMain();
        elements.forEach(element => {
            this.main.appendChild(element);
        })
    }

    static clearMain(){
        this.main.innerHTML = '';
    }
}