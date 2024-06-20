package cn.meteor.beyondclouds.modules.search.service.impl;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@org.springframework.boot.test.context.SpringBootTest
@org.junit.runner.RunWith(org.springframework.test.context.junit4.SpringRunner.class)
public class SearchServiceImplTest {
    @org.springframework.beans.factory.annotation.Autowired
    private cn.meteor.beyondclouds.modules.search.service.ISearchService searchService;

    @org.springframework.beans.factory.annotation.Autowired
    private cn.meteor.beyondclouds.modules.search.repository.ISearchRepository searchRepository;

    @org.junit.Test
    public void getSearchItem() {
        java.util.Optional<cn.meteor.beyondclouds.modules.search.entity.SearchItem> optionalSearchItem = searchService.getSearchItem(cn.meteor.beyondclouds.core.queue.message.DataItemType.BLOG, "123");
        if (optionalSearchItem.isPresent()) {
            java.lang.System.out.println(optionalSearchItem.get());
        }
    }

    @org.junit.Test
    public void testSearch() {
        java.lang.System.out.println("=======================================================");
        org.elasticsearch.index.query.QueryBuilder queryBuilder = org.elasticsearch.index.query.QueryBuilders.constantScoreQuery(org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery("itemType", "USER"));
        for (cn.meteor.beyondclouds.modules.search.entity.SearchItem item : searchRepository.search(queryBuilder)) {
            java.lang.System.out.println(item);
        }
    }
}