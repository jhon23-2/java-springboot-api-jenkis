pipeline {
    agent any

    environment {
        DOCKER_IMAGE='maven:3.9-eclipse-temurin-17'
    }

    stages{
        stage("Executing maven project") {
            agent { # local agent execution should work only to executing maven project stage section 
                docker {
                    image "${DOCKER_IMAGE}"
                }
            }
            steps {
                sh "mvn --version"
                sh "mvn clean install -DskipTests"
            }
        }
    }
}
