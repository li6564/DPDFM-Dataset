### 一、用户

###### :white_check_mark: 1.手机号注册

```
POST /api/user/register/mobile
```

###### :white_check_mark: 2.QQ号注册

```
POST /api/auth/qq 
```

###### :white_check_mark: 3.修改我的基本信息

```
PUT /api/my/baseinfo
```

###### :white_check_mark: 4.修改我的密码

```
PUT /api/my/password
```

###### :white_check_mark: 5.获取我的基本信息

```
GET /api/my/baseinfo
```

###### :white_check_mark: 6.获取他人基本信息

```
GET /api/user/{userId}/baseinfo
```

###### :white_check_mark: 7.拉黑用户

```
POST /api/my/blacklist/{userId}
```

###### :white_check_mark: 8.关注用户

```
POST /api/user/{userId}/follower
```

###### :white_check_mark: 9.取消关注

```
DELETE /api/user/{userId}/follower
```

###### :white_check_mark: 10.我的粉丝

```
GET /api/my/fans
```

###### :white_check_mark: 11.他人的粉丝

```
GET /api/user/{userId}/fans
```

###### :white_check_mark: 12.我的关注 

```
GET /api/my/follower
```

###### :white_check_mark: 13.他人的关注 

```
GET /api/user/{userId}/follower
```

###### :white_check_mark: 14.获取他人统计信息

```
GET /api/user/{userId}/statistics
```

###### :white_check_mark: 15.获取我的统计信息

```
GET /api/my/statistics
```

###### 16.钉钉注册:pushpin:

```
POST /api/user/register/ding
```

###### :white_check_mark: 17.社区精英

```
GET /api/user/elite
```

###### :white_check_mark: 18.热门博主

```
GET /api/user/hotBloggers
```

###### :white_check_mark: 19.我的黑名单

```
GET /api/my/blacklist
```

###### :white_check_mark: 20.取消拉黑

```
DELETE /api/my/blacklist/{userId}
```

###### :white_check_mark: 21.邮箱注册

```
POST /api/user/register/email
```

###### :white_check_mark: 22.账号激活

```
POST /api/user/active/{activeCode}
```

###### :white_check_mark: 23.绑定手机号

```
POST /api/user/binding/mobile
```

###### :white_check_mark: 24.绑定邮箱

```
POST /api/user/binding/email
```

###### :white_check_mark: 25.活跃用户

```
POST /api/user/actives
```



### 二、认证

###### :white_check_mark: ​1.密码认证 

```
POST /api/auth/password
```

######  :white_check_mark: 2.短信验证码认证 

```
POST /api/auth/sms
```

###### :white_check_mark: 3.QQ认证

```
POST /api/auth/qq
```

###### 4.钉钉认证:pushpin:

```
POST /api/auth/ding
```



### 三、话题

###### :white_check_mark: 1.创建话题

```
POST /api/topic
```

###### :white_check_mark: 2.话题列表 

```
GET /api/topics
```

###### :white_check_mark: 3.检索话题

```
GET /api/topic/search
```

###### :white_check_mark: 4.话题详情 

```
GET /api/topic/{topicId}
```

###### :white_check_mark: 5.关注话题

```
POST /api/topic/{topicId}/follower
```

###### :white_check_mark: 6.话题关注者列表 

```
GET /api/topic/{topicId}/follower
```

###### :white_check_mark: 7.我关注的话题 

```
GET /api/my/topic/followed
```

###### :white_check_mark: 8.我创建的话题

```
GET /api/my/topic/created
```

###### :white_check_mark: 9.热搜话题

```
GET /api/topic/hotSearches
```

###### :white_check_mark: 10.热门话题

```
GET /api/topic/hots
```

###### 11.推荐话题:pushpin:

```
GET /api/topic/recommends
```

###### :white_check_mark: 12.取消关注话题

```
DELETE /api/topic/{topicId}/follower
```

###### :white_check_mark: 14.查看话题下的所有动态

```
DELETE /api/topic/{topicName}/posts
```


###### 

### 四、标签

###### :white_check_mark: 1.创建标签

```
POST /api/tag
```

###### :white_check_mark: 2.检索标签

```
GET /api/tag/search
```

###### :white_check_mark: 3.我创建的标签

```
GET /api/my/tag/created
```

###### :white_check_mark: 4.热门标签

```
GET /api/tag/hots
```



### 五、短信

###### :white_check_mark: 1.发送验证码

```
GET /api/sms/verifyCode
```



### 六、资源

###### :white_check_mark: 1.上传文件

```
POST /api/resource/file
```



### 七、项目

###### :white_check_mark: 1.发布项目

```
POST /api/project
```

###### :white_check_mark: 2.删除项目 

```
DELETE /api/project/{projectId}
```

###### :white_check_mark: 3.修改项目

```
PUT /api/project/{projectId}
```

###### :white_check_mark: 4.项目详情

```
GET /api/project/{projectId}
```

###### :white_check_mark: 5.项目列表

```
GET /api/projects
```

###### :white_check_mark: 6.我的项目列表

```
GET /api/my/projects
```

###### :white_check_mark: 7.他人项目列表

```
GET /api/user/{userId}/projects
```

###### :white_check_mark: 8.发布评论

```
POST /api/project/{projectId}/comment
```

###### :white_check_mark: 9.删除评论

```
DELETE /api/project/comment/{commentId}
```

###### :white_check_mark: 10.评论列表

```
GET /api/project/{projectId}/comments
```

###### :white_check_mark: 11.热门项目

```
GET /api/project/hots
```

###### 12.推荐项目 :pushpin:

```
GET /api/project/recommends
```

###### 13.相关推荐  :pushpin:

