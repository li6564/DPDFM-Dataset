package cn.meteor.beyondclouds.core.lock;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
/**
 *
 * @author meteor
 */
public class RedisLockFactory {
    private org.springframework.data.redis.core.StringRedisTemplate redisTemplate;

    public RedisLockFactory(org.springframework.data.redis.core.StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public java.util.concurrent.locks.Lock createLock(java.lang.String lockKey) {
        return new cn.meteor.beyondclouds.core.lock.RedisLock(redisTemplate, lockKey);
    }
}