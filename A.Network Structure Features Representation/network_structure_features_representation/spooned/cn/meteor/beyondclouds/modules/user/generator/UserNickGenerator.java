package cn.meteor.beyondclouds.modules.user.generator;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * 用户昵称生成器
 *
 * @author meteor
 */
@org.springframework.stereotype.Component
public class UserNickGenerator implements cn.meteor.beyondclouds.core.generator.Generator<java.lang.String> {
    private static final java.lang.String NICK_PREFIX = "y";

    private cn.meteor.beyondclouds.modules.user.service.IUserService userService;

    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    private cn.meteor.beyondclouds.core.lock.RedisLockFactory redisLockFactory;

    @org.springframework.beans.factory.annotation.Autowired
    public UserNickGenerator(cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper, cn.meteor.beyondclouds.core.lock.RedisLockFactory redisLockFactory) {
        this.redisHelper = redisHelper;
        this.redisLockFactory = redisLockFactory;
    }

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserService(cn.meteor.beyondclouds.modules.user.service.IUserService userService) {
        this.userService = userService;
    }

    @java.lang.Override
    public java.lang.String next() {
        boolean repeat;
        java.lang.String nickName;
        do {
            nickName = generateNickName();
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.User> userQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            userQueryWrapper.eq("nick_name", nickName);
            userService.list(userQueryWrapper);
            if (userService.list(userQueryWrapper).size() > 0) {
                repeat = true;
            } else {
                repeat = false;
            }
        } while (repeat );
        return nickName;
    }

    private java.lang.String generateNickName() {
        java.time.LocalDateTime localDateTime = java.time.LocalDateTime.now();
        int year = localDateTime.getYear();
        year = year % 10;
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        java.lang.String datePrefix = java.lang.String.format("%d%d%d", year, month, day);
        datePrefix.replaceAll("0", "");
        int count = getAndIncreaseNickCount(datePrefix);
        return (cn.meteor.beyondclouds.modules.user.generator.UserNickGenerator.NICK_PREFIX + datePrefix) + count;
    }

    /**
     *
     * @param datePrefix
     * @return  */
    private int getAndIncreaseNickCount(java.lang.String datePrefix) {
        java.util.concurrent.locks.Lock lock = redisLockFactory.createLock(datePrefix);
        // 获取redis锁
        while (!lock.tryLock()) {
        } 
        java.lang.Integer count = redisHelper.get(cn.meteor.beyondclouds.core.redis.RedisKey.USER_NICK_GEN(datePrefix), java.lang.Integer.class);
        if (null == count) {
            count = 1;
            redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.USER_NICK_GEN(datePrefix), 1);
        } else {
            redisHelper.set(cn.meteor.beyondclouds.core.redis.RedisKey.USER_NICK_GEN(datePrefix), count + 1);
        }
        // 释放锁
        lock.unlock();
        return count;
    }
}