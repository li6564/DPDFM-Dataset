package cn.meteor.beyondclouds.modules.search.repository;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@org.springframework.boot.test.context.SpringBootTest
@org.junit.runner.RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class ISearchRepositoryTest {
    @org.springframework.beans.factory.annotation.Autowired
    private cn.meteor.beyondclouds.modules.search.repository.ISearchRepository searchRepository;

    @org.junit.Test
    public void test() {
        java.lang.System.out.println(searchRepository);
        cn.meteor.beyondclouds.modules.search.entity.SearchItem searchItem = new cn.meteor.beyondclouds.modules.search.entity.SearchItem();
        searchItem.setContent("我爱中国");
        searchItem.setCover("null");
        searchItem.setDescription("测试");
        searchItem.setTitle("ok");
        searchItem.setCreateTime(new java.util.Date());
        searchItem.setItemId("aad");
        searchItem.setItemType(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG);
        searchRepository.save(searchItem);
    }

    @org.junit.Test
    public void testQuery() {
        org.elasticsearch.index.query.QueryBuilder queryBuilder = org.elasticsearch.index.query.QueryBuilders.matchQuery("content", "我们中国啊");
        java.lang.Iterable<cn.meteor.beyondclouds.modules.search.entity.SearchItem> searchItems = searchRepository.search(queryBuilder);
        for (cn.meteor.beyondclouds.modules.search.entity.SearchItem searchItem : searchItems) {
            java.lang.System.out.println(searchItem);
        }
    }

    /**
     * 中文、拼音混合搜索
     *
     * @param content
     * 		the content
     * @return dis max query builder
     */
    public org.elasticsearch.index.query.DisMaxQueryBuilder structureQuery(java.lang.String content) {
        // 使用dis_max直接取多个query中，分数最高的那一个query的分数即可
        org.elasticsearch.index.query.DisMaxQueryBuilder disMaxQueryBuilder = org.elasticsearch.index.query.QueryBuilders.disMaxQuery();
        // boost 设置权重,只搜索匹配name和disrector字段
        org.elasticsearch.index.query.QueryBuilder ikNameQuery = org.elasticsearch.index.query.QueryBuilders.matchQuery("name", content).boost(2.0F);
        org.elasticsearch.index.query.QueryBuilder pinyinNameQuery = org.elasticsearch.index.query.QueryBuilders.matchQuery("name.pinyin", content);
        org.elasticsearch.index.query.QueryBuilder ikDirectorQuery = org.elasticsearch.index.query.QueryBuilders.matchQuery("director", content).boost(2.0F);
        disMaxQueryBuilder.add(ikNameQuery);
        disMaxQueryBuilder.add(pinyinNameQuery);
        disMaxQueryBuilder.add(ikDirectorQuery);
        return disMaxQueryBuilder;
    }
}