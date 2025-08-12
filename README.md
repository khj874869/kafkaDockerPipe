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