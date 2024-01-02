const SESSION_AUTH_KEY = "session-auth"

const MemoryService = {

    /**
     * Store object in current session. The object will be removed after closing browser.
     *
     * @param {string} key - Name of the object to save.
     * @param {Object} object - Object to save in session memory
     */
    storeInSession: (key, object) => {
        const serializedObject = JSON.stringify(object);
        localStorage.setItem(key, serializedObject);
    },

    /**
     * Retrieve object from session with given key name.
     *
     * @param {string} key - Name of the object to retrieve.
     * @returns {Object|null} Returns Object that was stored in session under given key name
     */
    getFromSession: (key) => {
        const serializedObject = localStorage.getItem(key);
        return serializedObject ? JSON.parse(serializedObject) : null;
    },

    /**
     * Stores authentication information to session memory.
     *
     * @param {SessionAuth} auth Authentication information object.
     */
    storeSessionAuth: (auth) =>{
        MemoryService.storeInSession(SESSION_AUTH_KEY, auth);
    },

    /**
     * Retrieves authentication information from session memory.
     *
     * @returns {SessionAuth|null} The authentication information object or null if not found.
     */
    getSessionAuth: () =>{
       return MemoryService.getFromSession(SESSION_AUTH_KEY);
    }
}

export default MemoryService;