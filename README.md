
# Microservices Deployment with Kubernetes

This project contains a set of microservices that have been containerized using Docker and are 
deployed to Kubernetes. The setup assumes you have a Kubernetes cluster running 
(e.g., using Minikube, Docker Desktop, or a cloud provider) and Docker images are either built 
locally or pulled from Docker Hub.

## Prerequisites

Before starting, ensure you have the following installed:

- [Docker](https://www.docker.com/get-started)
- [kubectl](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
- A Kubernetes cluster (Minikube, Docker Desktop, or a cloud-based Kubernetes cluster). I used Minikube

## 1. Set Up Kubernetes Cluster

If you're using **Minikube** or **Docker Desktop**, ensure that the Kubernetes cluster is running:

- For Minikube:

  ```bash
  minikube start

## 2.Create a Kubernetes Namespace

```bash
  kubectl create namespace my-app
```

## 3. Apply deployment files:
```bash
  kubectl apply -f k8s/deployments.yaml
  kubectl apply -f k8s/services.yaml
```

## 4. Monitor the deployment
```bash
  kubectl get pods -n my-app
  kubectl get services -n my-app
```

## 5. To access any service locally,, you can port-forward them:
```bash
kubectl port-forward svc/bookpersistence 8383:8181 -n my-app
