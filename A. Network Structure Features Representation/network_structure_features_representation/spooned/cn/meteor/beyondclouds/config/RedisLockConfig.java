package cn.meteor.beyondclouds.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
/**
 *
 * @author meteor
 */
@org.springframework.context.annotation.Configuration
public class RedisLockConfig {
    private org.springframework.data.redis.core.StringRedisTemplate redisTemplate;

    @org.springframework.beans.factory.annotation.Autowired
    public RedisLockConfig(org.springframework.data.redis.core.StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @org.springframework.context.annotation.Bean
    cn.meteor.beyondclouds.core.lock.RedisLockFactory redisLockFactory() {
        return new cn.meteor.beyondclouds.core.lock.RedisLockFactory(redisTemplate);
    }
}