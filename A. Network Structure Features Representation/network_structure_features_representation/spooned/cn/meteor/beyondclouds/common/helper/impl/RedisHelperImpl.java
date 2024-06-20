package cn.meteor.beyondclouds.common.helper.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
/**
 * Redis操作辅助实现类
 *
 * @author 段启岩
 */
@org.springframework.stereotype.Component
public class RedisHelperImpl implements cn.meteor.beyondclouds.common.helper.IRedisHelper {
    private org.springframework.data.redis.core.StringRedisTemplate redisTemplate;

    @org.springframework.beans.factory.annotation.Autowired
    public void setRedisTemplate(org.springframework.data.redis.core.StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @java.lang.Override
    public boolean expire(java.lang.String key, long seconds) {
        return redisTemplate.expire(key, seconds, java.util.concurrent.TimeUnit.SECONDS);
    }

    @java.lang.Override
    public long getExpire(java.lang.String key) {
        return redisTemplate.getExpire(key, java.util.concurrent.TimeUnit.SECONDS);
    }

    @java.lang.Override
    public boolean hasKey(java.lang.String key) {
        return redisTemplate.hasKey(key);
    }

    @java.lang.Override
    public void del(java.lang.String... key) {
        if (key.length == 1) {
            redisTemplate.delete(key[0]);
        } else {
            redisTemplate.delete(java.util.Arrays.asList(key));
        }
    }

    @java.lang.Override
    public <T> T get(java.lang.String key, java.lang.Class<T> type) {
        java.lang.String jsonValue = get(key);
        if (null == jsonValue) {
            return null;
        }
        try {
            if (type == java.lang.Integer.class) {
                return ((T) (java.lang.Integer.valueOf(jsonValue)));
            }
            if (type == java.lang.Long.class) {
                return ((T) (java.lang.Long.valueOf(jsonValue)));
            }
            if (type == java.lang.String.class) {
                return ((T) (jsonValue));
            }
            return cn.meteor.beyondclouds.util.JsonUtils.toBean(jsonValue, type);
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @java.lang.Override
    public java.lang.String get(java.lang.String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @java.lang.Override
    public void set(java.lang.String key, java.lang.Object value) throws cn.meteor.beyondclouds.common.exception.RedisOperationException {
        try {
            if (value instanceof java.lang.String) {
                redisTemplate.opsForValue().set(key, ((java.lang.String) (value)));
            } else {
                redisTemplate.opsForValue().set(key, cn.meteor.beyondclouds.util.JsonUtils.toJson(value));
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            throw new cn.meteor.beyondclouds.common.exception.RedisOperationException("数据存储失败");
        }
    }

    @java.lang.Override
    public void set(java.lang.String key, java.lang.Object value, long seconds) throws cn.meteor.beyondclouds.common.exception.RedisOperationException {
        try {
            if (value instanceof java.lang.String) {
                redisTemplate.opsForValue().set(key, ((java.lang.String) (value)), seconds, java.util.concurrent.TimeUnit.SECONDS);
            } else {
                redisTemplate.opsForValue().set(key, cn.meteor.beyondclouds.util.JsonUtils.toJson(value), seconds, java.util.concurrent.TimeUnit.SECONDS);
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            throw new cn.meteor.beyondclouds.common.exception.RedisOperationException();
        }
    }

    @java.lang.Override
    public long increment(java.lang.String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @java.lang.Override
    public long decrement(java.lang.String key, long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    @java.lang.Override
    public void setAdd(java.lang.String key, java.lang.String... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    @java.lang.Override
    public void setRemove(java.lang.String key, java.lang.String values) {
        redisTemplate.opsForSet().remove(key, values);
    }

    @java.lang.Override
    public java.util.Set<java.lang.String> setGet(java.lang.String key) {
        return redisTemplate.opsForSet().members(key);
    }
}