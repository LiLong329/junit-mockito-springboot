package com.didispace.redis;

public enum RedisKeys {
    INST;

    public enum ProjectKeys {
        CLOUD_WAREHOUSE,
        ORDER_BANK,
        WMS
    }

    public enum ModuleKeys {
        SYSTEM,CARS,SHIPS,WAREHOUSE
    }

    public enum BusinessKeys {
        SESSION,USER,SITE
    }
}
