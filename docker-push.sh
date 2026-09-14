#!/bin/bash

# Docker Hub Push Script for animal-api
# Repository: jhona1dev/animal-api

# Color codes for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

echo -e "${BLUE}=== Docker Hub Push Script ===${NC}"

# Docker Hub configuration
DOCKER_USERNAME="jhona1dev"
DOCKER_REPO="animal-api"
DOCKER_IMAGE="${DOCKER_USERNAME}/${DOCKER_REPO}"

# Get version tag (default to latest if not provided)
TAG="${1:-latest}"

echo -e "${BLUE}Building image: ${DOCKER_IMAGE}:${TAG}${NC}"

# Build the Docker image
docker build -t ${DOCKER_IMAGE}:${TAG} .

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Build successful${NC}"
else
    echo -e "${RED}✗ Build failed${NC}"
    exit 1
fi

# Also tag as latest if a specific version was provided
if [ "$TAG" != "latest" ]; then
    echo -e "${BLUE}Tagging as latest...${NC}"
    docker tag ${DOCKER_IMAGE}:${TAG} ${DOCKER_IMAGE}:latest
fi

# Check if logged in to Docker Hub
echo -e "${BLUE}Checking Docker Hub authentication...${NC}"
docker info | grep -q "Username: ${DOCKER_USERNAME}"

if [ $? -ne 0 ]; then
    echo -e "${RED}Not logged in to Docker Hub${NC}"
    echo -e "${BLUE}Please login:${NC}"
    docker login

    if [ $? -ne 0 ]; then
        echo -e "${RED}✗ Login failed${NC}"
        exit 1
    fi
fi

# Push the image
echo -e "${BLUE}Pushing ${DOCKER_IMAGE}:${TAG} to Docker Hub...${NC}"
docker push ${DOCKER_IMAGE}:${TAG}

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Push successful: ${DOCKER_IMAGE}:${TAG}${NC}"
else
    echo -e "${RED}✗ Push failed${NC}"
    exit 1
fi

# Push latest tag if it exists
if [ "$TAG" != "latest" ]; then
    echo -e "${BLUE}Pushing ${DOCKER_IMAGE}:latest to Docker Hub...${NC}"
    docker push ${DOCKER_IMAGE}:latest

    if [ $? -eq 0 ]; then
        echo -e "${GREEN}✓ Push successful: ${DOCKER_IMAGE}:latest${NC}"
    else
        echo -e "${RED}✗ Push failed${NC}"
        exit 1
    fi
fi

echo -e "${GREEN}=== Deployment Complete ===${NC}"
echo -e "${BLUE}Image available at: https://hub.docker.com/r/${DOCKER_USERNAME}/${DOCKER_REPO}${NC}"
echo -e "${BLUE}Pull command: docker pull ${DOCKER_IMAGE}:${TAG}${NC}"
