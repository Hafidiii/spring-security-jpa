node {
        stage('SCM Checkout') {
                git 'https://github.com/Hafidiii/spring-security-jpa'
        }

        stage('build') {
                bat 'mvn clean install'
        }
}
