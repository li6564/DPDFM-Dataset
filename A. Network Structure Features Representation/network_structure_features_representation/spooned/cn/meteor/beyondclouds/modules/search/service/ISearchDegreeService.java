package cn.meteor.beyondclouds.modules.search.service;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-13
 */
public interface ISearchDegreeService extends com.baomidou.mybatisplus.extension.service.IService<cn.meteor.beyondclouds.modules.search.entity.SearchDegree> {
    /**
     * 匹配更新数据的热度
     *
     * @param searchDegreeList
     */
    void updateTopicDegreeBatch(java.util.List<cn.meteor.beyondclouds.modules.search.entity.SearchDegree> searchDegreeList);
}