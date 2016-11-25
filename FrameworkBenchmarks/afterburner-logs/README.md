Results are taken with low concurrency of 8 in a Vagrant VirtualBox machine.
In each subfolder there is a `latest/results.json` file with the results.

We are interested in the followin results about `JSON` test.
"json": {
      "servlet": [
        {
          "latencyAvg": "1.75ms", 
          "latencyMax": "40.60ms", 
          "latencyStdev": "2.91ms", 
          "totalRequests": 73622, 
          "startTime": 1480013298, 
          "endTime": 1480013313
        }
      ]
    }, 

# Results

Servlet + Jackson 2.8.5 + Afterburner module 2.8.5
totalRequests: 73622
latencyAvg: 1.75ms

Servlet + Jackson 2.8.5
totalRequests: 74595
latencyAvg: 1.65ms

Servlet baseline (before the upgrades)
totalRequests: 64156
latencyAvg: 2.24ms

# Comments

The testcase is too simple and `Afterburner` module could not help. There is 16% increase in the throughput (total requests) after the upgrade to Jackson 2.8.5 with good decrease in average latency. So upgrade your libraries regularly but check the performance first.
