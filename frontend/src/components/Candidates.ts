import config from '../../config.js'
import { getAuthHeader } from './Auth'

export async function downloadMedicalDocument(candidateId: bigint) {
    try {
        const response = await fetch(config.host + '/candidates/' + candidateId + '/medicalDocument', {
            method: 'GET',
            headers: {
                "Accept": "application/pdf",
                'Authorization': getAuthHeader() ?? ''
            }
        })

        if (response.ok) {
            let blob = await response.blob()
            var url = window.URL || window.webkitURL;
            let link = url.createObjectURL(blob);

            let a = document.createElement("a");
            a.setAttribute("download", 'Medical document.pdf');
            a.setAttribute("href", link);
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        } else {
            return {
                error: true,
                errorMessage: 'Error occured during downloading'
            }
        }
    } catch(ex) {
        console.log(ex)
        return {
            error: true,
            errorMessage: 'Error occured during downloading'
        }
    }
}

export async function saveCandidate(candidateId: bigint, status: string) {
    try {
        const response = await fetch(config.host + '/candidates/' + candidateId, {
            method: 'PATCH',
            headers: {
                "Content-Type": "application/json",
                'Authorization': getAuthHeader() ?? ''
            },
            body: JSON.stringify({
                status: status
            }),
        })

        if (response.ok) {
            return {
                error: false
            }
        } else {
            return {
                error: true,
                errorMessage: 'Error occured during save request'
            }
        }
    } catch (ex) {
        console.log(ex)
        return {
            error: true,
            errorMessage: 'Error occured during save request'
        }
    }
}

export async function getCandidates() {
    try {
        const response = await fetch(config.host + '/candidates', {
            method: 'GET',
            headers: {
                "Accept": "application/json",
                'Authorization': getAuthHeader() ?? ''
            }
        });

        if (response.ok) {
            return {
                error: false,
                candidates: await response.json()
            }
        } else {
            return {
                error: true,
                errorMessage: 'Error occured during getting candidates'
            }
        }
    } catch (ex) {
        console.log(ex)
        return {
            error: true,
            errorMessage: 'Error occured during getting candidates'
        }
    }
}