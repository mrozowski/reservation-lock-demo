# Kubernetes Instruction

This instruction explains how to build reservation system on Kubernetes running locally on Linux machine.

## Prerequisites
- Minikube
- Kubectl
- Docker

## Instruction
1. Run minikube.
``` bash
 minikube start
```

2. Build the spring project
``` bash
 ./gradlew clean build
```

3. In your console run below command. This will point current console to minikube container
``` bash
 eval "$(minikube -p minikube docker-env)"
```

4. Build the images
```bash
 docker build seat-reservation/. -t mrozowski/reservation-backend-demo
 docker build frontend/. -t mrozowski/reservation-frontend
 docker build payment-service/. -t mrozowski/payment-service
 docker build kubernetes/nginx/. -t nginx
```

5. Run all yaml configurations files inside chart directory
```bash
 kubectl apply -f charts/
```

6. Check if all pods are running
```bash
kubectl get pods 
```

7. Get nginx URL and open it in your browser
```bash
minikube service nginx --url
```

The URL should be 192.168.49.2:31000

And that's it. Front and backends should be running on the kubernetes cluster. 
You can run above URL in your browser to access the frontend.
You should also be able to use `Postmen` or `curl` to sent request from host to services inside kubernetes cluster.