```
GET /api/project/{projectId}/recommends
```



### 八、博客

###### :white_check_mark: 1.发布博客

```
POST /api/blog
```

###### :white_check_mark: 2.删除博客

```
DELETE /api/blog/{blogId}
```

###### :white_check_mark: 3.修改博客

```
PUT /api/blog/{blogId}
```

###### :white_check_mark: 4.博客详情

```
GET /api/blog/{blogId}
```

###### :white_check_mark: 5.博客列表

```
GET /api/blogs
```

###### :white_check_mark: 6.我的博客列表

```
GET /api/my/blogs
```

###### :white_check_mark: 7.他人博客列表

```
GET /api/user/{userId}/blogs
```

###### :white_check_mark: 8.发布评论

```
POST /api/blog/{blogId}/comment
```

###### :white_check_mark: 9.删除评论

```
DELETE /api/blog/comment/{commentId}
```

###### :white_check_mark: 10.评论列表

```
GET /api/blog/{blogId}/comments
```

###### :white_check_mark: 11.热门博客

```
GET /api/blog/hots
```

###### 12.推荐博客 :pushpin:

```
GET /api/blog/recommends
```

###### 13.相关推荐 

```
GET /api/blog/{blogId}/recommends
```

###### 14.查看标签下的博客列表

```
GET /api/tag/{tagId}/blogs
```



### 九、问答

###### :white_check_mark: 1.发布问答

```
POST /api/question
```

###### :white_check_mark: 2.删除问答

```
DELETE /api/question/{questionId}
```

###### :white_check_mark: 3.修改问答

```
PUT /api/question/{questionId}
```

######   :white_check_mark: 4.问答详情

```
GET /api/question/{questionId}
```

###### :white_check_mark: 5.问答引用的标签

```
GET /api/question/{questionId}/tags
```

###### :white_check_mark: 6.问答列表

```
GET /api/questions
```

###### :white_check_mark: 7.我创建的问答

```
GET /api/my/questions
```

###### :white_check_mark: 8.我参与的问答

```
GET /api/my/question/participated
```

###### :white_check_mark: 9.他人问答列表

```
GET /api/user/{userId}/questions
```

###### :white_check_mark: 10.发布回复

```
POST /api/question/{questionId}/reply
```

###### :white_check_mark: 11.采纳回复

```
PUT /api/question/reply/{replyId}/adoption
```

###### :white_check_mark: 12.删除回复

```
DELETE /api/question/reply/{replyId}
```

###### :white_check_mark: 13.回复列表

```
GET /api/question/{questionId}/replies
```

###### :white_check_mark: 14.发布评论

```
POST /api/quesion/reply/{replyId}/comment
```

###### :white_check_mark: 15.删除评论

```
DELETE /api/quesion/reply/comment/{commentId}
```

###### :white_check_mark: 16.评论列表

```
GET /api/quesion/reply/{replyId}/comments
```

###### :white_check_mark: 17.热门问答

```
GET /api/question/hots
```

###### 18.推荐问答 :pushpin:

```
GET /api/question/recommends
```

###### :white_check_mark: 19.相关问答

```
GET /api/question/{questionId}/recommends
```

###### :white_check_mark: 20.查看标签下的问题列表

```
GET /api/tag/{tagId}/questions
```



### 十、动态

###### :white_check_mark: 1.发布动态

```
POST /api/post
```

###### :white_check_mark: 2.删除动态

```
DELETE /api/post/{postId}
```

###### :white_check_mark: 4.动态列表

```
GET /api/posts
```

###### :white_check_mark: 5.我的动态列表

```
GET /api/my/posts
```

###### :white_check_mark: 6.他人动态列表

```
GET /api/user/{userId}/posts
```

###### :white_check_mark: 7.发布评论

```
POST /api/post/{postId}/comment
```

###### :white_check_mark: 8.删除评论

```
DELETE /api/post/comment/{commentId}
```

###### :white_check_mark: 9.评论列表

```
GET /api/post/{postId}/comments
```

:white_check_mark: 10.我关注的用户的动态列表

```
GET /api/posts/followed
```

:white_check_mark: 11.推荐动态

```
GET /api/post/recommends
```

:white_check_mark: 12.话题下的推荐动态

```
GET /api/topic/{topicId}/post/recommends
```

###### 




### 十一、CMS

###### :white_check_mark: 1.内容详情

```
GET /api/content/{contentId}
```

###### :white_check_mark: 2.内容列表

```
GET /api/channel/{channelId}/contents
```



### 十二、邮箱

###### :white_check_mark: 1.发送验证码

```
GET /api/mail/verifyCode
```



### 十三、消息

###### :white_check_mark: 1.获取我的消息

```
GET /api/my/messages
```

###### :white_check_mark: 2.获取消息详情

```
GET /api/message/{messageId}
```

###### :white_check_mark: 3.标记消息为已读

```
PUT /api/message/{messageId}/read
```

###### :white_check_mark: 4.标记所有消息为已读

```
PUT /api/messages/read
```

###### :white_check_mark: 5.删除所有消息

```
DELETE /api/messages
```

###### :white_check_mark: 6.删除消息

```
DELETE /api/message/{messageId}
```



### 十四、类别

###### :white_check_mark: 1.获取博客分类

```
GET /api/blog/categories
```

###### :white_check_mark: 2.获取项目分类

```
GET /api/project/categories
```

###### :white_check_mark: 3.获取问题分类

```
GET /api/question/categories
```

### 十无、反馈

###### :white_check_mark: 1.意见反馈

```
POST /api/feedback
```

###### :white_check_mark: 2.投诉举报

```
POST /api/complaint
```