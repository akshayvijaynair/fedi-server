pipeline {
    agent any // Defines the Jenkins agent to run the pipeline.

    // Stages define the sequence flow of the pipeline
    stages {

        stage('Verify Branch') {
            steps {
                script {
                    // Assuming Git_Branch is an environment variable or a parameter
                    echo "Branch: ${env.Git_Branch ?: 'No branch specified'}"
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Building...'
                // Add your build commands here
                sh 'mvn clean install' // Executes the Maven build
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying...'
                sh 'mvn clean install' // Executes the Maven build for deployment
            }
        }
    }

    // Post-build actions
    post {
        success {
            echo 'Application launched successfully'
        }
        failure {
            echo 'Application failed to launch'
        }
    }
}