package cn.meteor.beyondclouds.modules.user.api;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author meteor
 */
@cn.meteor.beyondclouds.modules.user.api.RequestMapping("/api")
@cn.meteor.beyondclouds.modules.user.api.RestController
@io.swagger.annotations.Api(tags = "用户备注API")
public class UserRemarkApi {
    private cn.meteor.beyondclouds.modules.user.service.IUserRemarksService userRemarksService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setUserRemarksService(cn.meteor.beyondclouds.modules.user.service.IUserRemarksService userRemarksService) {
        this.userRemarksService = userRemarksService;
    }

    /**
     * 修改备注
     *
     * @param userId
     * 		被备注的用户的ID
     * @param remarksForm
     * 		备注
     * @return  */
    @cn.meteor.beyondclouds.modules.user.api.PutMapping("/user/{userId}/remarks")
    public cn.meteor.beyondclouds.core.api.Response alterRemarks(@cn.meteor.beyondclouds.modules.user.api.PathVariable("userId")
    java.lang.String userId, @cn.meteor.beyondclouds.modules.user.api.RequestBody
    cn.meteor.beyondclouds.modules.user.form.RemarksForm remarksForm) {
        cn.meteor.beyondclouds.core.authentication.Subject subject = cn.meteor.beyondclouds.util.SubjectUtils.getSubject();
        java.lang.String currentUserId = ((java.lang.String) (subject.getId()));
        // TODO 实现修改备注
        userRemarksService.alterRemarks(currentUserId, userId, remarksForm.getRemarks());
        return cn.meteor.beyondclouds.core.api.Response.success();
    }
}