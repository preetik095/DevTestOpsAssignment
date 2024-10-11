pipeline {
    agent any
    tools {
    	maven 'Maven'
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat "mvn clean install"
            }
        }

        stage('Test') {
            steps {
                bat "mvn test"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv(SonarQube_nagp) {
                    bat "mvn clean test sonar:sonar"
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
           steps{
                rtMavenDeployer(
                    id: 'deployer',
                    serverId: '3181417@artifactory',
                    releaseRepo: 'nagp.devtestops.assignment',
                    snapshotRepo: 'nagp.devtestops.assignment'
                )
                rtMavenRun(
                    pom: 'pom.xml',
                    goals: 'clean install',
                    deployerId: 'deployer'
                    )
                rtPublishBuildInfo(
                    serverId:'3181417@artifactory',
                )
            }   
        }
    }

    post {
        always {
            testng '**/target/surefire-reports/*.xml' // Archive test reports
        }
        success {
            echo 'Build and SonarQube quality gate passed. Artifacts uploaded to Artifactory.'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
