package org.project.streamingapi.patterns.singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class InMemoryCache {

    private static final Logger log = LoggerFactory.getLogger(InMemoryCache.class);

    private final Map<String, Object> store = new ConcurrentHashMap<>();

    private InMemoryCache() {
    }

    private static class Holder {
        private static final InMemoryCache INSTANCE = new InMemoryCache();
    }

    public static InMemoryCache getInstance() {
        return Holder.INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) store.get(key);
    }

    public void put(String key, Object value) {
        if (value == null) {
            store.remove(key);
        } else {
            store.put(key, value);
        }
    }

    public boolean contains(String key) {
        return store.containsKey(key);
    }

    public void evict(String key) {
        store.remove(key);
    }

    public void clear() {
        store.clear();
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrCompute(String key, Supplier<T> supplier) {
        Object existing = store.get(key);
        if (existing != null) {
            log.info("CACHE HIT: {}", key);
            return (T) existing;
        }
        log.info("CACHE MISS: {}", key);
        T computed = supplier.get();
        if (computed != null) {
            store.put(key, computed);
        }
        return computed;
    }
}
