# dropwizard/Hibernate batch updates

The HTTP query is: `http://localhost:9090/db/update?queries=20`
What SQL queries are hitting the JDBC driver when the above executes?
![VisualVM JDBC log](VisualVM-single-http-request.png)

The same with expanded stacktrace:
![VisualVM JDBC log expanded](VisualVM-single-http-request-expanded.png)

What `Hibernate` is logging as performed operations:
![Hibernate log](Hibernate-log.png)

The dropwizard [configuration file to enable hibernate logs](local-hello-world-mysql.yml).
