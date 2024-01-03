package repository

import (
	"payment-service/internal/model"
	"sync"
)

var paymentIntents sync.Map

// SavePaymentIntent saves a payment intent in the repository.
func SavePaymentIntent(clientSecret string, paymentIntent model.PaymentIntent) {
	paymentIntents.Store(clientSecret, paymentIntent)
}

// LoadPaymentIntent loads a payment intent from the repository.
func LoadPaymentIntent(clientSecret string) (model.PaymentIntent, bool) {
	value, ok := paymentIntents.Load(clientSecret)
	if !ok {
		return model.PaymentIntent{}, false
	}
	return value.(model.PaymentIntent), true
}