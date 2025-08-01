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
    document.querySelector('main')!.appendChild(feedbackElement);

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