import config from '../../config.js'
import { getAuthHeader } from './Auth'

export async function getResearches() {
    try {
        const response = await fetch(config.host + '/researches', {
            method: 'GET',
            headers: {
                "Accept": "application/json",
                'Authorization': getAuthHeader() ?? ''
            }
        })

        if (response.ok) {
            return {
                error: false,
                reseaches: await response.json()
            }
        } else {
            return {
                error: true,
                errorMessage: 'Error occured during getting researches'
            }
        }
    } catch(ex) {
        console.log(ex)
        return {
            error: true,
            errorMessage: 'Error occured during getting researches'
        }
    }
}

export async function addResearch(candidateId: bigint, medicineId: bigint, dozeMl: bigint, dozeCount: bigint) {
    try {
        const response = await fetch(config.host + '/researches', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                'Authorization': getAuthHeader() ?? ''
            },
            body: JSON.stringify({
                candidateId,
                medicineId,
                dozeMl,
                dozeCount
            }),
        })

        if (response.ok) {
            return {
                error: false
            }
        } else {
            return {
                error: true,
                errorMessage: 'Error occured during adding research'
            }
        }
    } catch (ex) {
        console.log(ex)
        return {
            error: true,
            errorMessage: 'Error occured during adding research'
        }
    }
}

export async function saveResearch(researchId: bigint, status: string) {
    try {
        const response = await fetch(config.host + '/researches/' + researchId, {
            method: 'PATCH',
            headers: {
                "Content-Type": "application/json",
                'Authorization': getAuthHeader() ?? ''
            },
            body: JSON.stringify({
                status
            }),
        })

        if (response.ok) {
            return {
                error: false
            }
        } else {
            return {
                error: true,
                errorMessage: 'Error occured during saving research'
            }
        }
    } catch (ex) {
        console.log(ex)
        return {
            error: true,
            errorMessage: 'Error occured during saving research'
        }
    }
}