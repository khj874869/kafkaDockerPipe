# AKS GitOps Order Platform (Portfolio)

- Docker/Kubernetes 운영 예시
- CI/CD (GitHub Actions) + Jenkinsfile + GitLab CI 샘플
- Argo CD GitOps
- Azure AKS/ACR Terraform
- 오픈소스: Postgres, Kafka, Prometheus, Grafana

## 로컬 실행
```bash
cp infra/.env.example infra/.env
make compose-up
# web: http://localhost:5173, api: http://localhost:8080, grafana: http://localhost:3000
```

The API now validates order commands, manages its schema through Flyway, publishes typed Kafka events keyed by order ID, and disables Hibernate schema mutation. The worker rejects malformed events and exposes separate success/failure metrics. CI builds every production image and validates the Compose configuration from the repository root.
