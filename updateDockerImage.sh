#!/bin/bash

versionLatest="latest"
version="0.0.3"
repository="aitooor/ignisguard"

# Limpia y borra todos los builders de Buildx viejos inactivos
docker buildx rm --all-inactive --force

# Construye y sube la imagen multiplataforma con la etiqueta "latest"
docker buildx create --use
docker buildx inspect --bootstrap
docker buildx build --platform linux/amd64,linux/arm64 -t $repository:$versionLatest --push . -t $repository:$version --push

# Limpia y borra todos los builders de Buildx inactivos(si estan encendidos no se borraran)
docker buildx rm --all-inactive --force
