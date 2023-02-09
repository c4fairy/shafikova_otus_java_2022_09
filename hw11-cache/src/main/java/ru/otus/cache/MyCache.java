package ru.otus.cache;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements Cache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);

    private final WeakHashMap<K, V> map;
    private final List<Listener<K, V>> listeners;

    public MyCache() {
        this.map = new WeakHashMap<>();
        this.listeners = new ArrayList<>();
    }

    @Override
    public void put(K key, V value) {
        this.map.put(key, value);
        notify(key, value, "put cache");
    }

    @Override
    public void remove(K key) {
        V valueToRemove = map.remove(key);
        if (valueToRemove != null) {
            notify(key, valueToRemove, "remove cache");
        }
    }

    @Override
    public V get(K key) {
        V value = map.get(key);
        if (value != null) {
            notify(key, value, "get cache");
        }
        return value;
    }

    @Override
    public void addListener(Listener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener<K, V> listener) {
        listeners.remove(listener);
    }

    private void notify(K key, V value, String action) {
        this.listeners.forEach(listener -> {
            if (listener != null) {
                try {
                    listener.notify(key, value, action);
                } catch (Exception e) {
                    logger.error("Ошибка при вызове подписчика: {}, ошибка: {}", listener, e, e);
                }
            }
        });
    }
}
