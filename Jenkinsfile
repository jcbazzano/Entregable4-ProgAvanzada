pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', 
                    url: 'https://github.com/tu-usuario/mi-playlist.git'
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
        
        stage('Deploy') {
            steps {
                script {
                    if (isUnix()) {
                        sh './deploy_mac.sh'
                    } else {
                        bat 'deploy_windows.bat'
                    }
                }
            }
        }
    }
    
    post {
        always {
            emailext (
                subject: "Build Result: ${currentBuild.currentResult}",
                body: "Build ${currentBuild.fullDisplayName} completed with status: ${currentBuild.currentResult}",
                to: "team@example.com"
            )
        }
        success {
            echo 'Build, test and deployment completed successfully!'
        }
        failure {
            echo 'Build, test or deployment failed!'
        }
    }
}