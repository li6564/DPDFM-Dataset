package cn.meteor.beyondclouds.modules.search.listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 *
 * @author meteor
 */
@org.springframework.stereotype.Component
@lombok.extern.slf4j.Slf4j
public class SearchItemItemChangeListener implements cn.meteor.beyondclouds.core.listener.DataItemChangeListener {
    private cn.meteor.beyondclouds.modules.search.service.ISearchService searchService;

    @org.springframework.beans.factory.annotation.Autowired
    public SearchItemItemChangeListener(cn.meteor.beyondclouds.modules.search.service.ISearchService searchService) {
        this.searchService = searchService;
    }

    @java.lang.Override
    public void onDataItemAdd(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) throws java.lang.Exception {
        searchService.saveSearchItem(dataItemChangeMessage.getItemType(), java.lang.String.valueOf(dataItemChangeMessage.getItemId()));
    }

    @java.lang.Override
    public void onDataItemUpdate(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) throws java.lang.Exception {
        searchService.updateSearchItem(dataItemChangeMessage.getItemType(), java.lang.String.valueOf(dataItemChangeMessage.getItemId()));
    }

    @java.lang.Override
    public void onDataItemDelete(cn.meteor.beyondclouds.core.queue.message.DataItemChangeMessage dataItemChangeMessage) throws java.lang.Exception {
        searchService.deleteSearchItem(dataItemChangeMessage.getItemType(), java.lang.String.valueOf(dataItemChangeMessage.getItemId()));
    }
}