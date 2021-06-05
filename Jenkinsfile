pipeline {
    agent any

    tools {
        jdk 'openjdk-11'
        maven 'maven3'
    }

    environment {
        APP_NAME="home-inc-api"
    }

    stages {
        stage('Cleanup Workspace'){
            steps {
                cleanWs()
                sh """
                echo 'Workspace cleaned for ${env.APP_NAME}'
                """
            }
        }

        stage('Checkout code') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/master']],
                    userRemoteConfigs: [[url: 'https://github.com/grglucastr/home-inc-api.git']]
                ])
            }
        }

        stage('Code build') {
            steps {
                sh 'mvn clean install -U'
            }
        }

        stage('Code test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}