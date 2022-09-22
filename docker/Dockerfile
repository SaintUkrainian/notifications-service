FROM maven:openjdk

WORKDIR /notification-service

COPY . .

EXPOSE 8082

CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.profiles=prod"]