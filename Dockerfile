FROM openjdk:13-alpine
EXPOSE 5432
WORKDIR /
COPY entrypoint.sh entrypoint.sh
ADD build/libs/crawler.jar Crawler.jar
ENTRYPOINT ["./entrypoint.sh"]
