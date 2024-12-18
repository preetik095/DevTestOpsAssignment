pipeline {
    agent any
    tools {
    	maven 'Maven'
    }
    
    stages {
        stage('Checkout') {
            steps {
            	echo 'Cloning the git repository'
                checkout scm
            }
        }

        stage('Build') {
            steps {
            	echo 'Code build'
                bat "mvn clean install"
            }
        }

        stage('Test') {
            steps {
            	echo 'Code test'
                bat "mvn test"
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube_nagp') {
                	echo 'Sonarqube analysis started'
                   bat "mvn sonar:sonar"
                }
            }
        }

        stage('Quality Gate') {
	    steps {
	    	echo 'Quality gate check'
	        timeout(time: 10, unit: 'MINUTES') {
	            script {
	                try {
	                    def qualityGate = waitForQualityGate()
	                    if (qualityGate.status != 'OK') {
	                        error "Pipeline failed due to SonarQube Quality Gate failure: ${qualityGate.status}"
	                    	}
	                } catch (Exception e) {
	                    error "Error checking SonarQube Quality Gate: ${e.message}"
	                	}
					}
	        	}
	    	}
		}

        stage('Publish Artifacts to Artifactory') {
           steps{
           		echo 'Uploading artifacts to artifactory'
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
        	echo 'Generating testng test result'
            junit '**/target/surefire-reports/*.xml'
        }
        success {
            echo 'Build and SonarQube quality gate passed. Artifacts uploaded to Artifactory.'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
