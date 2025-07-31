// Display sucess message at the main top for 2.5 seconds
export function showSucessMessage(message: string) {
    let feedbackElement = document.getElementById('sucess-msg');

    if (!feedbackElement) {
        feedbackElement = document.createElement('p');
        feedbackElement.id = 'sucess-msg'
    }

    feedbackElement.textContent = message;
    document.querySelector('main')!.prepend(feedbackElement);

    setTimeout(() => {
        feedbackElement.remove();
    }, 2500)
}

// Display visual error message at the main top for 5 seconds
export function showErrorMsg(message: string) {
    let errorElement = document.getElementById('error-msg');

    if (!errorElement) {
        errorElement = document.createElement('p');
        errorElement.id = 'error-msg'
    }

    errorElement.textContent = message;
    document.querySelector('main')!.prepend(errorElement);

    setTimeout(() => {
        errorElement.remove();
    }, 5000)
}