ARG MS_NAME=ms-usuarios

FROM openjdk:17-jdk-alpine as builder

ARG MS_NAME

WORKDIR /app/$MS_NAME

COPY ./pom.xml /app
COPY ./$MS_NAME/.mvn /app/$MS_NAME/.mvn
COPY ./$MS_NAME/mvnw /app/$MS_NAME
COPY ./$MS_NAME/pom.xml /app/$MS_NAME

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

COPY ./ms-usuarios/src /app/$MS_NAME/src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine as production

ARG MS_NAME

WORKDIR /app

ARG TARGET_FOLDER=/app/$MS_NAME/target
COPY --from=builder $TARGET_FOLDER/ms-usuarios-0.0.1-SNAPSHOT.jar /app/$MS_NAME/ms-usuarios.jar

RUN adduser -D myuser && chown myuser:myuser /app
USER myuser

ENV PORT=8001

EXPOSE $PORT

ENTRYPOINT ["java", "-jar", "ms-usuarios/ms-usuarios.jar"]