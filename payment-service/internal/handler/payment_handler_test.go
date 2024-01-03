package handler

import (
	"bytes"
	"encoding/json"
	"io"
	"net/http"
	"net/http/httptest"
	"payment-service/internal/model"
	"payment-service/internal/repository"
	"testing"
)

func TestMakePaymentHandler(t *testing.T) {
	// Mock repository for testing
	server := httptest.NewServer(http.HandlerFunc(MakePaymentHandler))

	clientSecret := "testClientSecret"
	paymentIntent := model.PaymentIntent{
		Price: 100,
		ProductId: "testProduct",
		Currency: "USD",
		Method: "card",
		ProductName: "testProductName",
	}
	repository.SavePaymentIntent(clientSecret, paymentIntent)


	paymentRequest := model.PaymentRequest{
		Price:        100,
		ProductId:    "testProduct",
		ClientSecret: clientSecret,
		Currency:     "USD",
	}

	requestBody, err := json.Marshal(paymentRequest)
	if err != nil {
		t.Fatal("Error encoding JSON:", err)
	}

	t.Logf("URL: %s", server.URL)
	resp, err := http.Post(server.URL, "application/json", bytes.NewBuffer(requestBody))

	if err != nil {
		t.Error("Failed to Post message", err)
	}

	if resp.StatusCode != http.StatusOK {
		body, err := io.ReadAll(resp.Body)
		t.Errorf("Expected 200 but got %d", resp.StatusCode)
		if err == nil {
			t.Errorf("Error Message: %s", body)
		}
	}
}

