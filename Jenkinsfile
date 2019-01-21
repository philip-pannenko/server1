pipeline {
    agent any

    tools {
	gradle "gradle"
    }
    
    options { 
        checkoutToSubdirectory('server1') 
    }

    stages {
        stage('Checkout Other Projects') {
            steps {
                
                dir('server-utils') {
                     git branch: '${BRANCH_NAME}', changelog: false, credentialsId: '9d721ad4-95fd-4169-ad43-eab15c89b642', poll: false, url: 'git@github.com:philip-pannenko/server-utils.git' 
                     sh "git checkout -B ${BRANCH_NAME}"
                     //sshagent(['aa5922b8-57ea-4a46-9178-a1f41aa24cc0']) {
                     //   sh('git push -u origin ${BRANCH_NAME}')
                     //}
                }
            }
        }
        
        stage('Build') {
            steps {
                
                dir('server-utils') {
                    sh 'gradle build publishToMavenLocal'
                }
                
                dir('server1') {
                    sh 'gradle build'
                }
            }
        }
        
        stage('Create Docker') {
            steps {
                dir('server1') {
                    sh 'gradle docker'
                }
            }
        }
        
        stage ('Run Docker') {
            
            environment { 
                COMPOSE_PROJECT_NAME="${env.BRANCH_NAME}"
                ENV='dev'
            }
                
            steps {
                
                dir('server1') {
                
                    sh "docker-compose -p ${env.BRANCH_NAME} up -d ";
                
                }
            }
        }
    }
}
