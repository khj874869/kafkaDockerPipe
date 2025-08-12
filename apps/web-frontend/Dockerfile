FROM node:20-alpine
WORKDIR /app
COPY package.json package-lock.json* ./
RUN npm install --omit=dev || true
COPY . .
EXPOSE 5173
CMD ["npm","run","dev","--","--host"]
