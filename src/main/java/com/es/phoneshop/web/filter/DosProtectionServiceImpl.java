package com.es.phoneshop.web.filter;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class DosProtectionServiceImpl implements DosProtectionService {
    private static DosProtectionServiceImpl instance;

    private Map<String, AtomicLong> countMap;
    private int MAX_REQUEST_COUNT = 200;
    private Date SAVED_DATE;

    protected DosProtectionServiceImpl() {
        countMap = Collections.synchronizedMap(new HashMap<>());
        SAVED_DATE = new Date();
    }

    public static DosProtectionServiceImpl getInstance() {
        if (instance == null) {
            synchronized (DosProtectionServiceImpl.class) {
                if (instance == null) {
                    instance = new DosProtectionServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean isAllowed(String ip) {
        if (new Date().getSeconds() - SAVED_DATE.getSeconds() > 60) {
            countMap.clear();
            SAVED_DATE = new Date();
        }
        AtomicLong count = countMap.get(ip);
        if (count == null) {
            count = new AtomicLong(1);
            countMap.put(ip, count);
        }
        return count.incrementAndGet() < MAX_REQUEST_COUNT;
    }
}
