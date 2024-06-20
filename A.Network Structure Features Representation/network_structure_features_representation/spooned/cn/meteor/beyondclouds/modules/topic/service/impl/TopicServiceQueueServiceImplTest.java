package cn.meteor.beyondclouds.modules.topic.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@org.springframework.boot.test.context.SpringBootTest
@org.junit.runner.RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class TopicServiceQueueServiceImplTest {
    @org.springframework.beans.factory.annotation.Autowired
    private cn.meteor.beyondclouds.modules.topic.service.ITopicService topicService;

    @org.junit.Test
    public void increaseReferenceCount() {
        topicService.increaseReferenceCount("229d7edbbe76badb0937238dc168c329", 2);
    }

    @org.junit.Test
    public void increaseReferenceCountBatch() {
        topicService.increaseReferenceCountBatch(java.util.List.of("229d7edbbe76badb0937238dc168c329", "32d0053d77e27fecbad5928c66ff617f"), 2);
    }
}