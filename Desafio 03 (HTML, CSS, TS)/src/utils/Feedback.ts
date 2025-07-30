export default function (message: string) {
    let feedbackElement = document.getElementById('feedback-msg');

    if (!feedbackElement) {
        feedbackElement = document.createElement('p');
        feedbackElement.id = 'feedback-msg'
    }

    feedbackElement.textContent = message;
    document.querySelector('main')!.prepend(feedbackElement);

    setTimeout(() => {
        feedbackElement.remove();
    }, 2500)
}