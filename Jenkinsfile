pipeline {
    agent any
    
    tools {
        maven 'M3'
        jdk 'JDK17'
    }
    
    triggers {
        pollSCM('H/2 * * * *') 
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "✅ Código descargado del repositorio"
            }
        }
        
        stage('Build') {
            steps {
                echo "🏗️ Compilando aplicación..."
                bat 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                echo "🧪 Ejecutando tests..."
                bat 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo "📦 Empaquetando aplicación..."
                bat 'mvn package -DskipTests'
                archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            }
        }
        
        stage('Deploy') {
            steps {
                echo "🚀 Desplegando aplicación..."
                script {
                    if (isUnix()) {
                        sh 'chmod +x deploy_mac.sh'
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
            echo "🚀 Pipeline completado - Commit: ${GIT_COMMIT}"
        }
        success {
            echo "🎉 ¡Build exitoso! La aplicación está desplegada"
        }
        failure {
            echo "❌ Build fallido - Revisar logs"
        }
    }
}