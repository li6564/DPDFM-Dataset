### 问答接口
##### 1.发布问题
```
    请求路径：/api/question
    请求方式：POST
    请求格式：JSON
    请求参数：
        questionTitle：问题标题
        questionDetail：问题详细信息
        tagName：标签(最多五个)
        tagType：标签类型
        questionClassify：问题分类
        questionStatus：问题状态（0：草稿，1：发布）
    返回结果：default
```

##### 2.回复问题
```
    请求路径：/api/question/{questionId}/reply
    请求方式：POST
    请求格式：JSON
    请求参数：
        questionId：问题ID
        content：回复内容
        replyStatus：回复状态(0：未采纳，1：采纳)
        parentId：父回复ID
    返回结果：default
```

##### 3.获取用户的所有提问
```
    请求路径：/api/user/{userId}/questions
    请求方式：GET
    请求格式：JSON
    请求参数：
    返回结果：
        List<question>{
            questionTitle：问题标题
            content：一部分内容
            tag：问题标签
            updateTime：问题更新时间
        }
```

##### 4.获取用户的所有回答
```
    请求路径：/api/user/{userId}/replies
    请求方式：GET
    请求格式：JSON
    请求参数：
    返回结果：
        List：上述对象的集合
        List{
            question{
                questionTitle：问题标题
            }
            reply{
                content：该用户的回复内容
                updateTime：回复更新时间  
            }
        }
```

##### 5.问题详情展示
```
    请求路径：/api/question/{questionId}
    请求方式：GET
    请求格式：JSON
    请求参数：
    返回结果：
        question{
            questionTitle：问题标题
            content：问题内容
            tag：标签
        }
        List<reply>{
            User{
                userAvatar：用户头像
                nickName：用户昵称
            }
            content：回复内容
            updateTime：回复更新时间
        }
```

##### 6.回复列表
```
    请求路径：/api/question/{questionId}/replies
    请求方式：GET
    请求格式：JSON
    请求参数：
        questionId：问题ID
    返回结果：
        List<reply>{
            User{
                userAvatar：用户头像
                nickName：用户昵称
            }
            content：回复内容
            updateTime：回复更新时间
        }
```

##### 7.根据分类得到提问
```
    请求路径：/api/questions
    请求方式：GET
    请求格式：JSON
    请求参数：
        classify：分类名称
    返回结果：
        List{
            User{
                nickName：用户昵称
            }
            Question{
                questionTitle：问题标题
                content：问题内容
                updateTime：问题更新时间
                tag：问题标签
            }
        }
```

##### 8.根据问题的标签进行相关推荐
```
    请求路径：/api/question/recommonds
    请求方式：GET
    请求格式：JSON
    请求参数：
        questionId：问题ID
    返回结果：
        List<Question>{
            User{
                nickname：用户昵称
            }
            questionTitle：问题标题
        }
```

##### 9.用户修改回复
```
    请求路径：/api/question/reply/{replyId}
    请求方式：PUT
    请求格式：JSON
    请求参数：
        content: 修改后的回复内容
    返回结果：default  
```

##### 10.用户删除提问
```
    请求路径：/api/question/{questionId}
    请求方式：DELETE
    请求格式：JSON
    请求参数：
        questionId：问题ID
    返回结果：default
```



