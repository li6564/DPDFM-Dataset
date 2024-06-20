package cn.meteor.beyondclouds.modules.search.repository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author meteor
 */
@org.springframework.stereotype.Repository
public interface ISearchRepository extends org.springframework.data.elasticsearch.repository.ElasticsearchRepository<cn.meteor.beyondclouds.modules.search.entity.SearchItem, cn.meteor.beyondclouds.modules.search.entity.SearchItemId> {}