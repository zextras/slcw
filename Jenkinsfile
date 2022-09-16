@Library("zextras-library@0.5.3") _

pipeline {
    agent {
        node {
            label 'infra-agent-v3'
        }
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '100'))
        timeout(time: 2, unit: 'HOURS')
    }
    stages {
        stage('Pre-commit validation') {
            steps {
                cmd sh: "pre-commit run --all-files"
            }
        }
        stage('Tests') {
            steps {
                sh "mvn -B test verify"
                junit checksName: 'UT & IT', healthScaleFactor: 0.0, testResults: '**/target/**/*.xml'
            }
        }
    }
    post {
        always {
            checkout scm
            script {
                GIT_COMMIT_EMAIL = sh(
                        script: 'git --no-pager show -s --format=\'%ae\'',
                        returnStdout: true
                ).trim()
            }
            emailext attachLog: true, body: '$DEFAULT_CONTENT', recipientProviders: [requestor()], subject: '$DEFAULT_SUBJECT', to: "${GIT_COMMIT_EMAIL}"
        }
    }
}
