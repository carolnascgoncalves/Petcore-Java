# Etapa 1: Compilação do projeto
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copia os arquivos necessários para o Maven Wrapper
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

RUN chmod +x mvnw

# Baixa dependências e compila o projeto
RUN ./mvnw dependency:go-offline

COPY src ./src

RUN ./mvnw clean package -DskipTests

# Etapa 2: Execução da aplicação
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copia o JAR gerado na etapa de compilação
COPY --from=build /app/target/*.jar app.jar

# Porta utilizada pela aplicação
EXPOSE 8080

# Inicia o PetCore
ENTRYPOINT ["java", "-jar", "app.jar"]
