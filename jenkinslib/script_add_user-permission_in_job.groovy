import jenkins.model.Jenkins
import hudson.*
import hudson.security.AuthorizationMatrixProperty
import hudson.security.Permission

  
def jobName = "pythonunit-test"
def userName = "aharongo"

// Get the Jenkins instance
def jenkins = Jenkins.instance

// Check if the job exists
def job = jenkins.getItemByFullName(jobName)
if (job) {
    // Get the current AuthorizationMatrixProperty or create a new one if not exists
    def authMatrix = job.getProperty(AuthorizationMatrixProperty.class) ?: new AuthorizationMatrixProperty()

    // Add the user with desired permissions
    //authMatrix.add(userName, Permission.READ, Permission.BUILD, Permission.CONFIGURE)
    authMatrix.add(Item.READ, userName)
    authMatrix.add(Item.BUILD, userName)
    authMatrix.add(Item.CONFIGURE, userName)
   
    // Apply the AuthorizationMatrixProperty to the job
    job.addProperty(authMatrix)

    println("User ${userName} added to job ${jobName} with permissions Read, Build, Configure.")
} else {
    println("Job ${jobName} not found.")
}

//#Project-based Matrix Authorization strategy 
//install the Authorize Project plugin.
//Click Configure->under the general tab->Enable Project-based Security
//Under the Authorization section, select the "Project-based Matrix Authorization Strategy"

// Add the particular user and assign the appropriate permissions.

//#install the plugin
//https://www.edureka.co/community/47822/there-restrict-permissions-user-per-individual-job-jenkins

//#To run this script:

//Go to Jenkins dashboard.
//Click on "Manage Jenkins" > "Script Console."
//Copy and paste the script into the console.
//Click "Run."
