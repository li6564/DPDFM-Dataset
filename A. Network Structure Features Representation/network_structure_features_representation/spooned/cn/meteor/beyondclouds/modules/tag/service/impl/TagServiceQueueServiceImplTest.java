package cn.meteor.beyondclouds.modules.tag.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@org.springframework.boot.test.context.SpringBootTest
@org.junit.runner.RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class TagServiceQueueServiceImplTest {
    @org.springframework.beans.factory.annotation.Autowired
    private cn.meteor.beyondclouds.modules.tag.service.ITagService tagService;

    @org.junit.Test
    public void increaseReferenceCount() {
        tagService.increaseReferenceCount("0718c0c591eb6c957b9c8691e808538e", 2);
    }

    @org.junit.Test
    public void increaseReferenceCountBatch() {
        tagService.increaseReferenceCountBatch(java.util.List.of("0718c0c591eb6c957b9c8691e808538e", "32eb229153d0f2b8919a1e0442e15146"), 2);
    }

    @org.junit.Test
    public void decreaseReferenceCount() {
        tagService.decreaseReferenceCount("0718c0c591eb6c957b9c8691e808538e", 2);
    }

    @org.junit.Test
    public void decreaseReferenceCountBatch() {
        tagService.decreaseReferenceCountBatch(java.util.List.of("0718c0c591eb6c957b9c8691e808538e", "32eb229153d0f2b8919a1e0442e15146"), 2);
    }
}