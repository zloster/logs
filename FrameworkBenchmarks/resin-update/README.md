Results are taken with low concurrency of 8 in a Vagrant VirtualBox machine.
In each subfolder there is a `latest/results.json` file with the results.

We are interested in the following results about `JSON` test.
"json": {
      "servlet": [
        {
          "latencyAvg": "2.81ms",
          "latencyMax": "235.28ms",
          "latencyStdev": "12.24ms",
          "totalRequests": 75147,
          "startTime": 1480150538,
          "endTime": 1480150554
        }
      ]
    },

# Results

Resin 4.0.41
totalRequests: 73159
latencyAvg: 3.66ms

Resin 4.0.49
totalRequests: 75147
latencyAvg: 2.81ms

# Conclusion

Some small increase in the processed requests.

