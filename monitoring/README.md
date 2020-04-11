### Usage

```sh
make  run                 # builds grafana and prometheus 
                          # images and initializes all three containers 
                          # that forms the infra (grafana, prometheus and 
                          # node_exporter).  

make  update-dashboards   # updates the list of json files that represent
                          # the dashboards configured in Grafana.

```

### Installation

1. In `/monitoring/grafana/provisioning/datasources/all.yml` , change the `url` to the host of the prometheus
2. In `monitoring/prometheus/config.yml`, add new job if there is any server
3. To start Prometheus and Grafana, use : ```docker-compose up -d --build --force-recreate``` 
4. Go to  http://{GRAFANA_HOST}:3000, UserName/Password: admin/admin, and import `monitoring/grafana/dashboards/default.json`
as dashboard
5. Done
