pipeline {
    agent any
    
    parameters {
        string(name: 'CUSTOMERS_BRANCH', defaultValue: 'main', description: 'Branch for customers-service')
        string(name: 'GENAI_BRANCH', defaultValue: 'main', description: 'Branch for genai-service')
        string(name: 'VETS_BRANCH', defaultValue: 'main', description: 'Branch for vets-service')
        string(name: 'VISITS_BRANCH', defaultValue: 'main', description: 'Branch for visits-service')
    }

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
                                recordCoverage(
                                    tools: [[parser: 'JACOCO']],
                                    id: 'customers-service-coverage',
                                    name: 'Customers Service Coverage',
                                    sourceCodeRetention: 'EVERY_BUILD',
                                    qualityGates: [
                                        [threshold: 71.0, metric: 'LINE', criticality: 'FAILURE'],
                                        [threshold: 65.0, metric: 'BRANCH', criticality: 'FAILURE'],
                                        [threshold: 75.0, metric: 'METHOD', criticality: 'FAILURE']
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
                                        [threshold: 71.0, metric: 'LINE', criticality: 'UNSTABLE'],
                                        [threshold: 65.0, metric: 'BRANCH', criticality: 'UNSTABLE'],
                                        [threshold: 75.0, metric: 'METHOD', criticality: 'UNSTABLE']
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
                                        [threshold: 71.0, metric: 'LINE',criticality: 'FAILURE'],
                                        [threshold: 65.0, metric: 'BRANCH', criticality: 'FAILURE'],
                                        [threshold: 75.0, metric: 'METHOD', criticality: 'FAILURE']
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
                                recordCoverage(
                                    tools: [[parser: 'JACOCO']],
                                    id: 'visits-service-coverage',
                                    name: 'visits Service Coverage',
                                    sourceCodeRetention: 'EVERY_BUILD',
                                    qualityGates: [
                                        [threshold: 71.0, metric: 'LINE', criticality: 'FAILURE'],
                                        [threshold: 65.0, metric: 'BRANCH', criticality: 'FAILURE'],
                                        [threshold: 75.0, metric: 'METHOD', criticality: 'FAILURE']
                                    ]
                                )
                                archiveArtifacts artifacts: 'target/surefire-reports/*.xml', fingerprint: true
                            }
                        }
                    }
                }
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
        
        stage('Build Docker Images') {
            steps {
                script {
                    // Get current commit ID for tagging
                    def commitId = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    
                    // Define Docker Hub credentials
                    withCredentials([usernamePassword(credentialsId: 'docker_hub_pat', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        // Login to Docker Hub
                        sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                        
                        // Get the list of changed files in the current commit
                        def changedFiles = sh(script: 'git diff --name-only HEAD~1 HEAD', returnStdout: true).trim().split('\n')
                        
                        // Define the services and their directories
                        def serviceDirs = [
                            'customers-service': 'spring-petclinic-customers-service',
                            'genai-service': 'spring-petclinic-genai-service',
                            'vets-service': 'spring-petclinic-vets-service',
                            'visits-service': 'spring-petclinic-visits-service'
                        ]
                        
                        // Determine which services have changes
                        def servicesToBuild = []
                        for (def file in changedFiles) {
                            for (def entry in serviceDirs) {
                                if (file.startsWith(entry.value + '/')) {
                                    if (!servicesToBuild.contains(entry.key)) {
                                        servicesToBuild.add(entry.key)
                                    }
                                }
                            }
                        }
                        
                        // If no specific service changes detected, skip this stage
                        if (servicesToBuild.isEmpty()) {
                            echo "No specific service changes detected. Skipping Docker image build."
                            return
                        }
                        
                        // Build and push images for services with changes
                        for (def service in servicesToBuild) {
                            def serviceDir = serviceDirs[service]
                            def imageName = "${DOCKER_USERNAME}/spring-petclinic-${service}"
                            
                            // Build the service JAR file
                            sh "./mvnw -pl ${serviceDir} -am clean package -DskipTests"
                            
                            // Build and push Docker image
                            sh """
                            cp ${serviceDir}/target/*.jar docker/${service}.jar
                            cd docker
                            docker build --build-arg ARTIFACT_NAME=${service} --build-arg EXPOSED_PORT=8080 -t ${imageName}:${commitId} .
                            docker tag ${imageName}:${commitId} ${imageName}:latest
                            docker push ${imageName}:${commitId}
                            docker push ${imageName}:latest
                            rm ${service}.jar
                            cd ..
                            """
                        }
                    }
                }
            }
        }
        
        stage('Deploy to Kubernetes') {
            when {
                expression { return params.RUN_DEPLOY == 'true' }
            }
            steps {
                script {
                    // Get Kubernetes config
                    sh "mkdir -p ~/.kube"
                    sh "cp /var/jenkins_home/.kube/config ~/.kube/config"
                    
                    // Get commit ID for tagging
                    def commitId = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                    
                    // Deploy services to Kubernetes
                    withCredentials([usernamePassword(credentialsId: 'docker_hub_PAT', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        def services = [
                            ['name': 'customers-service', 'branch': params.CUSTOMERS_BRANCH, 'port': 8081], 
                            ['name': 'genai-service', 'branch': params.GENAI_BRANCH, 'port': 8082], 
                            ['name': 'vets-service', 'branch': params.VETS_BRANCH, 'port': 8083], 
                            ['name': 'visits-service', 'branch': params.VISITS_BRANCH, 'port': 8084]
                        ]
                        
                        // Create namespace if it doesn't exist
                        sh "kubectl create namespace petclinic-dev --dry-run=client -o yaml | kubectl apply -f -"
                        
                        for (service in services) {
                            def serviceTag = service.branch == 'main' ? 'main' : commitId
                            def imageName = "${DOCKER_USERNAME}/spring-petclinic-${service.name}:${serviceTag}"
                            
                            // Create Kubernetes deployment and service
                            sh """
                            cat <<EOF | kubectl apply -f -
                            apiVersion: apps/v1
                            kind: Deployment
                            metadata:
                              name: ${service.name}
                              namespace: petclinic-dev
                            spec:
                              replicas: 1
                              selector:
                                matchLabels:
                                  app: ${service.name}
                              template:
                                metadata:
                                  labels:
                                    app: ${service.name}
                                spec:
                                  containers:
                                  - name: ${service.name}
                                    image: ${imageName}
                                    ports:
                                    - containerPort: 8080
                                    env:
                                    - name: SPRING_PROFILES_ACTIVE
                                      value: docker
                            ---
                            apiVersion: v1
                            kind: Service
                            metadata:
                              name: ${service.name}
                              namespace: petclinic-dev
                            spec:
                              type: NodePort
                              ports:
                              - port: 8080
                                targetPort: 8080
                                nodePort: ${service.port}
                              selector:
                                app: ${service.name}
                            EOF
                            """
                        }
                        
                        // Get Minikube IP
                        def minikubeIp = sh(script: 'minikube ip', returnStdout: true).trim()
                        echo "Services are accessible at:"
                        for (service in services) {
                            echo "${service.name}: ${minikubeIp}:${service.port}"
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