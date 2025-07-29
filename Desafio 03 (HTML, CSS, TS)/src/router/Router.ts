export default
class Router{
    private main = document.querySelector('main')! as HTMLElement;

    render(elements: HTMLElement[]){
        this.clearMain();
        elements.forEach(element => {
            this.main.appendChild(element);
        })
    }

    clearMain(){
        this.main.innerHTML = '';
    }
}