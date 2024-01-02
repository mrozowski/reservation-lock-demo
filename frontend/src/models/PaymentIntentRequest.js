class PaymentIntentRequest{
    constructor(amount, currency, description, referenceNumber, productId, metadata) {
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.referenceNumber = referenceNumber;
        this.productId = productId;
        this.metadata = metadata;
    }
}

export default PaymentIntentRequest;