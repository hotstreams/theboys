import config from '../../config.js'
import { getAuthHeader } from './Auth'

export async function addTest(researchId: bigint, testName: string) {
    try {
        const response = await fetch(config.host + '/researches/' + researchId + '/tests', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
                'Authorization': getAuthHeader() ?? ''
            },
            body: JSON.stringify({
                testName
            }),
        })

        if (response.ok) {
            return {
                error: false
            }
        } else {
            return {
                error: true,
                errorMessage: 'Error occured during adding test'
            }
        }
    } catch (ex) {
        console.log(ex)
        return {
            error: true,
            errorMessage: 'Error occured during adding test'
        }
    }
}

export async function saveTest(researchId: bigint, testId: bigint, testResult: string, eventDate: string) {
    try {
        const response = await fetch(config.host + '/researches/' + researchId + '/tests/' + testId, {
            method: 'PATCH',
            headers: {
                "Content-Type": "application/json",
                'Authorization': getAuthHeader() ?? ''
            },
            body: JSON.stringify({
                testResult,
                eventDate
            })
        });

        if (response.ok) {
            return {
                error: false
            }
        } else {
            return {
                error: true,
                errorMessage: 'Error occured during saving test'
            }
        }
    } catch (ex) {
        console.log(ex)
        return {
            error: true,
            errorMessage: 'Error occured during saving test'
        }
    }
}