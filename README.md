Ratpack + Spring Boot + Async PostgreSQL
===

This is a demonstration of a Ratpack application that leverages Spring Boot and makes an asynchronous database call to PostgreSQL. This is from the Spring One Platform 2016 talk, "High Performance Microservices with Ratpack and Spring Boot". This talk demonstrates leveraging Ratpack as the high throughput, non-blocking web layer to an application that is built with the comforts and familiarities of Spring Boot.

Performance Testing
===

With no additional server configuration or tuning, the following results were recorded:

Trial 1
---

| EC2 Instance Type | RDS Instance Type | Max Req/Sec Recorded |
|-------------------|-------------------|----------------------|
| t2.micro          | t2.micro          | 9,529                |

```
# ./wrk -t4 -c72 -d60s -R10000 http://localhost:5050
Running 1m test @ http://localhost:5050
  4 threads and 72 connections
  Thread calibration: mean lat.: 171.367ms, rate sampling interval: 930ms
  Thread calibration: mean lat.: 199.882ms, rate sampling interval: 1000ms
  Thread calibration: mean lat.: 204.732ms, rate sampling interval: 1061ms
  Thread calibration: mean lat.: 187.197ms, rate sampling interval: 983ms
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     1.45s     1.13s    4.34s    59.59%
    Req/Sec     2.39k   102.57     2.52k    82.91%
  571768 requests in 1.00m, 190.30MB read
Requests/sec:   9529.38
Transfer/sec:      3.17MB
```

Trial 2
---

| EC2 Instance Type | RDS Instance Type | Max Req/Sec Recorded |
|-------------------|-------------------|----------------------|
| t2.medium         | t2.micro          | 10,259               |

```
# ./wrk -t4 -c72 -d60s -R15000 http://localhost:5050
Running 1m test @ http://localhost:5050
  4 threads and 72 connections
  Thread calibration: mean lat.: 1557.261ms, rate sampling interval: 5705ms
  Thread calibration: mean lat.: 1553.418ms, rate sampling interval: 5693ms
  Thread calibration: mean lat.: 1555.895ms, rate sampling interval: 5701ms
  Thread calibration: mean lat.: 1557.657ms, rate sampling interval: 5709ms
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    11.04s     4.57s   19.22s    57.52%
    Req/Sec     2.56k    22.48     2.60k    75.00%
  615528 requests in 1.00m, 204.87MB read
Requests/sec:  10258.71
Transfer/sec:      3.41MB
```

Trial 3
---

| EC2 Instance Type | RDS Instance Type | Max Req/Sec Recorded |
|-------------------|-------------------|----------------------|
| t2.medium         | t2.medium         |                      |
