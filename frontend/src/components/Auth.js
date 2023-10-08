import config from '../../config.js';

export async function authenticate(username, password) {
    try {
        const auth = 'Basic ' + btoa(username + ':' + password);

        const response = await fetch(config.host + '/users/' + username, {
            method: 'GET',
            headers: {
                'Authorization': auth
            }
        })
    
        if (response.ok) {
            localStorage.setItem('auth', auth)
            localStorage.setItem('user', JSON.stringify(await response.json()))
            return {
                status: true
            }
        } else {
            const body = await response.json()
            return {
                status: false,
                message: body.message
            }
        }
    } catch (error) {
        return {
            status: false,
            message: 'Error occured during sending request'
        }
    }
}

export function isAuthenticated() {
    if (localStorage.getItem('auth') != null) {
        return true
    } else {
        return false
    }
}

export function getAuthHeader() {
    return localStorage.getItem('auth')
}

export function signout() {
    localStorage.removeItem('auth')
    localStorage.removeItem('user')
}

export function getUser() {
    return JSON.parse(localStorage.getItem('user') ?? '{}')
}