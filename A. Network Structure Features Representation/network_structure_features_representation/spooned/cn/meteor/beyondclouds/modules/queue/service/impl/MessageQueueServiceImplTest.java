package cn.meteor.beyondclouds.modules.queue.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@org.springframework.boot.test.context.SpringBootTest
@org.junit.runner.RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class MessageQueueServiceImplTest {
    @org.springframework.beans.factory.annotation.Autowired
    private cn.meteor.beyondclouds.modules.queue.service.IMessageQueueService messageQueueService;

    @org.junit.Test
    public void send() {
        // System.out.println(messageQueueService);
        // Message message = new Message();
        // message.setId("111L");
        // message.setMsg("hello");
        // messageQueueService.send(message);
        cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage itemUpdateMessage = new cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage();
        itemUpdateMessage.setItemId("123");
        itemUpdateMessage.setItemType(cn.meteor.beyondclouds.core.queue.message.DataItemType.PROJECT);
        itemUpdateMessage.setChangeType(cn.meteor.beyondclouds.core.queue.message.DataItemChangeType.ADD);
        messageQueueService.sendDataItemChangeMessage(itemUpdateMessage);
    }
}