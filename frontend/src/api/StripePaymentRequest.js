class StripePaymentRequest{
    constructor(price, productId, productName, method, currency, clientSecret) {
        this.price = price;
        this.productId = productId;
        this.productName = productName;
        this.method = method;
        this.currency = currency;
        this.clientSecret = clientSecret;
    }
}

export default StripePaymentRequest;