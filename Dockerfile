FROM openjdk:13-alpine
WORKDIR /
ADD build/libs/crawler.jar Crawler.jar
CMD ["java", "-jar", "Crawler.jar", "--port=$PORT"]
