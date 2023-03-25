# PrefO App

## Configuration

Before running the docker command you have to install gradle on your local machine from [here](https://services.gradle.org/distributions/gradle-8.0.2-all.zip).
Place it under lib folder. I do this to spin up the image faster.
Local database: `docker run -p 5432:5432 --name prefo -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=prefo -d --restart unless-stopped postgres`
Build the image: `docker build . -t prefo-app`
Build the container: ``

## Generated with Spring Initializr

You can find the template [here](https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.0.5&packaging=jar&jvmVersion=17&groupId=com.valentinstamate&artifactId=prefo-app&name=prefo-app&description=PrefO%20main%20rest%20API%20service&packageName=com.valentinstamate.prefo-app&dependencies=web,postgresql,data-jpa)

## Resources
* https://youtu.be/x7nrGLJLN5U
* https://github.com/palantir/gradle-docker
* https://stackoverflow.com/a/61461017/10805602
* https://stackoverflow.com/a/58848691/10805602