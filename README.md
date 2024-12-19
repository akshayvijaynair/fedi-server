# fediverse-starter-kit
An activitypub compliant prototype that will adhere to fediverse compliance.

## Instructions for running a  build.
- In application.properties update datasource url, username and password.
- Making sure the  application is running on the same jdk version.

### How to make sure whole application has same jdk version?
- Check Run/Debug configurations jdk version.
- Open Project settings and check SDK version.
- Open Intellij settings -> Build, Execution, Deployment -> Build Tools -> Maven -> Runner and check JRE version.

Check if JDK are same at all the places.

Now run the application.

# Spring boot Backend Setup Guide

This repository contains a backend system built for federated social networking, designed to comply with ActivityPub standards. The system supports user management, activity creation, follow requests, inbox/outbox handling, and feed generation. Follow this guide to set up and run the project.

---

* [Spring boot Backend Setup Guide](#spring-boot-java-backend-setup-guide)
  * [Prerequisites](#prerequisites)
  * [Project Setup](#project-setup)
    * [1. Clone the Repository](#1-clone-the-repository)
    * [2. Install Dependencies](#2-install-dependencies)
  * [Running the Application](#running-the-application)
    * [Using Maven](#using-maven)
  * [Troubleshooting](#troubleshooting)


---

## Prerequisites

Ensure the following tools are installed on your system:

1. **Java** (JDK 17 or later)
2. **Maven** (v3.8.x or later)
3. **Docker** (v20.x or later)
4. **PostgreSQL** (v13 or later, for database setup)

---

## Project Setup

### 1. Clone the Repository
Clone this repository to your local machine:
```bash
git clone https://github.com/akshayvijaynair/fedi-server.git
cd activitypub-backend
```

### 2. Install Dependencies
Install all the dependencies
```bash
mvn clean install
```

## Running the Application
### Using Maven
```bash
mvn spring-boot:run
```
### Troubleshooting
## Port Conflicts
By default, the backend runs on port 8080. Modify this port in the application.properties file or pass it as a runtime argument:

```bash
java -jar app.jar --server.port=9090
```

## Database Connectivity Issues:
If dependencies fail to resolve, run:

```bash
mvn clean install -U
```

Happy coding

Let me know if you need any further adjustments or additional sections!


