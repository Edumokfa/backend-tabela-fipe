FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /usr/src/integracaoFipe
COPY . .
RUN mvn clean package

FROM openjdk:18
WORKDIR /app
COPY --from=build /usr/src/integracaoFipe/target/integracao-tabela-fipe-mongodb.jar integracaoFipe.jar
ENTRYPOINT ["java", "-jar", "integracaoFipe.jar"]