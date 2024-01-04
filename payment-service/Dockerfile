FROM golang:1.21

WORKDIR /app

COPY go.mod go.sum ./
RUN go mod download

COPY . .

# Build the Go application
RUN go build -o myapp ./cmd


# Command to run the executable
CMD ["./myapp"]
