package cn.meteor.beyondclouds.modules.search.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author meteor
 */
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api/search")
@io.swagger.annotations.Api(tags = "搜索API")
public class SearchApi {
    private cn.meteor.beyondclouds.modules.search.service.ISearchService searchService;

    @org.springframework.beans.factory.annotation.Autowired
    public SearchApi(cn.meteor.beyondclouds.modules.search.service.ISearchService searchService) {
        this.searchService = searchService;
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @org.springframework.web.bind.annotation.GetMapping("")
    @io.swagger.annotations.ApiOperation("搜索")
    public cn.meteor.beyondclouds.core.api.Response<?> search(@javax.validation.Valid
    cn.meteor.beyondclouds.common.form.PageForm pageForm, java.lang.String keywords, @org.springframework.web.bind.annotation.RequestParam(value = "type", required = false)
    java.lang.String itemType, org.springframework.validation.BindingResult bindingResult, @cn.meteor.beyondclouds.core.flow.CollectAccessInfo(transmitType = cn.meteor.beyondclouds.core.flow.TransmitType.PARAM, type = cn.meteor.beyondclouds.core.flow.ParamType.SEARCH_KEYWORDS_TOPIC, paramName = "keywords")
    cn.meteor.beyondclouds.core.flow.AccessInfo accessInfo) {
        if (bindingResult.hasErrors()) {
            return cn.meteor.beyondclouds.core.api.Response.fieldError(bindingResult.getFieldError());
        }
        boolean updateDegree = false;
        if (null != accessInfo) {
            updateDegree = accessInfo.getFieldVisitCount() < 2;
        }
        // spring的分页是从0开始的，对输入的页码减1后可以统一分页规则
        pageForm.setPage(pageForm.getPage() - 1);
        org.springframework.data.domain.Page<cn.meteor.beyondclouds.modules.search.entity.SearchItem> searchItemPage = searchService.searchPage(pageForm.getPage(), pageForm.getSize(), itemType, keywords, updateDegree);
        cn.meteor.beyondclouds.common.dto.PageDTO<cn.meteor.beyondclouds.modules.search.entity.SearchItem> searchItemPageDTO = new cn.meteor.beyondclouds.common.dto.PageDTO<>(searchItemPage);
        return cn.meteor.beyondclouds.core.api.Response.success(searchItemPageDTO);
    }

    @cn.meteor.beyondclouds.core.annotation.Anonymous
    @org.springframework.web.bind.annotation.GetMapping("/makeIndex")
    @io.swagger.annotations.ApiOperation("对建立elasticsearch索引")
    public cn.meteor.beyondclouds.core.api.Response<?> buildIndex(java.lang.String password) {
        if (org.springframework.util.StringUtils.isEmpty(password) || (!password.equals("beyond#elastic"))) {
            return cn.meteor.beyondclouds.core.api.Response.error(cn.meteor.beyondclouds.common.enums.ErrorCode.OPERATION_FAILED.code(), "密码错误");
        }
        searchService.buildIndex();
        return cn.meteor.beyondclouds.core.api.Response.success();
    }
}