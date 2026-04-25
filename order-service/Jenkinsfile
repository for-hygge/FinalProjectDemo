pipeline {
    agent any

    environment {
        IMAGE_NAME = "order-service"
        CONTAINER_NAME = "order-service"
        PORT = "8080"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/for-hygge/order-service.git'
            }
        }

        stage('Build (Simulated)') {
            steps {
                echo 'Building project (simulated)'
            }
        }

        stage('Test (Simulated)') {
            steps {
                echo 'Running tests (simulated)'
            }
        }

        stage('Docker Build (Simulated)') {
            steps {
                echo "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Deploy (Simulated)') {
            steps {
                echo "docker stop ${CONTAINER_NAME} || true"
                echo "docker rm ${CONTAINER_NAME} || true"
                echo "docker run -d -p ${PORT}:${PORT} --name ${CONTAINER_NAME} ${IMAGE_NAME}"
            }
        }
    }
}