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
                        sh './mvnw -pl spring-petclinic-customers-service clean test'
                    }
                    post {
                        always {
                            echo "Publishing test results for Customers Service..."
                            dir('spring-petclinic-customers-service') {
                                junit 'target/surefire-reports/*.xml'
                                 // Save Jacoco report with a unique ID for this service
                                recordCoverage(
                                    tools: [[parser: 'JACOCO']],
                                    id: 'customers-service-coverage',
                                    name: 'Customers Service Coverage',
                                    sourceCodeRetention: 'EVERY_BUILD',
                                    qualityGates: [
                                        [threshold: 71.0, metric: 'LINE', unstable: false, failing: true],
                                        [threshold: 65.0, metric: 'BRANCH', unstable: false, failing: true],
                                        [threshold: 75.0, metric: 'METHOD', unstable: true, failing: false]
                                        // LINE, BRANCH, METHOD, CLASS, INSTRUCTION, FILE, PACKAGE, ... Ngoài ra còn nhiều, check document chính thức
                                        // threshold: giá trị phần trăm tối thiểu cần đạt
                                        // metric: loại coverage áp dụng
                                        // unstable: đánh dấu build là unstable nếu không đạt threshold
                                        // failing: đánh dấu build là failed nếu không đạt threshold
                                    ]
                                )
                                archiveArtifacts artifacts: 'target/surefire-reports/*.xml', fingerprint: true
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
                        sh './mvnw -pl spring-petclinic-genai-service clean test'
                        sh 'find . -name "*.xml"'
                    }
                    post {
                        always {
                            echo "Publishing test results for Genai Service..."
                            dir('spring-petclinic-genai-service') {
                                junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                                recordCoverage(
                                    tools: [[parser: 'JACOCO']],
                                    id: 'genai-service-coverage',
                                    name: 'Gen Ai Service Coverage',
                                    sourceCodeRetention: 'EVERY_BUILD',
                                    qualityGates: [
                                        [threshold: 71.0, metric: 'LINE', unstable: false, failing: true],
                                        [threshold: 65.0, metric: 'BRANCH', unstable: false, failing: true],
                                        [threshold: 75.0, metric: 'METHOD', unstable: true, failing: false]
                                        // LINE, BRANCH, METHOD, CLASS, INSTRUCTION, FILE, PACKAGE, ... Ngoài ra còn nhiều, check document chính thức
                                        // threshold: giá trị phần trăm tối thiểu cần đạt
                                        // metric: loại coverage áp dụng
                                        // unstable: đánh dấu build là unstable nếu không đạt threshold
                                        // failing: đánh dấu build là failed nếu không đạt threshold
                                    ]
                                )
                                archiveArtifacts artifacts: 'target/surefire-reports/*.xml', fingerprint: true, allowEmptyArchive: true
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
                        sh './mvnw -pl spring-petclinic-vets-service clean test'
                    }
                    post {
                        always {
                            echo "Publishing test results for Vets Service..."
                            dir('spring-petclinic-vets-service') {
                                junit 'target/surefire-reports/*.xml'
                                recordCoverage(
                                    tools: [[parser: 'JACOCO']],
                                    id: 'vets-service-coverage',
                                    name: 'vets Service Coverage',
                                    sourceCodeRetention: 'EVERY_BUILD',
                                    qualityGates: [
                                        [threshold: 71.0, metric: 'LINE', unstable: false, failing: true],
                                        [threshold: 65.0, metric: 'BRANCH', unstable: false, failing: true],
                                        [threshold: 75.0, metric: 'METHOD', unstable: true, failing: false]
                                        // LINE, BRANCH, METHOD, CLASS, INSTRUCTION, FILE, PACKAGE, ... Ngoài ra còn nhiều, check document chính thức
                                        // threshold: giá trị phần trăm tối thiểu cần đạt
                                        // metric: loại coverage áp dụng
                                        // unstable: đánh dấu build là unstable nếu không đạt threshold
                                        // failing: đánh dấu build là failed nếu không đạt threshold
                                    ]
                                )
                                archiveArtifacts artifacts: 'target/surefire-reports/*.xml', fingerprint: true
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
                        sh './mvnw -pl spring-petclinic-visits-service clean test'
                    }
                    post {
                        always {
                            echo "Publishing test results for Visits Service..."
                            dir('spring-petclinic-visits-service') {
                                junit 'target/surefire-reports/*.xml'
                                // Save Jacoco report with a unique ID for this service
                                recordCoverage(
                                    tools: [[parser: 'JACOCO']],
                                    id: 'visits-service-coverage',
                                    name: 'visits Service Coverage',
                                    sourceCodeRetention: 'EVERY_BUILD',
                                    qualityGates: [
                                        [threshold: 71.0, metric: 'LINE', unstable: false, failing: true],
                                        [threshold: 65.0, metric: 'BRANCH', unstable: false, failing: true],
                                        [threshold: 75.0, metric: 'METHOD', unstable: true, failing: false]
                                        // LINE, BRANCH, METHOD, CLASS, INSTRUCTION, FILE, PACKAGE, ... Ngoài ra còn nhiều, check document chính thức
                                        // threshold: giá trị phần trăm tối thiểu cần đạt
                                        // metric: loại coverage áp dụng
                                        // unstable: đánh dấu build là unstable nếu không đạt threshold
                                        // failing: đánh dấu build là failed nếu không đạt threshold
                                    ]
                                )
                                archiveArtifacts artifacts: 'target/surefire-reports/*.xml', fingerprint: true
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
                        sh './mvnw -pl spring-petclinic-customers-service -am clean install -DskipTests'
                    }
                }
                
                stage('Build - Genai Service') {
                    when {
                        changeset pattern: 'spring-petclinic-genai-service/**', comparator: 'ANT'
                    }
                    steps {
                        echo "Building Genai Service..."
                        sh './mvnw -pl spring-petclinic-genai-service -am clean install -DskipTests'
                    }
                }
                
                stage('Build - Vets Service') {
                    when {
                        changeset pattern: 'spring-petclinic-vets-service/**', comparator: 'ANT'
                    }
                    steps {
                        echo "Building Vets Service..."
                        sh './mvnw -pl spring-petclinic-vets-service -am clean install -DskipTests'
                    }
                }
                
                stage('Build - Visits Service') {
                    when {
                        changeset pattern: 'spring-petclinic-visits-service/**', comparator: 'ANT'
                    }
                    steps {
                        echo "Building Visits Service..."
                        sh './mvnw -pl spring-petclinic-visits-service -am clean install -DskipTests'
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
                    authentication: 'github-token-fix'
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
                    authentication: 'github-token-fix'
                )
                echo "GitHub Response: ${response.status}"
            }
        }

        always {
            echo "Pipeline finished."
        }
    }
}