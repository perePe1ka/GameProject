# Инструкция по настройке и работе с Grafana, Prometheus, Compass, Redis и ELK

## Grafana
1. Зарегистрируйтесь в системе (используйте логин и пароль `admin`).
2. Перейдите на дашборд.
3. Справа выберите **New** → **Import**.
4. В поле **Find and import dashboards for common applications at** введите `4701` и нажмите **Load**.
5. В поле **Prometheus - configure a new data source**, выберите **Prometheus**.
6. В разделе **Connection** укажите URL: `http://prometheus:9090` и нажмите **Save & Test** (если результат зеленый, значит всё настроено правильно).
7. Повторите шаги 2–4, но в шаге 4 сразу выберите Prometheus и нажмите **Import**.
8. Не забудьте изменить фильтр времени с 24 часов на, например, несколько минут.

---

## Prometheus
1. Откройте Prometheus по адресу: `http://localhost:9090/query`.
2. В поле **Query** введите: `http_server_requests_seconds_count`.

---

## Compass
1. В списке баз данных выберите базу данных `test`.
2. Зайдите в неё и просмотрите данные.

---

## Redis
1. В контейнере выполните команду:
   ```bash
   redis-cli
   KEYS *
