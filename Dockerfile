FROM java:8
WORKDIR /
ADD build/libs/crawler.jar Crawler.jar
CMD ["java",  "-jar", "Crawler.jar"]
