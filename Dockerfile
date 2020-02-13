FROM java:8
WORKDIR /
ADD build/libs/Crawler.jar Crawler.jar
CMD ["java",  "-jar", "Crawler.jar"]
