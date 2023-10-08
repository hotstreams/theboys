import config from '../../config.js'
import { getAuthHeader } from './Auth'

export async function getMedicines() {
    try {
        const response = await fetch(config.host + '/medicines', {
            method: 'GET',
            headers: {
                "Accept": "application/json",
                'Authorization': getAuthHeader() ?? ''
            }
        });

        if (response.ok) {
            return {
                error: false,
                medicines: await response.json()
            }
        } else {
            return {
                error: true,
                errorMessage: 'Error occured during getting medicines'
            }
        }
    } catch (ex) {
        console.log(ex)
        return {
            error: true,
                errorMessage: 'Error occured during getting medicines'
        }
    }
}