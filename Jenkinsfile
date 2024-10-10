pipeline {
    agent any

    environment {
        // Define environment variables for SonarQube and JFrog
        SONARQUBE_SERVER = 'SonarQube_nagp' // SonarQube server name as defined in Jenkins
        MAVEN_HOME = tool 'Maven' // Maven installation configured in Jenkins
        ARTIFACTORY_URL = 'https://your-artifactory-url'
        ARTIFACTORY_REPO = 'your-repository-name'
        ARTIFACTORY_CREDENTIALS = credentials('ArtifactoryCredentialsId') // Jenkins credentials for JFrog
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master', url: 'https://github.com/preetik095/DevTestOpsAssignment.git'
            }
        }

        stage('Build') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn clean install"
            }
        }

        stage('Test') {
            steps {
                sh "${MAVEN_HOME}/bin/mvn test"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv(SONARQUBE_SERVER) {
                    sh "${MAVEN_HOME}/bin/mvn sonar:sonar"
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    def qualityGate = waitForQualityGate() // Check SonarQube Quality Gate
                    if (qualityGate.status != 'OK') {
                        error "Pipeline failed due to SonarQube Quality Gate failure: ${qualityGate.status}"
                    }
                }
            }
        }

        stage('Publish Artifacts to Artifactory') {
            steps {
                script {
                    def server = Artifactory.server(ARTIFACTORY_URL)
                    def uploadSpec = """{
                        "files": [{
                            "pattern": "target/*.jar",
                            "target": "${ARTIFACTORY_REPO}/"
                        }]
                    }"""
                    server.upload(uploadSpec)
                }
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml' // Archive test reports
        }
        success {
            echo 'Build and SonarQube quality gate passed. Artifacts uploaded to Artifactory.'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
