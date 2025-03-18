# Fase 1: Compilación con Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline  # Mejora caché
COPY src ./src
RUN mvn clean package -DskipTests

# Fase 2: Imagen final con soporte GUI
FROM eclipse-temurin:21-jdk
WORKDIR /app
# Nombre exacto del JAR
COPY --from=build /app/target/Tienda-Abarrotes-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar

# Instalar librerías X11 (Swing)
RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libxinerama1 \
    libfontconfig1

ENV LANG=C.UTF-8 \
    LC_ALL=C.UTF-8 \
    DISPLAY=host.docker.internal:0 \
    _JAVA_AWT_WM_NONREPARENTING=1

CMD ["java", "-jar", "-Dsun.java2d.opengl=true", "app.jar"]