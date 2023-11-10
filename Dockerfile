# Etapa 1: Construir a aplicação
FROM maven:3.8.4-openjdk-17 AS builder

# Configurar variáveis de ambiente
ENV SPRING_PROFILES_ACTIVE=prod

# Criar um diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar apenas os arquivos necessários para a construção
COPY src src
COPY pom.xml .

# Empacotar a aplicação (build)
RUN mvn clean package -Pprod

# Etapa 2: Executar a aplicação
FROM openjdk:17-jdk-slim

# Criar um diretório de trabalho dentro do contêiner
WORKDIR /app

# Copiar apenas os artefatos necessários da etapa 1
COPY --from=builder /app/target/nome-do-seu-arquivo.jar /app/app.jar

# Expor a porta que a aplicação Spring Boot utiliza (por padrão, é a porta 8080)
EXPOSE 8080

# Configurar algumas opções para a execução em produção (ajuste conforme necessário)
ENV JAVA_OPTS="-Xmx256m -Xms128m"

# Comando para executar a aplicação quando o contêiner iniciar
CMD ["java", "-jar", "app.jar"]
