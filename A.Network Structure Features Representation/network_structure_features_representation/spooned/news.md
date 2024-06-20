### 资讯接口
##### 1.发布资讯
```
    请求路径：/api/news
    请求方式：POST
    请求格式：JSON
    请求参数：
        newsTitle：资讯标题
        content：资讯内容
        newsCover：资讯封面
    返回结果：default
```

##### 2.修改资讯
```
    请求路径：/api/news/{newsId}
    请求方式：PUT
    请求格式：JSON
    请求参数：
        newsTitle：资讯标题
        content：资讯内容
        newsCover：资讯封面
    返回结果：default
```

##### 3.按分类展示资讯
```
    请求路径：/api/news
    请求方式：GET
    请求格式：JSON
    请求参数：
        classify：资讯分类
    请求结果：
        List{
            newsTitle：资讯标题
            content：一部分内容
            newsCover：资讯封面
        }
```

##### 4.删除资讯
```
    请求路径：/api/news/{newsId}
    请求方式：DELETE
    请求格式：JSON
    请求参数：
    返回结果：default
```