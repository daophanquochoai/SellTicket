# --- BUILD REACT APP ---
FROM node:18 AS build
WORKDIR /app

# Copy package.json và package-lock.json trước để cache dependencies
COPY package.json package-lock.json ./
RUN npm install --frozen-lockfile

# Copy toàn bộ mã nguồn
COPY . .

# Truyền biến môi trường
ARG REACT_APP_API_BASE_URL
ARG REACT_APP_PUBLIC_KEY
ENV REACT_APP_API_BASE_URL=${REACT_APP_API_BASE_URL}
ENV REACT_APP_PUBLIC_KEY=${REACT_APP_PUBLIC_KEY}

# Build React App
RUN npm run build

# --- DEPLOY VỚI NGINX ---
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port
EXPOSE 80

# Run Nginx
CMD ["nginx", "-g", "daemon off;"]
