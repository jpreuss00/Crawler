FROM java:8
WORKDIR /
ADD build/libs/crawler.jar Crawler.jar
EXPOSE 8080
CMD ["java",  "-jar", "Crawler.jar"]
