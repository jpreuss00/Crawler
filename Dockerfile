FROM openjdk:13-alpine
WORKDIR /
COPY entrypoint.sh entrypoint.sh
ADD build/libs/crawler.jar Crawler.jar
ENTRYPOINT ["./entrypoint.sh"]
