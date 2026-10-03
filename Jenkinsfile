pipeline {
    agent any

    environment {
        MAVEN_IMAGE = 'maven:3.9-eclipse-temurin-17'
        IMAGE_NAME  = 'jhona1dev/animal-api'
    }

    stages {

        stage('Test and Build') {
            agent {
                docker {
                    image "${MAVEN_IMAGE}"
                    reuseNode true
                }
            }
            steps {
                sh 'mvn --version'
                sh 'mvn clean verify'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
                success {
                    echo 'Good and Done!'
                }
                failure {
                    echo 'Test and Build stage was wrong!'
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $IMAGE_NAME:latest .'
            }
        }

        // Docker push stages
    }
}