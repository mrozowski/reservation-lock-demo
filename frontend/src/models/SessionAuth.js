class SessionAuth{
    constructor(authorization, sessionExpiration) {
        this.authorization = authorization;
        this.sessionExpiration = sessionExpiration;
    }
}

export default SessionAuth;