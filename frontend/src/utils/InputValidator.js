
const _nameRegex = /^[a-zA-Z]+$/;
const _phoneRegex = /^\d+$/;
const _emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

const InputValidator = {
    validateName: (value) => {
        return _nameRegex.test(value.trim());
    },

    validatePhone: (value) => {
        return _phoneRegex.test(value.trim());
    },

    validateEmail: (value) => {
        return _emailRegex.test(value.trim());
    }
}

export default InputValidator;