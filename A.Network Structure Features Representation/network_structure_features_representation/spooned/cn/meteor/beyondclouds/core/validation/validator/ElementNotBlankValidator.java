package cn.meteor.beyondclouds.core.validation.validator;
import org.springframework.util.StringUtils;
/**
 * ElementNotBlank参数校验器
 *
 * @author meteor
 */
public class ElementNotBlankValidator implements javax.validation.ConstraintValidator<cn.meteor.beyondclouds.core.validation.constraints.ElementNotBlank, java.util.Collection<java.lang.String>> {
    @java.lang.Override
    public boolean isValid(java.util.Collection<java.lang.String> value, javax.validation.ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        for (java.lang.String str : value) {
            if (org.springframework.util.StringUtils.isEmpty(str)) {
                return false;
            }
        }
        return true;
    }
}