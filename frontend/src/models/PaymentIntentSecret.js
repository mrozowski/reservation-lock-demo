class PaymentIntentSecret{
    constructor(clientSecret, price, productId) {
        this.clientSecret = clientSecret;
        this.price = price;
        this.productId = productId;
    }
}

export default PaymentIntentSecret;