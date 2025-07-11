# TechBlog - Mikroserwisy

Projekt TechBlog to system mikroserwisowy oparty na Spring Boot z architekturą event-driven (Kafka), z wykorzystaniem m.in. Eureka, API Gateway i React na froncie.

---

## Spis treści

- [Opis projektu](#opis-projektu)
- [Technologie](#technologie)
- [Architektura](#architektura)
- [Uruchomienie](#uruchomienie)
- [Środowisko i konfiguracja](#środowisko-i-konfiguracja)
- [Frontend](#frontend)
- [Bezpieczeństwo](#bezpieczeństwo)
- [Docker i Docker Compose](#docker-i-docker-compose)
- [Rozwój i dalsze kroki](#rozwój-i-dalsze-kroki)

---

## Opis projektu

TechBlog to system blogowy z podziałem na mikroserwisy. Powstaje w celu rozwoju własnych umiejętnośći i poznania nowych technologi:

- `auth-service` – autoryzacja i rejestracja użytkowników, JWT
- `article-service` – zarządzanie artykułami
- `comment-service` – zarządzanie komentarzami
- `discovery-server` – Eureka do rejestracji serwisów
- `api-gateway` – brama API i routing
- `kafka` i `zookeeper` – system kolejek event-driven
- Frontend React z Redux do wyświetlania danych i obsługi uwierzytelnienia

---

## Technologie

- Java 21, Spring Boot 3.x (MVC)
- Spring Security (JWT)
- Apache Kafka + Zookeeper
- PostgreSQL (oddzielne bazy dla serwisów)
- Eureka Discovery Server
- Spring Cloud Gateway
- React + React-Bootstrap + Redux
- Docker + Docker Compose

---

## Architektura

- Usługi rejestrują się w Eureka
- API Gateway kieruje ruch do mikroserwisów i zarządza bezpieczeństwem
- Komunikacja asynchroniczna między serwisami za pomocą eventów Kafka (np. eventy komentarzy do article-service)
- Frontend subskrybuje dane przez REST i przechowuje stan w Redux

---

## Uruchomienie

1. Skopiuj `.env.example` do `.env` i wypełnij zmienne środowiskowe (bazy, hasła, JWT_SECRET itd.)
2. W głównym folderze techblog uruchom `mvn clean install`
3. Uruchom `docker-compose up --build`
4. Serwisy będą dostępne na portach:
    - Eureka: [http://localhost:8761](http://localhost:8761)
    - API Gateway: [http://localhost:8080](http://localhost:8080)
    - Auth Service: 8081
    - Article Service: 8082
    - Comment Service: 8083
5. Frontend: uruchom `npm install` i `npm start` w folderze frontend (w momencie kiedy frontend będzie bardziej rozbudowany, zostanie dodany do docker-compose)

---

## Środowisko i konfiguracja

- Wszystkie wrażliwe dane konfigurowane są przez `.env` i przekazywane do kontenerów przez `docker-compose.yml`
- CORS i bezpieczeństwo skonfigurowane głównie w API Gateway

---

## Frontend

- React z React-Bootstrap
- Routing React Router
- Autoryzacja z tokenem JWT przechowywanym w `localStorage`
- Stan aplikacji zarządzany przez Redux Thunk (asynchroniczne akcje)
- Strony: Home, Login, Register

---

## Bezpieczeństwo

- JWT tokeny generowane przez `auth-service`
- API Gateway filtruje i weryfikuje tokeny (filtr JWT)
- Ochrona endpointów na poziomie gateway i mikroserwisów
- CORS skonfigurowany w API Gateway

---

## Docker i Docker Compose

- Każdy mikroserwis ma swój Dockerfile
- Bazy danych PostgreSQL uruchamiane w kontenerach z osobnymi wolumenami danych
- Kafka i Zookeeper z oficjalnych obrazów Confluent
- Całość orkiestruje `docker-compose.yml` z odczytem zmiennych środowiskowych z `.env`

---

## Rozwój i dalsze kroki

- Implementacja frontendu z bardziej rozbudowanym dashboardem i sekcjami (np. taby na `/home`)
- Pełna obsługa eventów Kafka w frontendzie przez websocket lub polling (możliwość real-time update komentarzy)
- Testy integracyjne mikroserwisów
- Monitoring i logowanie
- Skalowanie mikroserwisów i Kafka

---


