# Docker Setup Guide

This project uses a multi-stage Dockerfile for optimized builds and Docker Compose for orchestration.

## Architecture

### Multi-Stage Dockerfile

The Dockerfile consists of two stages:

1. **Builder Stage** (`maven:3.9.9-eclipse-temurin-17-alpine`)
   - Downloads dependencies
   - Compiles source code
   - Packages the application as a JAR

2. **Runtime Stage** (`eclipse-temurin:17-jre-alpine`)
   - Uses lightweight JRE image
   - Runs as non-root user for security
   - Includes health check endpoint
   - Exposes port 8084

### Docker Compose Services

- **mysql**: MySQL 8.0 database with persistent volume
- **app**: Spring Boot application built from Dockerfile

## Quick Start

### Build and Run with Docker Compose

```bash
# Build and start all services
docker-compose up --build

# Run in detached mode (background)
docker-compose up -d --build

# View logs
docker-compose logs -f

# View logs for specific service
docker-compose logs -f app
```

### Stop and Clean Up

```bash
# Stop services
docker-compose down

# Stop and remove volumes (WARNING: deletes database data)
docker-compose down -v

# Stop and remove images
docker-compose down --rmi all
```

## Docker Commands

### Build Docker Image Only

```bash
# Build the image
docker build -t demo-app:latest .

# Build with no cache
docker build --no-cache -t demo-app:latest .
```

### Run Container Standalone

```bash
# Run the app container (requires MySQL running separately)
docker run -p 8084:8084 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/mydatabase \
  -e SPRING_DATASOURCE_USERNAME=myuser \
  -e SPRING_DATASOURCE_PASSWORD=secret \
  demo-app:latest
```

### Inspect and Debug

```bash
# List running containers
docker ps

# View container logs
docker logs demo-app

# Execute commands inside container
docker exec -it demo-app sh

# Inspect container
docker inspect demo-app

# View container stats
docker stats demo-app
```

### Database Access

```bash
# Connect to MySQL container
docker exec -it demo-mysql mysql -u myuser -p

# Run MySQL commands directly
docker exec demo-mysql mysql -u myuser -psecret -e "SHOW DATABASES;"
```

## Health Checks

The application includes health checks at:
- Application: `http://localhost:8084/actuator/health`
- Container: Built-in Docker health check

```bash
# Check application health
curl http://localhost:8084/actuator/health

# Check Docker container health
docker inspect --format='{{.State.Health.Status}}' demo-app
```

## Environment Variables

Available environment variables for the app service:

| Variable | Description | Default |
|----------|-------------|---------|
| `SPRING_DATASOURCE_URL` | Database JDBC URL | `jdbc:mysql://mysql:3306/mydatabase` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `myuser` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `secret` |
| `SPRING_PROFILES_ACTIVE` | Spring profile | - |

## Volumes

- `mysql-data`: Persistent storage for MySQL database

```bash
# List volumes
docker volume ls

# Inspect volume
docker volume inspect demo_mysql-data

# Remove volume (WARNING: deletes data)
docker volume rm demo_mysql-data
```

## Networking

Services communicate through the `demo-network` bridge network.

```bash
# Inspect network
docker network inspect demo_demo-network
```

## Troubleshooting

### Application won't start

1. Check if MySQL is healthy:
   ```bash
   docker-compose ps
   ```

2. Check application logs:
   ```bash
   docker-compose logs app
   ```

### Port already in use

Change the port mapping in `compose.yaml`:
```yaml
ports:
  - '8085:8084'  # Use 8085 on host instead
```

### Database connection issues

Verify MySQL is accessible from the app container:
```bash
docker exec demo-app ping mysql
```

### Rebuild after code changes

```bash
docker-compose up --build app
```

## Best Practices

1. **Never commit sensitive data**: Use `.env` files for secrets (add to .gitignore)
2. **Use specific image versions**: Avoid `latest` tag in production
3. **Regular cleanup**: Remove unused images and containers
4. **Monitor resources**: Use `docker stats` to check resource usage
5. **Backup volumes**: Regularly backup MySQL data volume

## Production Considerations

For production deployments:

1. Use specific image versions (not `latest`)
2. Set up proper secret management
3. Configure resource limits (CPU, memory)
4. Set up monitoring and logging
5. Use health checks for orchestration
6. Implement proper backup strategy
7. Configure restart policies
