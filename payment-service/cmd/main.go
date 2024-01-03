package main

import (
	"fmt"
	"log"
	"net/http"
	"os"

	"payment-service/internal/handler"
)

func main() {
	// Set up the HTTP server
	mux := http.NewServeMux()

	// Attach payment handlers
	mux.HandleFunc("/v1/payment/intent", handler.CreatePaymentIntentHandler)
	mux.HandleFunc("/v1/payment/process", handler.MakePaymentHandler)

	// Load CORS configuration
	corsMux := handler.CorsMiddleware(mux)

	// Get the port from the environment or default to 8095
	port := os.Getenv("PORT")
	if port == "" {
		port = "8095"
		log.Printf("Defaulting to port %s", port)
	}

	// Start the server
	log.Printf("Listening on port %s", port)
	log.Fatal(http.ListenAndServe(fmt.Sprintf(":%s", port), corsMux))
}