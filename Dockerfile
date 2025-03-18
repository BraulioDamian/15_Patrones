# Fase 1: Compilación con Maven y JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
# Descarga las dependencias primero (mejora caché de capas)
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Fase 2: Imagen final con soporte para GUI
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar-with-dependencies.jar app.jar





# Instalar librerías X11 necesarias para Swing
RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libxft2 \
    libxinerama1 \
    libcups2 \
    libfontconfig1 \
    libfreetype6

# Configuración para Java Swing
ENV LANG=C.UTF-8 \
    LC_ALL=C.UTF-8 \
    DISPLAY=host.docker.internal:0 \
    _JAVA_AWT_WM_NONREPARENTING=1

# Punto de entrada con parámetros para mejorar rendimiento GUI
ENTRYPOINT ["java", "-jar", "-Dsun.java2d.opengl=true", "-Dawt.useSystemAAFontSettings=on", "app.jar"]