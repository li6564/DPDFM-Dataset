package cn.meteor.beyondclouds.modules.log.listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 *
 * @author meteor
 */
@org.springframework.stereotype.Component
@lombok.extern.slf4j.Slf4j
public class LogListener implements cn.meteor.beyondclouds.core.listener.DataItemChangeListener , cn.meteor.beyondclouds.core.listener.UserActionListener {
    private cn.meteor.beyondclouds.modules.log.service.ISysLogService sysLogService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setSysLogService(cn.meteor.beyondclouds.modules.log.service.ISysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    @java.lang.Override
    public void onDataItemAdd(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) throws java.lang.Exception {
        onDataChange(dataItemChangeMessage);
    }

    @java.lang.Override
    public void onDataItemUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) throws java.lang.Exception {
        onDataChange(dataItemChangeMessage);
    }

    @java.lang.Override
    public void onDataItemDelete(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) throws java.lang.Exception {
        onDataChange(dataItemChangeMessage);
    }

    @java.lang.Override
    public void onUserLogin(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage) {
        onUserAction(userActionMessage);
    }

    @java.lang.Override
    public void onUserLoginFailure(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage) {
        onUserAction(userActionMessage);
    }

    @java.lang.Override
    public void onUserLogout(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage) {
        onUserAction(userActionMessage);
    }

    private void onDataChange(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) {
        if (!cn.meteor.beyondclouds.core.constant.SysConstants.SYS_ID.equals(dataItemChangeMessage.getSubject().getId())) {
            cn.meteor.beyondclouds.core.queue.message.DataItemType itemType = dataItemChangeMessage.getItemType();
            switch (itemType) {
                case USER_STATISTICS :
                case BLOG_VIEW_NUM :
                    log.info("检测查看型日志，忽略日志记录：{}", dataItemChangeMessage);
                    return;
            }
            log.info("正在记录日志：{}", dataItemChangeMessage);
            cn.meteor.beyondclouds.modules.log.entity.SysLog sysLog = new cn.meteor.beyondclouds.modules.log.entity.SysLog(dataItemChangeMessage);
            sysLogService.save(sysLog);
        } else {
            log.info("检测到系统操作，忽略日志记录：{}", dataItemChangeMessage);
        }
    }

    private void onUserAction(cn.meteor.beyondclouds.core.queue.message.UserActionMessage userActionMessage) {
        log.info("正在记录日志：{}", userActionMessage);
        cn.meteor.beyondclouds.modules.log.entity.SysLog sysLog = new cn.meteor.beyondclouds.modules.log.entity.SysLog(userActionMessage);
        sysLogService.save(sysLog);
    }
}