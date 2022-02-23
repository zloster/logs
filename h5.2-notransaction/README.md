# Hibernate 5.2 ORM test case about the missing flush and the JPA specification

The issue is described here: https://hibernate.atlassian.net/browse/HHH-12288

> Basically the Hibernate ORM adheres to the JPA specs even when we are using it outside the specification (in this case - not using transactions).
>
> Andrea Boriero 
> February 14, 2018, 10:36 AM
> JPA 2.1 specification section 3.10.8 Queries and Flush Mode states that "If there is no transaction active, the persistence provider must not flush to the database"

