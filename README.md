# Prometheus and Grafana with Spring Boot

This repository provides a sample Spring Boot application integrated with Prometheus for metrics collection and Grafana for visualization and monitoring. It demonstrates how to expose application metrics using Spring Boot Actuator and Micrometer, scrape them with Prometheus, and create dashboards in Grafana for observing JVM metrics, HTTP requests, and other key performance indicators.

The project includes a basic Spring Boot REST API, configuration files for Prometheus, and a Docker Compose setup to run everything locally.
<br/>
<img width="4320" height="3360" alt="Image" src="https://github.com/user-attachments/assets/8fe35a94-1aad-4f6c-bd63-217969d56fef" />
<br/>

## Features
- Spring Boot application with Actuator and Micrometer for Prometheus metrics export.
- Prometheus configuration to scrape metrics from the Spring Boot app.
- Grafana setup with pre-configured dashboards for JVM and application metrics.
- Docker Compose for easy deployment of Prometheus and Grafana.
- Example endpoints to generate metrics (e.g., a simple "/hello" API).

## Prerequisites
- Java 17 or higher (for Spring Boot 3.x).
- Maven 3.6+ (for building the Spring Boot app).
- Docker and Docker Compose (for running Prometheus and Grafana).
- Git (to clone the repository).

## Installation

1. **Clone the Repository**
   ```
   git clone https://github.com/moshclouds/Prometheus-And-Grafana-With-Springboot.git
   cd Prometheus-And-Grafana-With-Springboot
   ```

2. **Build the Spring Boot Application**
   Use Maven to build the project:
   ```
   mvn clean install
   ```

3. **Run the Spring Boot Application**
   Start the app locally:
   ```
   mvn spring-boot:run
   ```
   The application will run on `http://localhost:8080`. Metrics will be exposed at `http://localhost:8080/actuator/prometheus`.

## Configuration

### Spring Boot Dependencies (pom.xml excerpt)
Add the following dependencies to enable Actuator and Prometheus metrics:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
</dependencies>
```

### Application Properties (application.yml or application.properties)
Enable the Prometheus endpoint:
```yaml
management:
  endpoints:
    web:
      exposure:
        include: prometheus, health, info
  metrics:
    export:
      prometheus:
        enabled: true
```

### Prometheus Configuration (prometheus.yml)
Configure Prometheus to scrape metrics from the Spring Boot app. Example for local setup:
```yaml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

scrape_configs:
  - job_name: 'spring-boot-app'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8080']  # Use 'localhost:8080' if not in Docker
```

### Grafana Environment (grafana.env)
Set default admin credentials (change these in production):
```
GF_SECURITY_ADMIN_USER=admin
GF_SECURITY_ADMIN_PASSWORD=admin
```

## Running with Docker Compose

The repository includes a `docker-compose.yml` to spin up Prometheus and Grafana.

```yaml
version: '3.9'
services:
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    ports:
      - "9090:9090"

  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    ports:
      - "3000:3000"
    env_file:
      - ./grafana.env
    depends_on:
      - prometheus
```

Start the services:
```
docker-compose up -d
```

- Access Prometheus at `http://localhost:9090`.
- Access Grafana at `http://localhost:3000` (login with admin/admin).

## Setting Up Grafana

1. Log in to Grafana.
2. Add Prometheus as a data source:
   - URL: `http://prometheus:9090` (or `http://localhost:9090` if accessing externally).
3. Import a dashboard:
   - Use the provided `dashboard.json` in the repository (if available), or search for "JVM Micrometer" on Grafana.com (Dashboard ID: 4701 or similar).
   - Example dashboard includes panels for JVM memory, CPU usage, HTTP requests, and more.

## Usage

1. **Test the Spring Boot API**
   - Hit the example endpoint: `curl http://localhost:8080/hello`
   - This will generate metrics like `http_server_requests_seconds_count`.

2. **View Metrics in Prometheus**
   - Go to `http://localhost:9090/targets` to verify the scrape target.
   - Query metrics, e.g., `jvm_memory_used_bytes`.

3. **Visualize in Grafana**
   - Create or import dashboards to monitor metrics in real-time.

<img width="1883" height="892" alt="Image" src="https://github.com/user-attachments/assets/2e17b8f9-9080-419b-b4e8-ec7592e4e101" />

<img width="1918" height="561" alt="Image" src="https://github.com/user-attachments/assets/628eed18-9b37-4c1e-a946-b55c7f1d8235" />

<img width="1212" height="230" alt="Image" src="https://github.com/user-attachments/assets/7a398238-0fa9-445f-bb32-e807cfa11b8e" />

<img width="1844" height="880" alt="Image" src="https://github.com/user-attachments/assets/926df214-767c-4d1a-aa51-f7a5ac595367" />

## Troubleshooting

- **Metrics not appearing?** Ensure the `/actuator/prometheus` endpoint is accessible and Prometheus is configured to scrape it.
- **Docker host issues:** Replace `host.docker.internal` with your machine's IP if needed.
- **Grafana login issues:** Check the `grafana.env` file and restart the container.
- For more logs, run Docker Compose without `-d`.

## Contributing
Feel free to fork the repository and submit pull requests. Issues and suggestions are welcome!

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## References
- [Spring Boot Actuator Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Micrometer Prometheus Registry](https://micrometer.io/docs/registry/prometheus)
- [Prometheus Documentation](https://prometheus.io/docs/)
- [Grafana Documentation](https://grafana.com/docs/)