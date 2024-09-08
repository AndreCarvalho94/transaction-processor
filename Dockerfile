# Etapa 1: Build da aplicação
FROM maven:latest AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Preparação do container para execução do JAR
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar /app/application.jar

# Comando para rodar o JAR
ENTRYPOINT ["java", "-jar", "/app/application.jar"]
