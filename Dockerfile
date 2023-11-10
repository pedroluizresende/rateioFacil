# Etapa 1: Construir a aplicação
FROM eclipse-temurin:17-jdk-jammy as build-image
WORKDIR /app

# Copiar apenas os arquivos necessários para a construção
COPY .mvn/ .mvn
COPY ./src/main/ ./src/main/
COPY mvnw pom.xml ./

# Garantir permissões de execução para o script Maven Wrapper
RUN chmod +x mvnw

# Baixar e instalar as dependências Maven antes da construção
RUN ./mvnw clean install -U

# Construir a aplicação
RUN ./mvnw clean package

# Etapa 2: Executar a aplicação
FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# Copiar apenas os artefatos necessários da etapa de construção
COPY --from=build-image /app/target/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
