package cn.meteor.beyondclouds.common.helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@org.springframework.boot.test.context.SpringBootTest
@org.junit.runner.RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class IRedisHelperTest {
    private cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper;

    @org.springframework.beans.factory.annotation.Autowired
    public void setRedisHelper(cn.meteor.beyondclouds.common.helper.IRedisHelper redisHelper) {
        this.redisHelper = redisHelper;
    }

    @org.junit.Test
    public void testSetValue() {
        try {
            redisHelper.set("test", "test1");
        } catch (cn.meteor.beyondclouds.common.exception.RedisOperationException e) {
            e.printStackTrace();
        }
    }
}