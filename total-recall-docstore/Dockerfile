FROM anapsix/alpine-java:8_server-jre_unlimited
ENV JAVA_OPTS="-Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true"
COPY ./target/*.jar ./
COPY entrypoint.sh ./
EXPOSE 9802
ENTRYPOINT exec java -Dspring.profiles.active=docker -jar total-recall-docstore-0.0.1-SNAPSHOT.jar