Demonstration of FrameworkBenchmarks toolset instability in the Vagrant development environment

The toolset is reporting failures with some database tests without actual problems with them. It only happens on the first run after the VM has been started. Note that the failing test is always the first test to be verified. Here is [the console log](console-log.txt) from the Vagrant VM executing twice the same two benchmarks for `gemini-postgres` and `dropwizard-postgres`.
The first benchmark for `gemini` is reported as failure for the `db test`. Immediate second run succeeds without reporting a problem.
The result directories from the two runs are available here:
  [20170331152432](/20170331152432) - the first run
  [20170331154604](/20170331154604) - the second run
