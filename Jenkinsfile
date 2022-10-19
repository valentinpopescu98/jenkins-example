pipeline {
    agent any
    
    tools { 
        maven 'Maven'
        jdk 'JDK 17'
    }
    
    stages {
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'mvn -B clean package --file pom.xml'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                sh 'mvn test'
            }
        }
    }
}