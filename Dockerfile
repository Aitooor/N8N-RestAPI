# Usa una imagen base sin Java
FROM debian:bullseye-slim

# Instala OpenJDK 17
RUN apt-get update && \
    apt-get install -y openjdk-17-jre-headless && \
    rm -rf /var/lib/apt/lists/*

# Establece el directorio de trabajo
WORKDIR /n8n_restapi

# Copia los archivos de la aplicación al contenedor
COPY . .

# Construye la aplicación con Maven
RUN ./gradlew clean build

# Establece una imagen base más ligera para la ejecución
FROM debian:bullseye-slim

# Instala OpenJDK 17 (puedes omitir esto si la imagen base ya lo tiene instalado)
RUN apt-get update && \
    apt-get install -y openjdk-17-jre-headless && \
    rm -rf /var/lib/apt/lists/*

# Establece el directorio de trabajo
WORKDIR /n8n_restapi

# Copia solo los archivos necesarios para la ejecución desde la imagen de construcción
COPY --from=0 /n8n_restapi/build/libs/*.jar N8N_RestAPI.jar

# Expone el puerto en el que la aplicación se ejecutará
EXPOSE 9501

# Comando para ejecutar la aplicación cuando se inicia el contenedor
CMD ["java", "-jar", "N8N_RestAPI.jar"]