### 博客接口
#### 1.发布博客
        请求路径：/api/blog
        请求方式：POST
        请求格式：JSON
        请求参数：
            blogTitle：标题
            blogContent：内容
            blogAbstract：摘要(自动或手动)
            originLink：网络链接
            tagIds：[博客引用的标签]
            attribute：属性(是否置顶)
            type：文章类型
            viewPrivileges：浏览权限
            allowComment：评论权限
            allowForward：转载权限
            topicIds: []
        返回结果：success
        
#### 2.删除博客
        请求路径：/api/blog/{blogId}
        请求方式：DELETE
        返回结果：success
        请求示例：DELETE /api/blog/fe66853d28b36a9e0ec17cf9221bd5da

#### 3.发布评论
        请求路径：/api/blog/{blogId}/comment
        请求方式：POST
        请求格式：JSON
        请求参数：
            parentId：上层ID
            content：内容
        返回结果：success

#### 4.删除评论
        请求路径：/api/blog/{blogId}/comment/{commentId}
        请求方式：DELETE
        请求格式：JSON
        请求示例：DELETE /api/blog/comment/fe66853d28b36a9e0ec17cf9221bd5da
        返回结果：success        
#### 5.评论排序(-)
        
#### 6.话题引用(-)
        请求路径：/api/blog/reference
        请求方式：POST
        请求格式：JSON
        请求参数：
            token：token
            topicId：引用话题ID
            referencerType：引用类型
        返回结果：success      
#### 8.博客列表
        请求路径：/api/blogs
        请求方式：GET
        请求格式：分页参数，排序参数
        请求参数：
            pageNo：页数
        返回结果：
            bloglist：博客内容列表    
            {
                code: 0,
                msg: '操作成功',
                data: {
                    total: 20,
                    dataList: [
                    ...,
                    ...
                    ]
                }
            }

#### 9.热门博客
        请求路径：/api/blog/hots
        请求方式：GET
        请求格式：分页参数，排序参数
        请求参数：
            pageNo：页数
        返回结果：
            bloglist：博客内容列表    
            {
                code: 0,
                msg: '操作成功',
                data: {
                    total: 20,
                    dataList: [
                    ...,
                    ...
                    ]
                }
            }

#### 10.博客详情(-)
        请求路径：/api/blog/{blogId}
        请求方式：GET     
        请求格式：JSON
        返回结果：
            blogId：博客ID
            userID：发布者ID
            userNick：发布者昵称
            userAvatar：发布者头像
            blogTitle：标题
            blogContent：内容
            blogAbstract：摘要(自动或手动)
            originLink：网络链接
            tag：标签
        

#### 11.个人博客展示
         请求路径：/api/user/{userId}/blogs
         请求方式：GET
         请求格式：JSON
         返回结果：
            blogId：动态ID
            userNick：发布者昵称
            userAvatar：发布者头像
            blogTitle：标题
            blogContent：内容
            blogAbstract：摘要(自动或手动)
            originLink：网络链接
            tag：标签

                                  
#### 12.评论展示
         请求路径：/api/blog/{blogId}/comments
         请求方式：GET
         请求格式：JSON
         返回结果：
            commentId：评论ID
            commentContent：评论内容
            commentDepth：评论深度
            commenterID：评论者ID
            commenterAvatar：评论者头像
            commenterNick：评论者昵称                           
                                              