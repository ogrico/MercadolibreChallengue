# Etapa de construcción
FROM maven:3.8.4-openjdk-17-slim AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Empaquetar la aplicación
RUN mvn package

# Etapa de ejecución
FROM openjdk:17-slim

# Establecer el directorio de trabajo en el contenedor
WORKDIR /app

# Copiar el archivo JAR generado en la etapa de construcción al contenedor
COPY --from=builder /app/target/Challenge-1.0.0.jar .

CMD ["java", "-jar", "Challenge-1.0.0.jar"]
