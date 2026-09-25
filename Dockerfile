# Etapa de build e execução usando Maven com JDK 17
FROM maven:3.9-eclipse-temurin-17-alpine

# Diretório de trabalho
WORKDIR /app

# Copia pom.xml e baixa dependências (camada de cache)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o código-fonte
COPY src ./src

# Compila o projeto
RUN mvn compile -B

# Cria diretório para banco H2 persistido em arquivo
RUN mkdir -p /app/data

# Variáveis de ambiente para deploy online
ENV PORT=8080
ENV DATABASE_FILE=/app/data/assistenciadb
EXPOSE 8080

# Inicia a aplicação com Tomcat embarcado
CMD ["mvn", "exec:exec"]
