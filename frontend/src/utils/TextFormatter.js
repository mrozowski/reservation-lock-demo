const TextFormatter = {
    formatDate: (date) => {
        return new Date(date).toLocaleString([], {
            year: 'numeric',
            month: 'numeric',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        })
            .replaceAll("/", ".")
    },

    formatPrice: (amount, currency) => {
        const amountNumber = Number(amount) / 100
        return `${amountNumber.toFixed(2)} ${currency}`
    }
};

export default TextFormatter;