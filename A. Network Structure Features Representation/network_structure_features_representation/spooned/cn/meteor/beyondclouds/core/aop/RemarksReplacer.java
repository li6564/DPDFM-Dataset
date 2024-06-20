package cn.meteor.beyondclouds.core.aop;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 * 用户备注解析器
 *
 * @author meteor
 */
@org.aspectj.lang.annotation.Aspect
@org.springframework.stereotype.Component
@lombok.extern.slf4j.Slf4j
public class RemarksReplacer {
    private cn.meteor.beyondclouds.modules.user.service.IUserRemarksService userRemarksService;

    @org.springframework.beans.factory.annotation.Autowired
    public RemarksReplacer(cn.meteor.beyondclouds.modules.user.service.IUserRemarksService userRemarksService) {
        this.userRemarksService = userRemarksService;
    }

    @org.aspectj.lang.annotation.Around("@annotation(cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks)")
    public java.lang.Object processTx(org.aspectj.lang.ProceedingJoinPoint jp) throws java.lang.Throwable {
        java.lang.Object retValue = jp.proceed();
        org.aspectj.lang.reflect.MethodSignature methodSignature = ((org.aspectj.lang.reflect.MethodSignature) (jp.getSignature()));
        if (!(retValue instanceof cn.meteor.beyondclouds.core.api.Response)) {
            return retValue;
        }
        cn.meteor.beyondclouds.core.api.Response<?> response = cn.meteor.beyondclouds.core.api.Response.class.cast(retValue);
        if ((0 != response.getCode()) || (response.getData() == null)) {
            return retValue;
        }
        if (cn.meteor.beyondclouds.util.SubjectUtils.isAuthenticated()) {
            java.lang.Object data = response.getData();
            cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks replaceWithRemarks = methodSignature.getMethod().getAnnotation(cn.meteor.beyondclouds.core.annotation.ReplaceWithRemarks.class);
            java.util.Set<java.lang.Object> checked = new java.util.HashSet<>(100);
            com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.user.entity.UserRemarks> userRemarksQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
            userRemarksQueryWrapper.eq("user_id", cn.meteor.beyondclouds.util.SubjectUtils.getSubject().getId());
            java.util.Map<java.lang.String, java.lang.String> remarksMap = userRemarksService.list(userRemarksQueryWrapper).stream().collect(java.util.stream.Collectors.toMap(cn.meteor.beyondclouds.modules.user.entity.UserRemarks::getMarkedId, cn.meteor.beyondclouds.modules.user.entity.UserRemarks::getRemarks));
            if (org.springframework.util.CollectionUtils.isEmpty(remarksMap)) {
                return retValue;
            }
            parseAndReplace(data, replaceWithRemarks.id(), replaceWithRemarks.fields(), checked, remarksMap);
            return retValue;
        } else {
            return retValue;
        }
    }

    /**
     * 解析字段并替换值
     *
     * @param data
     * @param id
     * @param fields
     * @param checked
     * @param remarksMap
     */
    private void parseAndReplace(java.lang.Object data, java.lang.String id, java.lang.String[] fields, java.util.Set<java.lang.Object> checked, java.util.Map<java.lang.String, java.lang.String> remarksMap) {
        if (checked.contains(data)) {
            return;
        }
        // 将该对象添加到检测过的列表里面
        checked.add(data);
        // 如果对象为可迭代对象，则遍历对象
        if (data instanceof java.lang.Iterable) {
            for (java.lang.Object obj : ((java.util.Collection) (data))) {
                parseAndReplace(obj, id, fields, checked, remarksMap);
            }
        } else if (data instanceof cn.meteor.beyondclouds.common.dto.PageDTO) {
            // 如果对象为pageDTO，则遍历其分页内容
            java.util.List<java.lang.Object> dataList = ((cn.meteor.beyondclouds.common.dto.PageDTO) (data)).getDataList();
            if (!org.springframework.util.CollectionUtils.isEmpty(dataList)) {
                for (java.lang.Object obj : dataList) {
                    parseAndReplace(obj, id, fields, checked, remarksMap);
                }
            }
        } else {
            try {
                // 遍历对象的每个字段，如果是可迭代对象，则继续遍历
                // 获取用户ID
                java.lang.reflect.Field idField = getField(data, id);
                if (null == idField) {
                    return;
                }
                idField.setAccessible(true);
                java.lang.String userId = ((java.lang.String) (idField.get(data)));
                java.lang.String remarks = remarksMap.get(userId);
                // 如果有备注，则设置每个昵称的备注
                if (!org.springframework.util.StringUtils.isEmpty(remarks)) {
                    for (java.lang.String fieldName : fields) {
                        java.lang.reflect.Field nickField = getField(data, fieldName);
                        if (null != nickField) {
                            nickField.setAccessible(true);
                            nickField.set(data, remarks);
                            log.debug("替换 {} 的字段 {} 值为 {}", data.getClass().getName(), nickField.getName(), remarks);
                        }
                    }
                }
            } catch (java.lang.Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取字段，如果本类找不到，则从父类找
     *
     * @param obj
     * @param fieldName
     * @return  */
    private java.lang.reflect.Field getField(java.lang.Object obj, java.lang.String fieldName) {
        java.lang.Class clazz = obj.getClass();
        java.lang.reflect.Field field = null;
        do {
            try {
                field = clazz.getDeclaredField(fieldName);
            } catch (java.lang.NoSuchFieldException e) {
            }
            clazz = clazz.getSuperclass();
        } while ((null == field) && (null != clazz) );
        return field;
    }
}