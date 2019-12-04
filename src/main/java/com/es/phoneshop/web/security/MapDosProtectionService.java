package com.es.phoneshop.web.security;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MapDosProtectionService implements DosProtectionService {
    private static DosProtectionService dosProtectionService;
    private Map<String, Integer> requestCountMap;
    private static Lock lock = new ReentrantLock();

    private MapDosProtectionService() {
        requestCountMap = new ConcurrentHashMap<>();
    }

    public static MapDosProtectionService getInstance() {
        lock.lock();
        try {
            if (dosProtectionService == null) {
                dosProtectionService = new MapDosProtectionService();
            }
            return (MapDosProtectionService) dosProtectionService;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public boolean allowed(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        Integer count = requestCountMap.get(ip);

        if (count == null) {
            count = 0;
        } else if (count > 20000) {
            return false;
        }

        count += 1;
        requestCountMap.put(ip, count);
        return true;
    }
}
