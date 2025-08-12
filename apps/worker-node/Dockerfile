FROM node:20-alpine
WORKDIR /app
COPY package.json package-lock.json* ./
RUN npm install --omit=dev || true
COPY src ./src
EXPOSE 9100
CMD ["npm","start"]
