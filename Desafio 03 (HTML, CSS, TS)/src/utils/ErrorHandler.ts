export async function ResponseHandler(response: Response) {
    if (!response.ok) {
        const content = response.headers.get('Content-Type');
        let errorMsg = `Erro ${response.status}`

        if (content && content.includes('application/json')) {
            const body = await response.json();
            if (body.message) errorMsg += `: ${body.message}`
        }

        throw new Error(errorMsg);
    }

    return response.json();
}

export function showErrorMsg(message: string) {
    let errorElement = document.getElementById('error-msg');

    if (!errorElement) {
        errorElement = document.createElement('p');
        errorElement.id = 'error-msg'
    }

    errorElement.textContent = message;
    document.querySelector('main')!.prepend(errorElement);
}