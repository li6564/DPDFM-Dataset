package cn.meteor.beyondclouds.util;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
/**
 *
 * @author meteor
 */
public class PageUtils {
    public static <T> com.baomidou.mybatisplus.core.metadata.IPage<T> emptyPage() {
        com.baomidou.mybatisplus.core.metadata.IPage<T> page = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>();
        page.setTotal(0L);
        page.setPages(0L);
        page.setCurrent(0);
        page.setRecords(java.util.List.of());
        return page;
    }

    public static <T> cn.meteor.beyondclouds.common.dto.PageDTO<T> emptyPageDTO() {
        cn.meteor.beyondclouds.common.dto.PageDTO<T> page = new cn.meteor.beyondclouds.common.dto.PageDTO<>();
        page.setTotalRecords(0L);
        page.setTotalPage(0L);
        page.setDataList(java.util.List.of());
        page.setPageSize(0);
        return page;
    }

    public static void copyMeta(com.baomidou.mybatisplus.core.metadata.IPage<?> src, cn.meteor.beyondclouds.common.dto.PageDTO<?> dest) {
        dest.setTotalPage(src.getPages());
        dest.setPageSize(src.getSize());
        dest.setTotalRecords(src.getTotal());
    }
}