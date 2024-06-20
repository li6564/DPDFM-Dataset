package cn.meteor.beyondclouds.util;
/**
 *
 * @author meteor
 */
public class SubjectUtils {
    public static cn.meteor.beyondclouds.core.authentication.Subject getSubject() {
        cn.meteor.beyondclouds.core.authentication.Subject subject = ((cn.meteor.beyondclouds.core.authentication.Subject) (cn.meteor.beyondclouds.util.ThreadLocalMap.get(cn.meteor.beyondclouds.core.constant.SysConstants.HTTP_ATTRIBUTE_SUBJECT)));
        if (null == subject) {
            return cn.meteor.beyondclouds.core.authentication.Subject.anonymous();
        }
        return subject;
    }

    public static boolean isAuthenticated() {
        cn.meteor.beyondclouds.core.authentication.Subject subject = ((cn.meteor.beyondclouds.core.authentication.Subject) (cn.meteor.beyondclouds.util.ThreadLocalMap.get(cn.meteor.beyondclouds.core.constant.SysConstants.HTTP_ATTRIBUTE_SUBJECT)));
        return (null != subject) && subject.isAuthenticated();
    }
}