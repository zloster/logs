## PostgreJDBC 9.4.1212 vs 42.0.0
The results are in the `resutls.json` file.
Please note that this data is from Vagrant VirtualBox VM - 2 CPU cores and 3GB RAM. So the actual RPS numbers are not significant as is. I'm using this setup for comparison if there are differences in performance between the two drivers. If you look closely you also will notice that on the higher request concurrencies there are errors in the request processing: for example timeouts. The load-generator applies too much load on the server for available resources.

The branch with the code is here: https://github.com/zloster/FrameworkBenchmarks/tree/postgrejdbc
To replicate the experiment and get results yourself: `tfb --mode benchmark --test servlet servlet-postgres-v42` from the `~/FrameworkBenchmarks` directory in the VM.
