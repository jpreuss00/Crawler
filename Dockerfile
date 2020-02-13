FROM java:8
WORKDIR /
ADD Crawler.jar Crawler.jar
CMD ["java",  "-jar", "Crawler.jar"]
