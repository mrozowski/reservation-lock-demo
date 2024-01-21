package handler

import (
	"bytes"
	"encoding/json"
	"fmt"
	"github.com/google/uuid"
	"log"
	"net/http"
	"os"
	"time"

	"payment-service/internal/model"
	"payment-service/internal/repository"
)

const (
	endpoint = "v1/payment/stripe/webhook"
)

var WebhookBaseUrl string

func init() {
	WebhookBaseUrl = os.Getenv("PAYMENT_WEBHOOK_BASE_URL")
	if WebhookBaseUrl == "" {
		WebhookBaseUrl = "http://localhost:8080/"
	}
}

func CreatePaymentIntentHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		handleError(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	log.Printf("Received create payment intent request")
	var paymentIntent model.PaymentIntent

	err := json.NewDecoder(r.Body).Decode(&paymentIntent)
	if err != nil {
		handleError(w, "Invalid JSON payload", http.StatusBadRequest)
		return
	}

	clientSecret := uuid.New().String()

	repository.SavePaymentIntent(clientSecret, paymentIntent)

	respond := model.PaymentIntentRespond{
		Price:        paymentIntent.Price,
		ProductId:    paymentIntent.ProductId,
		ClientSecret: clientSecret,
	}
	jsonRespond, err := json.Marshal(respond)
	w.Header().Set("Content-Type", "application/json")
	log.Printf("Sending respond with created payment intent for productId: %s", paymentIntent.ProductId)
	fmt.Fprintf(w, string(jsonRespond), clientSecret)
}

// MakePaymentHandler handles the processing of payments.
func MakePaymentHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}
	log.Printf("Received request to process payment")

	var paymentRequest model.PaymentRequest

	err := json.NewDecoder(r.Body).Decode(&paymentRequest)
	if err != nil {
		handleError(w, "Invalid JSON payload", http.StatusBadRequest)
		return
	}

	// Load payment intent from the repository
	value, ok := repository.LoadPaymentIntent(paymentRequest.ClientSecret)
	if !ok {
		handleError(w, "Payment intent not found", http.StatusNotFound)
		return
	}

	if !isPaymentRequestValid(value, paymentRequest) {
		handleError(w, "Invalid payment request", http.StatusBadRequest)
		return
	}

	go sendWebhookAsync(paymentRequest)

	w.WriteHeader(http.StatusOK)
}

func handleError(w http.ResponseWriter, errorMsg string, statusCode int) {
	http.Error(w, errorMsg, statusCode)
}

func isPaymentRequestValid(savedPaymentIntent model.PaymentIntent, request model.PaymentRequest) bool {
	return savedPaymentIntent.Price == request.Price && savedPaymentIntent.ProductId == request.ProductId
}

func sendWebhookAsync(paymentRequest model.PaymentRequest) {
	webhookPayload := model.WebhookPayload{
		ID:                 uuid.New().String(),
		Amount:             paymentRequest.Price,
		ClientSecret:       paymentRequest.ClientSecret,
		ProductId:          paymentRequest.ProductId,
		Created:            time.Now(),
		Currency:           paymentRequest.Currency,
		Description:        "Created by stripe.com/docs demo",
		PaymentMethodTypes: []string{"card"},
		Status:             "succeeded",
	}

	webhookPayloadJSON, _ := json.Marshal(webhookPayload)

	resp, err := http.Post(WebhookBaseUrl+endpoint, "application/json", bytes.NewBuffer(webhookPayloadJSON))
	if err != nil {
		fmt.Printf("Webhook call failed: %v\n", err)
		return
	}
	defer resp.Body.Close()

	fmt.Println("Webhook call successful")
	fmt.Printf("Async Webhook Payload: %s\n", webhookPayloadJSON)
}
