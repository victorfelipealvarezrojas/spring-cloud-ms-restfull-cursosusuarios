FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/ms-cursos

COPY ./pom.xml /app
COPY ./ms-cursos/.mvn /app/ms-cursos/.mvn
COPY ./ms-cursos/mvnw /app/ms-cursos
COPY ./ms-cursos/pom.xml /app/ms-cursos

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

COPY ./ms-cursos/src /app/ms-cursos/src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine as production

WORKDIR /app

COPY --from=builder /app/ms-cursos/target/ms-cursos-0.0.1-SNAPSHOT.jar /app/ms-cursos/ms-cursos.jar

RUN adduser -D myuser && chown myuser:myuser /app
USER myuser

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "ms-cursos/ms-cursos.jar"]