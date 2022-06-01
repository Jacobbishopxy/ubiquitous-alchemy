# Ubiquitous Resource Centre

## Switching databases

```java
@Autowired
private Mongo mongo;
```

```java
MongoOperations mongoOps = new MongoTemplate(mongo, "databaseName");
```
