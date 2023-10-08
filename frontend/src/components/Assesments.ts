import config from '../../config.js'
import { getAuthHeader } from './Auth'

export async function addAssesment(researchId: bigint, assessmentName: string) {
    try {
        const response = await fetch(config.host + '/researches/' + researchId + '/assessments', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                'Authorization': getAuthHeader() ?? ''
            },
            body: JSON.stringify({
                assessmentName
            }),
        })

        if (response.ok) {
            return {
                error: false
            }
        } else {
            return {
                error: true,
                errorMessage: 'Error occured during adding assessment'
            }
        }
    } catch (ex) {
        console.log(ex)
        return {
            error: true,
            errorMessage: 'Error occured during adding assessment'
        }
    }
}

export async function saveAssesment(researchId: bigint, assessmentId: bigint, eventDate: string, comment: string, passed: boolean) {
    try {
        const response = await fetch(config.host + '/researches/' + researchId + '/assessments/' + assessmentId, {
            method: 'PATCH',
            headers: {
                "Content-Type": "application/json",
                'Authorization': getAuthHeader() ?? ''
            },
            body: JSON.stringify({
                comment,
                eventDate,
                passed
            })
        });

        if (response.ok) {
            return {
                error: false
            }
        } else {
            return {
                error: true,
                errorMessage: 'Error occured during saving assessment'
            }
        }
    } catch (ex) {
        console.log(ex)
        return {
            error: true,
            errorMessage: 'Error occured during saving assessment'
        }
    }
}