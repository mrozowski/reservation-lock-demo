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
    },

    extractDateAndTime: (dateTimeString) => {
        const dateTime = new Date(dateTimeString);

        // Extract date components
        const year = dateTime.getFullYear();
        const month = dateTime.getMonth() + 1; // Months are zero-based
        const day = dateTime.getDate();

        // Extract time components
        const hours = dateTime.getHours();
        const minutes = dateTime.getMinutes();

        // Format the date and time
        const formattedDate = `${day < 10 ? '0' + day : day}.${month < 10 ? '0' + month : month}.${year}`;
        const formattedTime = `${hours < 10 ? '0' + hours : hours}:${minutes < 10 ? '0' + minutes : minutes}`;

        return { formattedDate, formattedTime };
    }
};

export default TextFormatter;