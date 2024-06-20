###动态接口
#### 1.发布动态
        请求路径：/api/post
        请求方式：POST
        请求格式：JSON
        请求参数：
            content：发布内容
            address：地址
            browseRight：浏览权限(公/私)
        返回结果：success
        
#### 2.关注用户(-)
        请求路径：/api/post/follow
        请求方式：POST
        请求格式：JSON
        请求参数：
            token：关注者token
            followedId：被关注者ID
        返回结果：success
        
#### 3.评论
        请求路径：/api/post/{postId}/comment
        请求方式：POST
        请求格式：JSON
        请求参数：
            postId：动态ID
            parentId：上层ID
            content：内容
        返回结果：success
      
#### 4.删除评论
        请求路径：/api/post/comment/{commentId}
        请求方式：DELETE
        请求参数：
        返回结果：success
            
#### 5.动态展示
        请求路径：/api/posts
        请求方式：GET     
        请求格式：JSON
        请求参数：
           
        返回结果：
            postId：动态ID
            userID：发布者ID
            userNick：发布者昵称
            userAvatar：发布者头像
            content：动态内容
            pictures：图片

#### 6.个人动态展示
        请求路径：/api/user/{userId}/posts
        请求方式：GET
        请求格式：JSON
        请求参数：
        返回结果：
            postId：动态ID
            userNick：发布者昵称
            userAvatar：发布者头像
            content：动态内容
            pictures：图片
          
#### 7.删除动态
        请求路径：/api/post/{postId}
        请求方式：DELETE
        请求格式：JSON
        请求参数：
        返回结果：success


#### 8.话题引用(-)
        请求路径：/api/post/reference
        请求方式：POST
        请求格式：JSON
        请求参数：
            token：token
            topicId：引用话题ID
            referencerType：引用类型
        返回结果：success 
        
#### 9.评论展示
         请求路径：/api/post/{postId}/comments
         请求方式：GET
         请求格式：JSON
         请求参数：
         返回结果：
            commentId：评论ID
            commentContent：评论内容
            commentDepth：评论深度
            commenterID：评论者ID
            commenterAvatar：评论者头像
            commenterNick：评论者昵称                                                          