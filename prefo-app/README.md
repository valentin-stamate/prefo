# PrefO App

## Configuration

Local database: `docker run -p 5432:5432 --name prefo -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=prefo -d --restart unless-stopped postgres`

## Generated with Spring Initializr

You can find the template [here](https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.0.5&packaging=jar&jvmVersion=17&groupId=com.valentinstamate&artifactId=prefo-app&name=prefo-app&description=PrefO%20main%20rest%20API%20service&packageName=com.valentinstamate.prefo-app&dependencies=web,postgresql,data-jpa)
