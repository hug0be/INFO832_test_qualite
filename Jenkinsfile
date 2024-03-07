pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }

    stages {
        stage('Build') {
            steps {
                script {
                    def mvnHome = tool 'Maven'
                    sh "${mvnHome}/bin/mvn -B -DskipTests clean install"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    sh "mvn test"
                }
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Coverage') {
            steps {
                script {
                    sh "mvn clean verify sonar:sonar -Dsonar.projectKey=sat_refactory -Dsonar.host.url=http://gpu-epu.univ-savoie.fr:9000 -Dsonar.login=sqp_881fa0b81b8cc8ab12afeaf1206b6b6518d5fec3"
                }
            }
        }
    }
}
