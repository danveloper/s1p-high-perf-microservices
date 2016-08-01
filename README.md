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
| t2.medium         | t2.medium         | 17,399               |

```
# ./wrk -t4 -c72 -d60s -R20000 http://localhost:5050
Running 1m test @ http://localhost:5050
  4 threads and 72 connections
  Thread calibration: mean lat.: 668.483ms, rate sampling interval: 2269ms
  Thread calibration: mean lat.: 666.548ms, rate sampling interval: 2248ms
  Thread calibration: mean lat.: 677.119ms, rate sampling interval: 2285ms
  Thread calibration: mean lat.: 669.698ms, rate sampling interval: 2271ms
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     4.33s     1.84s    8.08s    57.61%
    Req/Sec     4.36k   120.14     4.52k    67.06%
  1043910 requests in 1.00m, 347.45MB read
Requests/sec:  17398.57
Transfer/sec:      5.79MB
```

Trial 4
---

| EC2 Instance Type | RDS Instance Type | Max Req/Sec Recorded |
|-------------------|-------------------|----------------------|
| m4.large          | t2.medium         | 13,884               |

```
# ./wrk -t4 -c72 -d60s -R20000 http://localhost:5050
Running 1m test @ http://localhost:5050
  4 threads and 72 connections
  Thread calibration: mean lat.: 1405.393ms, rate sampling interval: 4927ms
  Thread calibration: mean lat.: 1663.260ms, rate sampling interval: 6381ms
  Thread calibration: mean lat.: 1610.518ms, rate sampling interval: 5992ms
  Thread calibration: mean lat.: 1506.068ms, rate sampling interval: 5328ms
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    10.48s     4.99s   23.63s    65.29%
    Req/Sec     3.49k   113.93     3.69k    55.88%
  833028 requests in 1.00m, 277.26MB read
Requests/sec:  13884.09
Transfer/sec:      4.62MB
```

_Note_: It shouldn't be surprising that performance is less on AWS's memory-intensive instance class, because Ratpack is CPU-bound. It's worth keeping in mind that this is the case and that you need to do some work to find out the proper instance class for your application's use case.

Trail 5
---

| EC2 Instance Type | RDS Instance Type | Max Req/Sec Recorded |
|-------------------|-------------------|----------------------|
| c4.large          | t2.medium         | 15,373               |

```
# ./wrk -t4 -c72 -d60s -R20000 http://localhost:5050
Running 1m test @ http://localhost:5050
  4 threads and 72 connections
  Thread calibration: mean lat.: 1257.768ms, rate sampling interval: 5152ms
  Thread calibration: mean lat.: 1201.606ms, rate sampling interval: 5210ms
  Thread calibration: mean lat.: 1223.431ms, rate sampling interval: 5107ms
  Thread calibration: mean lat.: 1079.383ms, rate sampling interval: 4616ms
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     7.88s     4.21s   19.32s    64.80%
    Req/Sec     3.86k    79.60     4.04k    62.16%
  922323 requests in 1.00m, 306.98MB read
Requests/sec:  15372.73
Transfer/sec:      5.12MB
```

Trial 6
---

| EC2 Instance Type | RDS Instance Type | Max Req/Sec Recorded |
|-------------------|-------------------|----------------------|
| c4.large          | t2.large          | 16,085               |

```
# ./wrk -t4 -c72 -d60s -R20000 http://localhost:5050
Running 1m test @ http://localhost:5050
  4 threads and 72 connections
  Thread calibration: mean lat.: 938.818ms, rate sampling interval: 4073ms
  Thread calibration: mean lat.: 943.311ms, rate sampling interval: 4079ms
  Thread calibration: mean lat.: 1007.105ms, rate sampling interval: 4089ms
  Thread calibration: mean lat.: 975.187ms, rate sampling interval: 4182ms
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     6.57s     4.36s   14.74s    60.80%
    Req/Sec     4.03k    36.11     4.10k    70.21%
  965082 requests in 1.00m, 321.21MB read
Requests/sec:  16085.08
Transfer/sec:      5.35MB
```

Trial 7
---

| EC2 Instance Type | RDS Instance Type | Max Req/Sec Recorded |
|-------------------|-------------------|----------------------|
| c4.xlarge         | t2.large          | 23,149               |

```
# ./wrk -t4 -c100 -d60s -R25000 http://localhost:5050
Running 1m test @ http://localhost:5050
  4 threads and 100 connections
  Thread calibration: mean lat.: 346.475ms, rate sampling interval: 1235ms
  Thread calibration: mean lat.: 347.531ms, rate sampling interval: 1232ms
  Thread calibration: mean lat.: 347.625ms, rate sampling interval: 1254ms
  Thread calibration: mean lat.: 347.428ms, rate sampling interval: 1245ms
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency     2.54s     1.13s    5.64s    59.59%
    Req/Sec     5.80k    71.22     5.89k    83.02%
  1388845 requests in 1.00m, 462.25MB read
Requests/sec:  23148.93
Transfer/sec:      7.70MB
```

_Note_: The first thing I'd like to note is that this combo went from a cold JVM to 20,000 rps without breaking a sweat. Normally, I run the test over 5 mins to ensure the JVM is warm (usually doesn't improve dramatically after the first round of calls), but this combo was seeing performance right out of the gates. The second thing I'd like to point out is that I increased the number of persistent connections to see if I could squeeze a few more RPS out of the run, so here you'll see 4 x 100 instead of my normal 4 x 72.
