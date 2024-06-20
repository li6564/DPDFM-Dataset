package cn.meteor.beyondclouds.core.validation.validator;
import org.springframework.util.StringUtils;
/**
 * NullOrNotBlank参数校验器
 *
 * @author meteor
 */
public class NullOrNotBlankValidator implements javax.validation.ConstraintValidator<cn.meteor.beyondclouds.core.validation.constraints.NullOrNotBlank, java.lang.String> {
    @java.lang.Override
    public boolean isValid(java.lang.String value, javax.validation.ConstraintValidatorContext context) {
        if (null == value) {
            return true;
        }
        if (!org.springframework.util.StringUtils.isEmpty(value.trim())) {
            return true;
        }
        return false;
    }
}