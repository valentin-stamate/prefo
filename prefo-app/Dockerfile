FROM openjdk:19-jdk-alpine3.16

WORKDIR /home/app
COPY ./build/libs/app.jar .
CMD ["java", "-jar", "app.jar"]

# ---==== Another Method ====----
#FROM openjdk:19-jdk-alpine3.16
#
#COPY . /home/app
#
#RUN apk add zip
#COPY lib/gradle-8.0.2-all.zip /home/gradle-8.0.2-all.zip
#RUN rm /home/gradle-8.0.2-all.zip

#RUN mkdir /opt/gradle && \
#    unzip -d /opt/gradle /home/gradle-8.0.2-all.zip
#RUN export PATH=$PATH:/opt/gradle/gradle-8.0.2/bin && \
#    cd /home/app/app_files && \
#    gradle bootJar
#
#RUN rm /home/gradle-8.0.2-all.zip && rm -rf /opt/gradle
#
#EXPOSE 8080
#
#WORKDIR /home/app/app_files/build/libs
#CMD ["java", "-jar", "app.jar"]

# ----==== A Method ====----
#FROM gradle:latest
#
#WORKDIR /home/app
#COPY . .
#RUN gradle bootJar
#
#WORKDIR /home/app/build/libs
#CMD ["java", "-jar", "app.jar"]
