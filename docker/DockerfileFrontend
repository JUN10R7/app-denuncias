# Etapa 1: Compilar Angular
FROM node:22-alpine as builder

WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build -- --configuration=production

# Etapa 2: Servir con NGINX
FROM nginx:alpine

# Copiar archivos compilados
COPY --from=builder /app/dist/frontend/browser /usr/share/nginx/html

# Configuración personalizada de NGINX
COPY nginx.conf /etc/nginx/conf.d/default.conf
