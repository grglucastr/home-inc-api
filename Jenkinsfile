pipeline {
    agent any

    tools {
        jdk 'openjdk-11'
        maven 'maven3'
    }

    environment {
        APP_NAME="home-inc-api"
        NODEJS_HOME = "${tool 'nodejs16'}"
        PATH="${env.NODEJS_HOME}/bin:${env.PATH}"
        ZIP_FILE_NAME="publish.zip"
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

        stage('Generate API documentation') {
            steps {
                sh 'npx redoc-cli bundle -o api.html ./src/main/resources/openapi.yaml'
            }
        }

        stage('Compress documentation') {
            steps {
                zip zipFile: "${env.ZIP_FILE_NAME}", overwrite:true, archive:false, glob: '*.html'
            }
        }

        stage('Publish documentation') {
            steps {
                sh 'echo ---- deploy somewhere else ----'
                archiveArtifacts artifacts: "${env.ZIP_FILE_NAME}"
            }
        }
    }
}