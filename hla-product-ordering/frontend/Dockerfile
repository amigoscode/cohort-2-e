FROM node:19-alpine
ARG api_base_url
WORKDIR /app
COPY package*.json .
RUN npm i --silent
COPY . .
RUN echo "VITE_API_BASE_URL=http://localhost:8088" > .env
EXPOSE 3000
CMD ["npm", "run", "dev"]
