package main

import (
	"bytes"
	"encoding/json"
	"fmt"
	"github.com/google/uuid"
	"log"
	"net/http"
	"os"
	"sync"
	"time"
)

const (
	WEBHOOK_URL = "http://localhost:8080/v1/payment/stripe/webhook"
)

type PaymentIntent struct {
	Price       int    `json:"price"`
	ProductId   string `json:"productId"`
	ProductName string `json:"productName"`
	Method      string `json:"method"`
	Currency    string `json:"currency"`
}

type PaymentRequest struct {
	Price        int    `json:"price"`
	ProductId    string `json:"productId"`
	ProductName  string `json:"productName"`
	Method       string `json:"method"`
	Currency     string `json:"currency"`
	ClientSecret string `json:"clientSecret"`
}

type PaymentIntentRespond struct {
	Price        int    `json:"price"`
	ProductId    string `json:"productId"`
	ClientSecret string `json:"clientSecret"`
}

type WebhookPayload struct {
	ID                 string    `json:"id"`
	Amount             int       `json:"amount"`
	ClientSecret       string    `json:"clientSecret"`
	ProductId          string    `json:"productId"`
	Created            time.Time `json:"created"`
	Currency           string    `json:"currency"`
	Description        string    `json:"description"`
	PaymentMethodTypes []string  `json:"paymentMethodTypes"`
	Status             string    `json:"status"`
}

var paymentIntents sync.Map

func corsMiddleware(next http.Handler) http.Handler {
	return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
		// Allow requests from any origin
		w.Header().Set("Access-Control-Allow-Origin", "*")

		// Allow common HTTP methods
		w.Header().Set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")

		// Allow common headers
		w.Header().Set("Access-Control-Allow-Headers", "Content-Type, Origin, Accept, Authorization")

		// Allow credentials (if needed)
		w.Header().Set("Access-Control-Allow-Credentials", "true")

		// Handle preflight requests
		if r.Method == "OPTIONS" {
			w.WriteHeader(http.StatusOK)
			return
		}

		// Continue with the next handler
		next.ServeHTTP(w, r)
	})
}

func main() {
	mux := http.NewServeMux()
	mux.HandleFunc("/v1/payment/intent", createPaymentIntentHandler)
	mux.HandleFunc("/v1/payment/process", makePaymentHandler)

	port := os.Getenv("PORT")
	if port == "" {
		port = "8095"
		log.Printf("Defaulting to port %s", port)
	}

	corsMux := corsMiddleware(mux)

	log.Printf("Listening on port %s", port)
	log.Printf("Open http://localhost:%s in the browser", port)
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%s", port), corsMux))
}

func createPaymentIntentHandler(w http.ResponseWriter, r *http.Request) {
	if r.Method != http.MethodPost {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}
	log.Printf("Received create payment intent request")
	var paymentIntent PaymentIntent

	err := json.NewDecoder(r.Body).Decode(&paymentIntent)
	if err != nil {
		http.Error(w, "Invalid JSON payload", http.StatusBadRequest)
		return
	}

	clientSecret := uuid.New().String()

	// Save payment intent in the map
	paymentIntents.Store(clientSecret, paymentIntent)

	// Return the client secret
	respond := PaymentIntentRespond{
		Price:        paymentIntent.Price,
		ProductId:    paymentIntent.ProductId,
		ClientSecret: clientSecret,
	}
	jsonRespond, err := json.Marshal(respond)
	w.Header().Set("Content-Type", "application/json")
	log.Printf("Sending respond with created payment intent for productId: %s", paymentIntent.ProductId)
	fmt.Fprintf(w, string(jsonRespond), clientSecret)
}

func makePaymentHandler(w http.ResponseWriter, r *http.Request) {
	log.Printf("Received request to process payment")
	if r.Method != http.MethodPost {
		http.Error(w, "Method not allowed", http.StatusMethodNotAllowed)
		return
	}

	var paymentRequest PaymentRequest

	err := json.NewDecoder(r.Body).Decode(&paymentRequest)
	if err != nil {
		http.Error(w, "Invalid JSON payload", http.StatusBadRequest)
		return
	}

	value, ok := paymentIntents.Load(paymentRequest.ClientSecret)
	if !ok {
		http.Error(w, "Payment intent not found", http.StatusNotFound)
		return
	}

	if value.(PaymentIntent).Price == paymentRequest.Price && value.(PaymentIntent).ProductId == paymentRequest.ProductId {
		go func() {
			webhookPayload := WebhookPayload{
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

			resp, err := http.Post(WEBHOOK_URL, "application/json", bytes.NewBuffer(webhookPayloadJSON))
			if err != nil {
				fmt.Printf("Webhook call failed: %v\n", err)
				return
			}
			defer resp.Body.Close()

			fmt.Println("Webhook call successful")
			fmt.Printf("Async Webhook Payload: %s\n", webhookPayloadJSON)
		}()
	}

	w.WriteHeader(http.StatusOK)
}
