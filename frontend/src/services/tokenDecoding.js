import { jwtDecode } from 'jwt-decode';

export function decodeToken(token) {
    try {
        const decoded = jwtDecode(token);
        return decoded;
    } catch (error) {
        console.error('Invalid token:', error);
        return null;
    }
}