package com.winbaoxian.module.security.utils;


import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dongxuanliang252
 * @date 2018-11-28 16:29
 */
public class MemoryExpirationCache<K, V> implements Cache<K, V> {

    private final Map<K, CacheEntry<K, V>> caches;
    private final ReferenceQueue<V> queue;
    private long lifetime;

    public MemoryExpirationCache(boolean softFlag) {
        this(softFlag, 0);
    }

    public MemoryExpirationCache(boolean softFlag, int lifetimeSecond) {
        this.lifetime = (long) (lifetimeSecond * 1000);
        if (softFlag) {
            this.queue = new ReferenceQueue();
        } else {
            this.queue = null;
        }
        this.caches = new ConcurrentHashMap<>();
    }

    @Override
    public V get(K k) throws CacheException {
        this.emptyQueue();
        CacheEntry<K, V> cacheEntry = this.caches.get(k);
        if (cacheEntry == null) {
            return null;
        } else {
            if (!cacheEntry.isValid()) {
                this.caches.remove(k);
                return null;
            } else {
                return cacheEntry.getValue();
            }
        }
    }

    @Override
    public V put(K k, V v) throws CacheException {
        this.emptyQueue();
        long expirationTime = this.lifetime == 0L ? 0L : System.currentTimeMillis() + this.lifetime;
        CacheEntry<K, V> newEntry = this.newEntry(k, v, expirationTime, this.queue);
        CacheEntry<K, V> oldEntry = this.caches.put(k, newEntry);
        if (oldEntry != null) {
            V oldV = oldEntry.getValue();
            oldEntry.invalidate();
            return oldV;
        }
        return null;
    }

    @Override
    public V remove(K k) throws CacheException {
        this.emptyQueue();
        CacheEntry<K, V> cacheEntry = this.caches.remove(k);
        if (cacheEntry != null) {
            V oldV = cacheEntry.getValue();
            cacheEntry.invalidate();
            return oldV;
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        if (this.queue != null) {
            Iterator it = this.caches.values().iterator();
            while (it.hasNext()) {
                CacheEntry cacheEntry = (CacheEntry) it.next();
                cacheEntry.invalidate();
            }
            while (this.queue.poll() != null) {
                //nothing to do
            }
        }
        this.caches.clear();
    }

    @Override
    public int size() {
        this.expungeExpiredEntries();
        return this.caches.size();
    }

    @Override
    public Set<K> keys() {
        this.expungeExpiredEntries();
        return this.caches.keySet();
    }

    @Override
    public Collection<V> values() {
        this.expungeExpiredEntries();
        return (Collection<V>) this.caches.values();
    }

    protected CacheEntry<K, V> newEntry(K key, V value, long expirationTime, ReferenceQueue<V> queue) {
        return (CacheEntry) (queue != null ? new SoftCacheEntry(key, value, expirationTime, queue) : new HardCacheEntry(key, value, expirationTime));
    }

    /**
     * 清空过期的数据
     */
    private void expungeExpiredEntries() {
        this.emptyQueue();
        if (this.lifetime != 0L) {
            Iterator it = this.caches.values().iterator();
            while (it.hasNext()) {
                CacheEntry cacheEntry = (CacheEntry) it.next();
                if (!cacheEntry.isValid()) {
                    it.remove();
                }
            }
        }
    }

    /**
     * 清空软引用的队列，当java对象被回收后，该对象的软引用会被放入队列中
     */
    private void emptyQueue() {
        if (this.queue != null) {

            while (true) {
                CacheEntry<K, V> queueEntry = (CacheEntry) this.queue.poll();
                if (queueEntry == null) {
                    return;
                }
                K key = queueEntry.getKey();
                if (key != null) {
                    CacheEntry<K, V> mapEntry = this.caches.remove(key);
                    if (mapEntry != null && queueEntry != mapEntry) {
                        this.caches.put(key, mapEntry);
                    }
                }
            }
        }
    }


    private static class HardCacheEntry<K, V> implements CacheEntry<K, V> {

        private K key;
        private V value;
        /**
         * 过期时间 小于等于0标识永久存活
         */
        private long expirationTime;

        HardCacheEntry(K key, V value, long expirationTime) {
            this.key = key;
            this.value = value;
            this.expirationTime = expirationTime;
        }


        @Override
        public boolean isValid() {
            boolean validFlag = System.currentTimeMillis() <= this.expirationTime;
            if (!validFlag) {
                this.invalidate();
            }
            return validFlag;
        }

        @Override
        public void invalidate() {
            this.key = null;
            this.value = null;
            this.expirationTime = -1L;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }
    }


    private static class SoftCacheEntry<K, V> extends SoftReference<V> implements CacheEntry<K, V> {

        private K key;
        private V value;
        /**
         * 过期时间 小于等于0标识永久存活
         */
        private long expirationTime;

        SoftCacheEntry(K key, V value, long expirationTime, ReferenceQueue<V> queue) {
            super(value, queue);
            this.key = key;
            this.value = value;
            this.expirationTime = expirationTime;
        }


        @Override
        public boolean isValid() {
            boolean validFlag = System.currentTimeMillis() <= this.expirationTime && this.get() != null;
            if (!validFlag) {
                this.invalidate();
            }
            return validFlag;
        }

        @Override
        public void invalidate() {
            this.clear();
            this.key = null;
            this.expirationTime = -1L;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return this.value;
        }
    }

    private interface CacheEntry<K, V> {

        boolean isValid();

        void invalidate();

        K getKey();

        V getValue();
    }

}
