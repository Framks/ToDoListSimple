FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=build /app/target/*.jar /app/app.jar

RUN apt-get update && \
    apt-get install -y zabbix-agent && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

COPY zabbix_agentd.conf /etc/zabbix/zabbix_agentd.conf
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

EXPOSE 9010 10050 8080

CMD ["/entrypoint.sh"]
