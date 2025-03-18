pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                script {
                    def branchToCheckout = env.BRANCH_NAME ?: 'main'
                    echo "Checkout branch: ${branchToCheckout}"
                    git branch: branchToCheckout, 
                        url: 'https://github.com/tranductung07012004/devOps_1_spring-petclinic-microservices.git'
                }
            }
        }
        
        stage('Test Services') {
            parallel {
                stage('Test - Customers Service') {
                    when {
                        changeset pattern: 'spring-petclinic-customers-service/**', comparator: 'ANT'
                    }
                    steps {
                        echo "Running tests for Customers Service..."
                        dir('spring-petclinic-customers-service') {
                            sh './mvnw clean test'
                        }
                    }
                    post {
                        always {
                            echo "Publishing test results for Customers Service..."
                            dir('spring-petclinic-customers-service') {
                                junit '**/target/surefire-reports/*.xml'
                                jacoco(
                                    execPattern: '**/target/jacoco.exec',
                                    classPattern: '**/target/classes',
                                    sourcePattern: '**/src/main/java'
                                )
                                archiveArtifacts artifacts: '**/surefire-reports/*.xml', fingerprint: true
                            }
                        }
                    }
                }
                
                stage('Test - Genai Service') {
                    when {
                        changeset pattern: 'spring-petclinic-genai-service/**', comparator: 'ANT'
                    }
                    steps {
                        echo "Running tests for Genai Service..."
                        dir('spring-petclinic-genai-service') {
                            sh './mvnw clean test'
                        }
                    }
                    post {
                        always {
                            echo "Publishing test results for Genai Service..."
                            dir('spring-petclinic-genai-service') {
                                junit '**/target/surefire-reports/*.xml'
                                jacoco(
                                    execPattern: '**/target/jacoco.exec',
                                    classPattern: '**/target/classes',
                                    sourcePattern: '**/src/main/java'
                                )
                                archiveArtifacts artifacts: '**/surefire-reports/*.xml', fingerprint: true
                            }
                        }
                    }
                }
                
                stage('Test - Vets Service') {
                    when {
                        changeset pattern: 'spring-petclinic-vets-service/**', comparator: 'ANT'
                    }
                    steps {
                        echo "Running tests for Vets Service..."
                        dir('spring-petclinic-vets-service') {
                            sh './mvnw clean test'
                        }
                    }
                    post {
                        always {
                            echo "Publishing test results for Vets Service..."
                            dir('spring-petclinic-vets-service') {
                                junit '**/target/surefire-reports/*.xml'
                                jacoco(
                                    execPattern: '**/target/jacoco.exec',
                                    classPattern: '**/target/classes',
                                    sourcePattern: '**/src/main/java'
                                )
                                archiveArtifacts artifacts: '**/surefire-reports/*.xml', fingerprint: true
                            }
                        }
                    }
                }
                
                stage('Test - Visits Service') {
                    when {
                        changeset pattern: 'spring-petclinic-visits-service/**', comparator: 'ANT'
                    }
                    steps {
                        echo "Running tests for Visits Service..."
                        dir('spring-petclinic-visits-service') {
                            sh './mvnw clean test'
                        }
                    }
                    post {
                        always {
                            echo "Publishing test results for Visits Service..."
                            dir('spring-petclinic-visits-service') {
                                junit '**/target/surefire-reports/*.xml'
                                jacoco(
                                    execPattern: '**/target/jacoco.exec',
                                    classPattern: '**/target/classes',
                                    sourcePattern: '**/src/main/java'
                                )
                                archiveArtifacts artifacts: '**/surefire-reports/*.xml', fingerprint: true
                            }
                        }
                    }
                }
            }
        }
        
        stage('Debug') {
            steps {
                echo "Checking test report files..."
                sh 'find . -name "*.xml"'
            }
        }
        
        stage('Build Services') {
            parallel {
                stage('Build - Customers Service') {
                    when {
                        changeset pattern: 'spring-petclinic-customers-service/**', comparator: 'ANT'
                    }
                    steps {
                        echo "Building Customers Service..."
                        dir('spring-petclinic-customers-service') {
                            sh './mvnw clean install -DskipTests'
                        }
                    }
                }
                
                stage('Build - Genai Service') {
                    when {
                        changeset pattern: 'spring-petclinic-genai-service/**', comparator: 'ANT'
                    }
                    steps {
                        echo "Building Genai Service..."
                        dir('spring-petclinic-genai-service') {
                            sh './mvnw clean install -DskipTests'
                        }
                    }
                }
                
                stage('Build - Vets Service') {
                    when {
                        changeset pattern: 'spring-petclinic-vets-service/**', comparator: 'ANT'
                    }
                    steps {
                        echo "Building Vets Service..."
                        dir('spring-petclinic-vets-service') {
                            sh './mvnw clean install -DskipTests'
                        }
                    }
                }
                
                stage('Build - Visits Service') {
                    when {
                        changeset pattern: 'spring-petclinic-visits-service/**', comparator: 'ANT'
                    }
                    steps {
                        echo "Building Visits Service..."
                        dir('spring-petclinic-visits-service') {
                            sh './mvnw clean install -DskipTests'
                        }
                    }
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
