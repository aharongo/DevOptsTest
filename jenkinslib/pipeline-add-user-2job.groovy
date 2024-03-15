#pre requisit

#install jenkins plugin

Matrix Authorization Strategy
Matrix Combinations Plugin
Matrix Groovy Execution Strategy Plugin
Matrix Project Plugin

------------------------------

import jenkins.model.Jenkins
import hudson.*
import hudson.security.AuthorizationMatrixProperty
import hudson.security.Permission

pipeline {
    agent any

    parameters {
        string(name: 'JOB_NAME', defaultValue: 'pipeline-job', description: 'Name of the existing Jenkins job')
        string(name: 'USER_NAME', defaultValue: 'golan', description: 'Name of the user to be added')
    }

    stages {
        stage('Add User to Job') {
            steps {
                script {
                    def jobName = params.JOB_NAME
                    def userName = params.USER_NAME
                    
                    // Check if job name and user name are provided
                    if (!jobName || !userName) {
                        error('Please provide both JOB_NAME and USER_NAME parameters.')
                    }

                    // Get the Jenkins instance
                    def jenkins = Jenkins.get()

                    // Get the job by its name
                    def job = jenkins.getItemByFullName(jobName)

                    // Check if the job exists
                    if (!job) {
                        error("Job '${jobName}' not found.")
                    }

                    // Get the current AuthorizationMatrixProperty or create a new one if it doesn't exist
                    def authMatrix = job.getProperty(AuthorizationMatrixProperty.class) ?: new AuthorizationMatrixProperty()

                    // Add the user with desired permissions
                    authMatrix.add(Item.READ, userName)
                    authMatrix.add(Item.BUILD, userName)
                    authMatrix.add(Item.CONFIGURE, userName)

                    // Apply the AuthorizationMatrixProperty to the job
                    job.addProperty(authMatrix)

                    println("User '${userName}' added to job '${jobName}' with permissions Read, Build, Configure.")
                }
            }
        }
    }
}

#Run the pipeline until all list below will approved


jenkins -> Manage Jenkins -> ScriptAproval

Signatures already approved:

method groovy.lang.GroovyObject invokeMethod java.lang.String java.lang.Object
method hudson.model.Job addProperty hudson.model.JobProperty
method hudson.model.Job getProperty java.lang.Class
method jenkins.model.Jenkins getAuthorizationStrategy
method jenkins.model.Jenkins getItemByFullName java.lang.String
method org.jenkinsci.plugins.matrixauth.AuthorizationContainer add hudson.security.Permission java.lang.String
staticField hudson.model.Item BUILD
staticField hudson.model.Item CONFIGURE
staticField hudson.model.Item READ
staticMethod com.michelin.cio.hudson.plugins.rolestrategy.RoleBasedAuthorizationStrategy getInstance
staticMethod jenkins.model.Jenkins get
staticMethod jenkins.model.Jenkins getInstance


Signatures already approved assuming permission check:

method jenkins.model.Jenkins getItemByFullName java.lang.String

Signatures already approved which may have introduced a security vulnerability (recommend clearing):
method groovy.lang.GroovyObject invokeMethod java.lang.String java.lang.Object
staticMethod jenkins.model.Jenkins getInstance