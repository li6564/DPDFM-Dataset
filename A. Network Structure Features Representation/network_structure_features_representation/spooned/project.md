### 项目接口
##### 1.发布项目
```
    请求路径：/api/project
    请求方式：POST
    请求格式：JSON
    请求参数：
        projectName：项目名称
        projectLink：项目链接
        projectHome：项目官网链接
        projectDoc: 项目文档链接
        projectType: 项目类型
        lincense: 授权协议
        devLong: 开发语言
        runtimePlatform: 操作系统
        author: 作者
        projectDescription: 项目介绍
        projectCover: 项目封面
    返回结果：default
    
```
    
##### 2.项目详情
```
    请求路径：/api/project/{projectId}
    请求方式：GET
    请求格式：JSON
    请求参数：
    返回结果：
        projectName：项目名称
        projectId: 项目id
        projectDetail: 项目具体信息
        projectType: 项目类型
        lincense: 授权协议
        devLong: 开发语言
        runtimePlatform: 操作系统
        author: 作者
        projectDescription: 项目介绍
        projectCover: 项目封面 
        commentId: 评论id
        comment: 评论内容
    
```    
           
##### 3.修改项目资料
```
    请求路径：/api/project/{projectId}
    请求方式：PUT
    请求格式：JSON
    请求参数：
        projectName：项目名称
        projectLink：项目链接
        projectHome：项目官网链接
        projectDoc: 项目文档链接
        projectType: 项目类型
        lincense: 授权协议
        devLong: 开发语言
        runtimePlatform: 操作系统
        author: 作者
        projectDescription: 项目介绍
        projectCover: 项目封面
    返回结果：default
   
```    
##### 4.个人项目列表
```
    请求路径：/api/user/{userId}/projects
    请求方式：GET
    请求格式：JSON
    返回结果：List<project>
```    
##### 5.项目列表
```
    请求路径：/api/projects
    请求方式：GET
    请求格式：JSON
    请求参数：userId
    返回结果：List<project>
```    

##### 6.提交评论    
```
    请求路径：/api/project/{projectId}/comment
    请求方式：POST
    请求格式：JSON
    请求参数：
        content: 评论内容
        commentId: 上一级评论的id
    返回结果：default

```
##### 7.删除评论    
```
    请求路径：/api/project/comment/{commentId}
    请求方式：DELETE
    请求格式：JSON
    请求参数：
    返回结果：default    
    
```        
