FROM amazoncorretto:17.0.7-alpine

WORKDIR /app
COPY rinha-backend/build/libs/rinha-backend-1.0.0-WEBFLUX.jar /app/service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "service.jar"]