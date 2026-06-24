pipeline {

    agent any

    tools {
        maven 'Maven 3'
    }

    parameters {

        choice(
                name: 'env',
                choices: ['local', 'stage', 'production'],
                description: 'Environment'
        )

        choice(
                name: 'browser',
                choices: ['chrome', 'firefox', 'edge'],
                description: 'Browser'
        )
    }

    environment {

        HEADLESS = 'true'
        ChromeExeFilePath = '/usr/bin/google-chrome'

        // Jenkins Credentials
        CRM_CREDS = credentials('crm-credentials')

        UsernameForCrm = "${CRM_CREDS_USR}"
        PasswordForCrm = "${CRM_CREDS_PSW}"

        CRM_URL = credentials('crm-url')
        URLForCrm = "${CRM_URL}"
    }

    stages {

        stage('Environment Info') {
            steps {
                sh '''
                echo "===== JAVA ====="
                java -version

                echo "===== MAVEN ====="
                mvn -version

                echo "===== CHROME ====="
                google-chrome --version || true
            '''
            }
        }

        stage('Clean Allure Results') {
            steps {
                sh 'rm -rf allure-results || true'
                sh 'rm -rf target/allure-results || true'
            }
        }

        stage('Build & Test') {
            steps {

                sh """
                mvn clean test \
                -Denv=${params.env} \
                -Dbrowser=${params.browser} \
                -Dheadless=true
            """
            }
        }

        stage('Allure Report') {
            steps {
                allure([
                        includeProperties: false,
                        results: [[path: 'allure-results']]
                ])
            }
        }
    }

    post {

        always {

            archiveArtifacts(
                    artifacts: 'target/screenshots/*.png',
                    fingerprint: true,
                    allowEmptyArchive: true
            )
        }

        success {
            echo 'Build successful'
        }

        failure {
            echo 'Build failed'
        }
    }
}
