# README.md

# Jenkins Setup Instructions

## Table of Contents

1. [Jenkins Setup Instructions](#jenkins-setup-instructions)
    * [Running Jenkins with `java -jar jenkins.war`](#running-jenkins-with-java--jar-jenkinswar)
2. [Setting Up Jenkins with Docker](#setting-up-jenkins-with-docker)
    * [Prerequisites](#prerequisites)
    * [Running Jenkins in Docker](#running-jenkins-in-docker)
    * [Persisting Jenkins Data](#persisting-jenkins-data)
    * [Accessing Jenkins](#accessing-jenkins)

---

## Jenkins Setup Instructions

### Running Jenkins with `java -jar jenkins.war`

Follow these steps to run Jenkins using the `jenkins.war` file:

#### Prerequisites:
* **Java** (Version 11 or higher).
* Download the `jenkins.war` file from the [Jenkins official website](https://www.jenkins.io/download/).

#### Steps:

1. Verify that **Java** is installed:
   ```bash
   java -version

2. If not installed, download and install Java.

3. Place the jenkins.war file in your desired directory.

4. Open a terminal and run the following command:

 - java -jar jenkins.war

5. Once the Jenkins server starts, you will see an output similar to this:
 
 - Jenkins is fully up and running at http://localhost:8080

6. Open your browser and go to:

 - http://localhost:8080
* During the first run, Jenkins will prompt you to unlock it. Follow these steps:

* Copy the initial admin password displayed in the terminal output.
* Paste it into the Jenkins setup screen in your browser.
7. Complete the setup wizard by installing suggested plugins or customizing plugins.


# -----------------------------------------------------------------------------------------------------------------------
  #                                         Setting Up Jenkins With Docker
# -----------------------------------------------------------------------------------------------------------------------


# Prerequisites:
* Docker installed on your system.
* Check by running:

- docker --version
* Docker Hub account (optional for image pulls).

# Running Jenkins in Docker
1. Pull the official Jenkins Docker image:


- docker pull jenkins/jenkins:lts
2. Start the Jenkins container:


- docker run -p 8080:8080 -p 50000:50000 --name jenkins-container jenkins/jenkins:lts
* -p 8080:8080 maps Jenkins to port 8080 on your machine.
* -p 50000:50000 maps the port for Jenkins agent connections.
* --name jenkins-container assigns a name to the container.
3. Wait for Jenkins to start. You will see logs in the terminal indicating that Jenkins is up and running:


* Jenkins is fully up and running at http://localhost:8080

4. Open your browser and access Jenkins at:

- http://localhost:8080

# -----------------------------------------------------------------------------------------------------------------------
#                                                     Accessing Jenkins
# -----------------------------------------------------------------------------------------------------------------------

* Once Jenkins is running, open your browser and go to:

- http://localhost:8080
 ### To unlock Jenkins during the first run:

* Copy the admin password from the Docker container logs:

- docker logs jenkins-container
* Look for the password in the output and paste it into the Jenkins setup screen.
* Complete the setup wizard and install the desired plugins.

