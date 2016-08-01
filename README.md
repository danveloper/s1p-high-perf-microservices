Ratpack + Spring Boot + Async PostgreSQL
===

This is a demonstration of a Ratpack application that leverages Spring Boot and makes an asynchronous database call to PostgreSQL. This is from the Spring One Platform 2016 talk, "High Performance Microservices with Ratpack and Spring Boot". This talk demonstrates leveraging Ratpack as the high throughput, non-blocking web layer to an application that is built with the comforts and familiarities of Spring Boot.

Performance Testing
===

With no additional server configuration or tuning, the following results were recorded:

EC2 t2.micro 
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
