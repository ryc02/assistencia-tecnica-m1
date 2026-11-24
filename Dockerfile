FROM maven:3.9-eclipse-temurin-17-alpine

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn compile -B

RUN mkdir -p /app/data

ENV PORT=8080
ENV DATABASE_FILE=/app/data/assistenciadb
EXPOSE 8080

CMD ["mvn", "exec:exec"]
