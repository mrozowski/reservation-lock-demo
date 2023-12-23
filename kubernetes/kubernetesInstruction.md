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

4. Build the image of spring app using Dockerfile. 
```bash
 docker build seat-reservation/. -t mrozowski/reservation-backend-demo
```

5. Run all yaml configurations files inside chart directory
```bash
 kubectl apply -f charts/
```

6. Check if all pods are running
```bash
kubectl get pods 
```

7. You can forward port be able to call backend endpoints
```bash
kubectl port-forward reservation_image_name 8080:8080  
```

And that's it. Spring app and postgres should be running on the kubernetes cluster. 
With forwarded ports you should be able to use `Postmen` or `curl` to sent request from host to reservation app inside kubernetes cluster