package cn.meteor.beyondclouds.core.lock;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
/**
 *
 * @author meteor
 */
public class RedisLock implements java.util.concurrent.locks.Lock {
    private static final java.lang.String LOCK_PREFIX = "LOCK:";

    private static final long timeout = 1000;

    private java.lang.String lockKey;

    private org.springframework.data.redis.core.StringRedisTemplate redisTemplate;

    public RedisLock(org.springframework.data.redis.core.StringRedisTemplate redisTemplate, java.lang.String lockKey) {
        this.lockKey = cn.meteor.beyondclouds.core.lock.RedisLock.LOCK_PREFIX + lockKey;
        this.redisTemplate = redisTemplate;
    }

    @java.lang.Override
    public void lock() {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public void lockInterruptibly() throws java.lang.InterruptedException {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public boolean tryLock() {
        long expireAt = (java.lang.System.currentTimeMillis() + cn.meteor.beyondclouds.core.lock.RedisLock.timeout) + 1;
        byte[] lock = lockKey.getBytes();
        return ((boolean) (redisTemplate.execute(((org.springframework.data.redis.core.RedisCallback) (connection -> {
            boolean acquire = connection.setNX(lock, java.lang.String.valueOf(expireAt).getBytes());
            if (acquire) {
                return true;
            } else {
                byte[] value = connection.get(lock);
                if ((!java.util.Objects.isNull(value)) && (value.length > 1)) {
                    long expireTime = java.lang.Long.parseLong(new java.lang.String(value));
                    // 判断锁是否超时
                    if (java.lang.System.currentTimeMillis() > expireTime) {
                        // 锁已超时
                        byte[] oldValue = connection.getSet(lock, java.lang.String.valueOf(expireAt).getBytes());
                        return java.util.Arrays.equals(oldValue, value);
                    }
                }
            }
            return false;
        })))));
    }

    @java.lang.Override
    public boolean tryLock(long time, java.util.concurrent.TimeUnit unit) throws java.lang.InterruptedException {
        throw new java.lang.UnsupportedOperationException();
    }

    @java.lang.Override
    public void unlock() {
        redisTemplate.delete(lockKey);
    }

    @java.lang.Override
    public java.util.concurrent.locks.Condition newCondition() {
        throw new java.lang.UnsupportedOperationException();
    }
}