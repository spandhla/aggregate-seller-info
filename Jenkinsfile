pipeline {
    agent any

    triggers {
            pollSCM('* * * * *')
    }

    environment {
        APPLICATION_NAME = 'aggregate-seller-info'
    }

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
    }
}
