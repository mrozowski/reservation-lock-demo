package repository

import (
	"payment-service/internal/model"
	"testing"
)

func TestSaveAndLoadPaymentIntent(t *testing.T) {
	clientSecret := "testClientSecret"
	testPaymentIntent := model.PaymentIntent{
		Price:       100,
		ProductId:   "testProduct",
		ProductName: "Test Product",
		Method:      "credit_card",
		Currency:    "USD",
	}

	SavePaymentIntent(clientSecret, testPaymentIntent)

	loadedIntent, ok := LoadPaymentIntent(clientSecret)

	if !ok {
		t.Errorf("Expected to load payment intent, but it was not found")
	}

	if loadedIntent != testPaymentIntent {
		t.Errorf("Loaded payment intent does not match the original")
	}
}