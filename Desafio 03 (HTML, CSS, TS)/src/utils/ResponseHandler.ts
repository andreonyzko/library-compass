// Handles API response, converting to an error if necessary
export async function ResponseHandler(response: Response) {
    const content = response.headers.get('Content-Type');
    if (!response.ok) {
        let errorMsg = `Erro ${response.status}`

        if (content && content.includes('application/json')) {
            const body = await response.json();
            if (body.message) errorMsg += `: ${body.message}`
        }

        throw new Error(errorMsg);
    }

    if(content && content.includes('application/json')){
        return await response.json();
    }
    else{
        return await response.text();
    }
}

