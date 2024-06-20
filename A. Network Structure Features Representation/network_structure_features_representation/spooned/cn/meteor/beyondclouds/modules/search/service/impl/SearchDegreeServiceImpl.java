package cn.meteor.beyondclouds.modules.search.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 段启岩
 * @since 2020-02-13
 */
@org.springframework.stereotype.Service
public class SearchDegreeServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<cn.meteor.beyondclouds.modules.search.mapper.SearchDegreeMapper, cn.meteor.beyondclouds.modules.search.entity.SearchDegree> implements cn.meteor.beyondclouds.modules.search.service.ISearchDegreeService {
    @java.lang.Override
    public void updateTopicDegreeBatch(java.util.List<cn.meteor.beyondclouds.modules.search.entity.SearchDegree> searchDegreeList) {
        java.util.List<java.lang.String> itemIds = searchDegreeList.stream().map(cn.meteor.beyondclouds.modules.search.entity.SearchDegree::getItemId).collect(java.util.stream.Collectors.toList());
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<cn.meteor.beyondclouds.modules.search.entity.SearchDegree> searchDegreeQueryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        searchDegreeQueryWrapper.in("item_id", itemIds);
        searchDegreeQueryWrapper.in("item_type", cn.meteor.beyondclouds.core.queue.message.DataItemType.TOPIC.name());
        java.util.List<cn.meteor.beyondclouds.modules.search.entity.SearchDegree> searchDegreesInDb = list(searchDegreeQueryWrapper);
        java.util.Map<java.lang.String, cn.meteor.beyondclouds.modules.search.entity.SearchDegree> stringSearchDegreeMap = searchDegreesInDb.stream().collect(java.util.stream.Collectors.toMap(cn.meteor.beyondclouds.modules.search.entity.SearchDegree::getItemId, searchDegree -> searchDegree));
        searchDegreeList.forEach(searchDegree -> {
            java.lang.String itemId = searchDegree.getItemId();
            if (stringSearchDegreeMap.containsKey(itemId)) {
                cn.meteor.beyondclouds.modules.search.entity.SearchDegree finalSearchDegree = stringSearchDegreeMap.get(itemId);
                finalSearchDegree.setDegree(cn.meteor.beyondclouds.modules.search.util.TopicScoreUtils.setScale(finalSearchDegree.getDegree() + searchDegree.getDegree()));
                updateById(finalSearchDegree);
            } else {
                save(searchDegree);
            }
        });
    }
}