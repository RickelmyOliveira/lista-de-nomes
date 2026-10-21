FROM eclipse-temurin:25-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

RUN mv target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]