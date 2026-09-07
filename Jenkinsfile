pipeline {
    agent any

    environment {
        DOCKER_IMAGE='maven:3.9-eclipse-temurin-17'
    }

    stages{
        stage("Build") {
            agent { // local docker agent execution should work only into Build stage section
                docker {
                    image "${DOCKER_IMAGE}"
                }
            }
            steps {
                sh "mvn --version"
                sh "mvn clean install -DskipTests"
            }
            post {
                success{
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage("Test"){
            agent { // local docker agent execution should work only to into Test stage section
                docker {
                    image "${DOCKER_IMAGE}"
                }
            }
            steps {
                sh "mvn clean install"
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
    }
}
