pipeline {
    agent any

    environment {
        DOCKER_IMAGE='maven:3.9-eclipse-temurin-17'
    }

    stages{
        stage("Executing maven project") {
            agent {
                docker {
                    image "${DOCKER_IMAGE}"
                }
            }
            steps {
                sh "mvn --version"
                sh "mvn clean install -DskipTests"
            }
        }

        stage("Executing some command") {
            steps {
                sh "mvn --version"
            }
            post {
                success {
                    echo "Good!"
                }
                failure {
                    echo "Something was wrong with {mvn --version} command"
                }
            }
        }
    }
}
