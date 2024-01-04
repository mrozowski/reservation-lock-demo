#!/usr/bin/env bash

set -eu -o pipefail

projdir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$projdir"

print() {
  echo "[$(date -u -Is)] $*"
}

err() {
  echo "[$(date -u -Is)] $*" >&2
}

display_prerequisite(){
  print "Required prerequisite:"
  print "1. Minikube"
  print "2. Kubectl"
  print "3. Docker"
}

validate_prerequisite() {
  if ! command -v minikube &> /dev/null
  then
      err "Minikube could not be found. Please install required prerequisite before running the script"
      display_prerequisite
      exit 8
  fi

  if ! command -v kubectl &> /dev/null
    then
        err "Kubectl could not be found. Please install required prerequisite before running the script"
        display_prerequisite
        exit 8
    fi
}

start_minikube() {
  set +e
  status=$(minikube status --format='{{.Kubelet}}' 2>&1)
  exit_code=$?

  if [[ "$status" = "Running" ]]; then
    print "Minikube cluster is already running. Skipping running minikube"
  else
    print "Starting minikube"
    minikube start
    print "Minikube is running"
  fi
  set -e
}

create_spring_app_image(){
  eval "$(minikube -p minikube docker-env)"
  print "Building reservation spring boot image in minikube cluster"
  docker build ../seat-reservation/. -t mrozowski/reservation-backend-demo
}

create_frontend_image(){
  eval "$(minikube -p minikube docker-env)"
  print "Building reservation react image in minikube cluster"
  docker build ../fronted/. -t mrozowski/reservation-frontend-demo
}

create_postgres_image(){
  if [[ "$(docker image inspect postgres:12 >/dev/null)" == "" ]]; then
    # Postgres image exist on host. Copy it to minikube instead of re-downloading it again
    docker save -o postgres-image-temp.tar postgres:12
    eval "$(minikube -p minikube docker-env)"
    docker load -i postgres-image-temp.tar
  fi
}

run_kubernetes_configuration_files() {
  kubectl apply -f charts/postgres-persistent-volume.yaml
  kubectl apply -f charts/postgres-volume-claim.yaml
  kubectl apply -f charts/postgres-deployment.yaml
  kubectl apply -f charts/postgres-service.yaml
  kubectl apply -f charts/config-map.yaml
  kubectl apply -f charts/reservation-backend-deployment.yaml
}

validate_prerequisite
start_minikube
create_spring_app_image
run_kubernetes_configuration_files
