# Vertx App Runner

Module allows You to run vertx application consisting many vertx modules using one configuration file.
Every module in application can share the same global system properties defined in configuration file.


Sample config file:

```
{
    // global system properties available in every module
    "systemProperties": {
        "mongo.host": "127.0.0.1",
        "mongo.port": "27017"
    },
    //module definitions
    "mods": [
        {
            //module name
            "name": "module1.group~module1.name~0.0.1",
            //nuber of instances to run
            "instances": 1,
            //configuration passed to module
            "conf": {
                "property1": "value1",
                "property2": "value2"
            }
        },
        {
            //module name
            "name": "module2.group~module2.name~0.0.1",
            //nuber of instances to run
            "instances": 1,
            //configuration passed to module
            "conf": {
                "property1": "value1",
                "property2": "value2"
            }
        }
    ]
}
```

How to run:

```
vertx runMod pl.finsys.vertx~vertx-app-runner~1.0.0-SNAPSHOT -conf sample_conf.json
```

