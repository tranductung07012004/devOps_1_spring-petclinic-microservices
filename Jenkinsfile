pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                script {
                    def branchToCheckout = env.BRANCH_NAME ?: 'main'
                    echo "Checkout branch: ${branchToCheckout}"
                    git branch: branchToCheckout, url: 'https://github.com/tranductung07012004/devOps_1_spring-petclinic-microservices.git'
                }
            }
        }

        stage('Detect changes') {
            steps {
                script {
                    env.CHANGED_FILES = sh(script: "git diff --name-only HEAD~1", returnStdout: true).trim()
                    echo "Changed files:\n${env.CHANGED_FILES}"
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    echo "Performing build for changed services..."
                    def services = [
                        'spring-petclinic-customers-service',
                        'spring-petclinic-genai-service',
                        'spring-petclinic-vets-service',
                        'spring-petclinic-visits-service'
                    ]
                    if (env.CHANGED_FILES && env.CHANGED_FILES.trim()) {
                        def changedFilesList = env.CHANGED_FILES.split('\n')
                        for (service in services) {
                            if (changedFilesList.find { it.startsWith(service) }) {
                                echo "Building ${service}..."
                                dir(service) {
                                    sh './mvnw clean install -DskipTests'
                                }
                            } else {
                                echo "Skipping ${service}, no changes detected."
                            }
                        }
                    } else {
                        echo "No files changed. Skipping build."
                    }
                }
            }
        }

        stage('Test') {
            steps {
                echo "Running tests..."
                def services = [
                    'spring-petclinic-customers-service',
                    'spring-petclinic-genai-service',
                    'spring-petclinic-vets-service',
                    'spring-petclinic-visits-service'
                ]
                if (env.CHANGED_FILES && env.CHANGED_FILES.trim()) {
                    def changedFilesList = env.CHANGED_FILES.split('\n')
                    for (service in services) {
                        if (changedFilesList.find { it.startsWith(service) }) {
                            echo "Testing ${service}..."
                            dir(service) {
                                sh './mvnw clean test'
                            }
                        } else {
                            echo "Skipping ${service}, no changes detected."
                        }
                    }
                } else {
                    echo "No files changed. Skipping test."
                }
            }
            post {
                always {
                    echo "Publishing test results..."
                    junit '*/target/surefire-reports/.xml'
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java'
                    )
                    archiveArtifacts artifacts: '*/surefire-reports/.xml', fingerprint: true
                }
            }
        }
    }

    post {
        success {
            script {
                def commitId = env.GIT_COMMIT
                echo "Sending 'success' status to GitHub for commit: ${commitId}"
                def response = httpRequest(
                    url: "https://api.github.com/repos/tranductung07012004/devOps_1_spring-petclinic-microservices/statuses/${commitId}",
                    httpMode: 'POST',
                    contentType: 'APPLICATION_JSON',
                    requestBody: """{
                        "state": "success",
                        "description": "Build passed",
                        "context": "ci/jenkins-pipeline",
                        "target_url": "${env.BUILD_URL}"
                    }""",
                    authentication: 'github-token'
                )
                echo "GitHub Response: ${response.status}"
            }
        }

        failure {
            script {
                def commitId = env.GIT_COMMIT
                echo "Sending 'failure' status to GitHub for commit: ${commitId}"
                def response = httpRequest(
                    url: "https://api.github.com/repos/tranductung07012004/devOps_1_spring-petclinic-microservices/statuses/${commitId}",
                    httpMode: 'POST',
                    contentType: 'APPLICATION_JSON',
                    requestBody: """{
                        "state": "failure",
                        "description": "Build failed",
                        "context": "ci/jenkins-pipeline",
                        "target_url": "${env.BUILD_URL}"
                    }""",
                    authentication: 'github-token'
                )
                echo "GitHub Response: ${response.status}"
            }
        }

        always {
            echo "Pipeline finished."
        }
    }
}