pipeline {
    agent any
    
    tools {
        maven 'M3'
        jdk 'JDK11'
    }
    
    triggers {
        // Se ejecuta automáticamente con cada push
        pollSCM('H/2 * * * *')  // Revisa cambios cada 2 minutos
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "✅ Código descargado - Commit: ${GIT_COMMIT}"
            }
        }
        
        stage('Build') {
            steps {
                echo "🏗️ Compilando cambios..."
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                echo "🧪 Ejecutando tests..."
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        reportDir: 'target/surefire-reports',
                        reportFiles: '*.html',
                        reportName: 'HTML Test Report'
                    ])
                }
            }
        }
        
        stage('Package') {
            steps {
                echo "📦 Empaquetando aplicación..."
                sh 'mvn package -DskipTests'
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
            echo "📊 Pipeline ejecutado para commit: ${GIT_COMMIT}"
            emailext (
                subject: "Build #${BUILD_NUMBER} - ${currentBuild.currentResult}",
                body: "El build ${BUILD_URL} terminó con estado: ${currentBuild.currentResult}",
                to: "tu-email@example.com"
            )
        }
        success {
            echo "🎉 ¡Build exitoso! La aplicación está desplegada"
            slackSend(color: "good", message: "✅ Build #${BUILD_NUMBER} exitoso - ${JOB_NAME}")
        }
        failure {
            echo "❌ Build fallido - Revisar logs"
            slackSend(color: "danger", message: "❌ Build #${BUILD_NUMBER} fallido - ${JOB_NAME}")
        }
        unstable {
            echo "⚠️  Build inestable - Algunos tests fallaron"
        }
    }
}