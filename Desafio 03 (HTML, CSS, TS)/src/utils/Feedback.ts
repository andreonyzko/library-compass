import Modal from "../models/Modal";

const main = document.querySelector('main')! as HTMLElement;

// Display sucess message at the main top for 2.5 seconds
export function showSucessMessage(message: string) {
    let feedbackElement = document.querySelector('feedback') as HTMLElement;

    if (!feedbackElement) {
        feedbackElement = document.createElement('p') as HTMLElement;
        feedbackElement.classList.add('feedback', 'sucess-msg');
    }

    feedbackElement.textContent = message;
    showFeedback(feedbackElement);
}

// Display visual error message at the main top for 5 seconds
export function showErrorMsg(message: string) {
    let feedbackElement = document.querySelector('feedback') as HTMLElement;

    if (!feedbackElement) {
        feedbackElement = document.createElement('p') as HTMLElement;
        feedbackElement.classList.add('feedback', 'error-msg');
    }

    feedbackElement.textContent = message;
    showFeedback(feedbackElement);
}

function showFeedback(feedbackElement: HTMLElement) {
    main.appendChild(feedbackElement);

    requestAnimationFrame(() => {
        feedbackElement.classList.add('show-feedback');
    })

    setTimeout(() => {
        feedbackElement.classList.remove('show-feedback');

        setTimeout(() => {
            feedbackElement.remove();
        }, 400)

    }, 3000)
}

export function showConfirmModal(): Promise<boolean>{
    return new Promise(resolve => {
        const modal = new Modal();
        modal.title.textContent = 'Are you sure?';
        modal.message.textContent = `This action can't be undoned. Please confirm if you want delete`

        main.appendChild(modal.element);

        modal.confirmBtn.addEventListener('click', () => {
            modal.element.remove();
            resolve(true);
        })

        modal.cancelBtn.addEventListener('click', () => {
            modal.element.remove();
            resolve(false);
        })
    })
}