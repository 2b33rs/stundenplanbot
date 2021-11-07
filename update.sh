git pull
docker build --tag stundenplanbot:latest .
docker-compose down -v
docker-compose up -d