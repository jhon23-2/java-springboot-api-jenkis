# Docker Hub Deployment Guide

## Repository Information
- **Docker Hub Username**: jhona1dev
- **Repository Name**: animal-api
- **Full Image Name**: jhona1dev/animal-api

## Quick Commands

### 1. Login to Docker Hub
```bash
docker login
```
Enter your Docker Hub credentials when prompted.

### 2. Build the Image
```bash
# Build with 'latest' tag
docker build -t jhona1dev/animal-api:latest .

# Build with specific version tag
docker build -t jhona1dev/animal-api:v1.0.0 .

# Build with multiple tags
docker build -t jhona1dev/animal-api:latest -t jhona1dev/animal-api:v1.0.0 .
```

### 3. Push to Docker Hub
```bash
# Push latest
docker push jhona1dev/animal-api:latest

# Push specific version
docker push jhona1dev/animal-api:v1.0.0

# Push all tags
docker push jhona1dev/animal-api --all-tags
```

## Using the Automated Script

A script `docker-push.sh` is provided for easier deployment:

```bash
# Make script executable (on Linux/Mac)
chmod +x docker-push.sh

# Push with 'latest' tag
./docker-push.sh

# Push with specific version
./docker-push.sh v1.0.0

# Push with semantic version
./docker-push.sh v1.2.3
```

## Manual Step-by-Step Process

### Step 1: Login to Docker Hub
```bash
docker login
# Username: jhona1dev
# Password: <your-docker-hub-password>
```

### Step 2: Build the Image
```bash
docker build -t jhona1dev/animal-api:v1.0.0 .
```

### Step 3: Tag for Latest (Optional)
```bash
docker tag jhona1dev/animal-api:v1.0.0 jhona1dev/animal-api:latest
```

### Step 4: Push to Docker Hub
```bash
docker push jhona1dev/animal-api:v1.0.0
docker push jhona1dev/animal-api:latest
```

## Verify the Push

Check your image on Docker Hub:
- URL: https://hub.docker.com/r/jhona1dev/animal-api
- Or run: `docker search jhona1dev/animal-api`

## Pull and Run the Image

Others can now pull and run your image:

```bash
# Pull the image
docker pull jhona1dev/animal-api:latest

# Run the container
docker run -p 8084:8084 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/mydatabase \
  -e SPRING_DATASOURCE_USERNAME=myuser \
  -e SPRING_DATASOURCE_PASSWORD=secret \
  jhona1dev/animal-api:latest
```

## Update docker-compose.yaml to Use Docker Hub Image

Instead of building locally, you can use the published image:

```yaml
services:
  app:
    image: jhona1dev/animal-api:latest  # Use published image
    # Remove the 'build' section
    container_name: demo-app
    ports:
      - '8084:8084'
    # ... rest of configuration
```

## Versioning Strategy

Recommended tagging strategy:

```bash
# Development
docker build -t jhona1dev/animal-api:dev .

# Staging
docker build -t jhona1dev/animal-api:staging .

# Production with semantic versioning
docker build -t jhona1dev/animal-api:v1.0.0 .
docker tag jhona1dev/animal-api:v1.0.0 jhona1dev/animal-api:latest

# Git commit hash (for traceability)
docker build -t jhona1dev/animal-api:$(git rev-parse --short HEAD) .
```

## Automated CI/CD Integration

### GitHub Actions Example
```yaml
- name: Login to Docker Hub
  uses: docker/login-action@v2
  with:
    username: jhona1dev
    password: ${{ secrets.DOCKER_HUB_TOKEN }}

- name: Build and push
  uses: docker/build-push-action@v4
  with:
    push: true
    tags: jhona1dev/animal-api:latest
```

### Jenkins Pipeline Example
```groovy
stage('Push to Docker Hub') {
    steps {
        script {
            docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                def app = docker.build("jhona1dev/animal-api:${env.BUILD_NUMBER}")
                app.push()
                app.push('latest')
            }
        }
    }
}
```

## Troubleshooting

### Error: "denied: requested access to the resource is denied"
- Make sure you're logged in: `docker login`
- Verify your username is correct
- Check repository exists on Docker Hub
- Ensure you have push permissions

### Error: "no basic auth credentials"
- Run `docker login` again
- Check credentials are correct

### Build is slow
- Use `.dockerignore` to exclude unnecessary files
- Leverage Docker layer caching
- Consider using Docker BuildKit: `DOCKER_BUILDKIT=1 docker build -t jhona1dev/animal-api:latest .`

### Image is too large
- Use multi-stage builds (already implemented)
- Use Alpine-based images (already implemented)
- Clean up unnecessary files in Dockerfile

## Best Practices

1. **Use semantic versioning**: v1.0.0, v1.1.0, v2.0.0
2. **Tag with git commit hash** for traceability
3. **Always tag latest** for the current stable version
4. **Use Docker Hub Access Tokens** instead of password for CI/CD
5. **Keep secrets out of images**: Use environment variables
6. **Scan images for vulnerabilities**: `docker scout cves jhona1dev/animal-api:latest`
7. **Add image labels** for metadata:
   ```dockerfile
   LABEL org.opencontainers.image.source="https://github.com/jhona1dev/animal-api"
   LABEL org.opencontainers.image.description="Animal API Spring Boot Application"
   LABEL org.opencontainers.image.version="1.0.0"
   ```

## Useful Commands

```bash
# List local images
docker images jhona1dev/animal-api

# Remove local image
docker rmi jhona1dev/animal-api:v1.0.0

# View image details
docker inspect jhona1dev/animal-api:latest

# Check image size
docker images jhona1dev/animal-api --format "table {{.Repository}}\t{{.Tag}}\t{{.Size}}"

# Logout from Docker Hub
docker logout
```

## Docker Hub Repository Settings

Consider configuring on Docker Hub:
1. **Description**: Add a detailed description of your API
2. **README**: Auto-sync from GitHub repository
3. **Automated Builds**: Link to GitHub for automatic builds
4. **Webhooks**: Trigger events after push
5. **Collaborators**: Add team members if needed
