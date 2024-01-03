package model

import "time"

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

