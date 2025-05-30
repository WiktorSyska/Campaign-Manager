FROM openjdk:21-jdk-slim

WORKDIR /app

# Kopiuj pliki Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Pobierz zależności
RUN ./mvnw dependency:resolve

# Kopiuj kod źródłowy
COPY src src

# Zbuduj aplikację
RUN ./mvnw clean package -DskipTests

# Uruchom aplikację
EXPOSE 8080
CMD ["java", "-jar", "target/*.jar"]