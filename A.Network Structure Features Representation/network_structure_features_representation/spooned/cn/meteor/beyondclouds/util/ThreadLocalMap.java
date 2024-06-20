package cn.meteor.beyondclouds.util;
/**
 *
 * @author meteor
 */
public class ThreadLocalMap {
    private static final java.lang.ThreadLocal<java.util.Map<java.lang.String, java.lang.Object>> THREAD_CONTEXT = new cn.meteor.beyondclouds.util.ThreadLocalMap.MapThreadLocal();

    /**
     * 存值
     *
     * @param key
     * 		the key
     * @param value
     * 		the value
     */
    public static void put(java.lang.String key, java.lang.Object value) {
        cn.meteor.beyondclouds.util.ThreadLocalMap.getContextMap().put(key, value);
    }

    /**
     * 删除对象
     *
     * @param key
     * 		the key
     * @return the object
     */
    public static java.lang.Object remove(java.lang.String key) {
        return cn.meteor.beyondclouds.util.ThreadLocalMap.getContextMap().remove(key);
    }

    /**
     * 获取对象
     *
     * @param key
     * 		the key
     * @return the object
     */
    public static java.lang.Object get(java.lang.String key) {
        return cn.meteor.beyondclouds.util.ThreadLocalMap.getContextMap().get(key);
    }

    private static class MapThreadLocal extends java.lang.ThreadLocal<java.util.Map<java.lang.String, java.lang.Object>> {
        /**
         * Initial value map.
         *
         * @return the map
         */
        @java.lang.Override
        protected java.util.Map<java.lang.String, java.lang.Object> initialValue() {
            return new java.util.HashMap<>(10);
        }
    }

    /**
     * 取得thread context Map的实例。
     *
     * @return thread context Map的实例
     */
    private static java.util.Map<java.lang.String, java.lang.Object> getContextMap() {
        return cn.meteor.beyondclouds.util.ThreadLocalMap.THREAD_CONTEXT.get();
    }

    /**
     * 清理线程所有被hold住的对象。以便重用！
     */
    public static void remove() {
        cn.meteor.beyondclouds.util.ThreadLocalMap.getContextMap().clear();
    }
}