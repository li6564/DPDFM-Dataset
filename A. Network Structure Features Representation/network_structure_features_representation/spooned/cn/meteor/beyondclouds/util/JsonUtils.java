package cn.meteor.beyondclouds.util;
import java.io.IOException;
/**
 * Json转换工具
 *
 * @author 段启岩
 */
public class JsonUtils {
    private static com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();

    /**
     * 将输入的Java对象转换为Json字符串
     *
     * @param obj
     * @return  * @throws IOException
     */
    public static java.lang.String toJson(java.lang.Object obj) {
        try {
            java.io.StringWriter stringWriter = new java.io.StringWriter();
            com.fasterxml.jackson.core.JsonGenerator jsonGenerator = new com.fasterxml.jackson.core.JsonFactory().createGenerator(stringWriter);
            cn.meteor.beyondclouds.util.JsonUtils.mapper.writeValue(jsonGenerator, obj);
            jsonGenerator.close();
            return stringWriter.toString();
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("Json转换失败", e);
        }
    }

    /**
     * 将输入的Json字符串转换为Java对象
     *
     * @param jsonStr
     * 		待转换的Json字符串
     * @param type
     * 		要转换成的Java对象的类型
     * @return  */
    public static <T> T toBean(java.lang.String jsonStr, java.lang.Class<T> type) {
        try {
            return cn.meteor.beyondclouds.util.JsonUtils.mapper.readValue(jsonStr, type);
        } catch (java.lang.Exception e) {
            throw new java.lang.RuntimeException("Json转换失败", e);
        }
    }
}