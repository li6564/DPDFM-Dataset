### 用户接口
##### 1.用户注册
```
 请求路径：/api/user/register
    请求方式：POST
    请求格式：JSON
    请求参数：
        mobile：手机号
        password：密码
        verifyCode：验证码
    返回结果：default
```
   
##### 2.用户本地认证
```
    请求路径：/api/auth/local
    请求方式：POST
    请求格式：JSON
    请求参数：
        mobile：手机号
        password：密码
    返回结果：
        userId：用户ID
        accessToken：授权令牌
```
##### 3.用户短信验证码认证
```
    请求路径：/api/auth/sms
    请求方式：POST
    请求格式：JSON
    请求参数：
        mobile：手机号
        verifyCode：验证码
    返回结果：
        userId：用户ID
        accessToken：授权令牌
```

##### 4.修改密码
```
    请求路径：/api/user/{userId}/password
    请求方式：PUT
    请求格式：JSON
    请求参数：
        oldPassword：旧密码
        newPassword: 新密码
    返回结果：
        default 
```    

##### 5.用户通过手机号修改密码(-)
```
    请求路径：/api/user/reset-pwd-by-phone
    请求方式：PUT
    请求格式：JSON
    请求参数：
        mobile：手机号
        verifyCode：验证码
        newPassword: 新密码
    返回结果：
        default
```
        
##### 6.个人信息
```
    请求路径：/api/user/{userId}
    请求方式：GET
    请求格式：JSON
    请求参数：
    返回结果：
        userAvatar
        nickName
        gender
        signature
        mobile
        qqNumber
        wxNumber

```         

##### 6.他人信息
```
    请求路径：/api/user/{userId}
    请求方式：GET
    请求格式：JSON
    请求参数：
    返回结果：
        userAvatar
        nickName
        gender
        signature
        mobile
        qqNumber
        wxNumber

```               
##### 7.上传头像(-)
```
 请求路径：/api/file/upload
    请求方式：POST
    请求格式：file
    请求参数：
        file
        type
    返回结果：
        default
   
```   
                 
##### 8.修改基本信息
```
    请求路径：/api/userinfo/base
    请求方式：PUT
    请求格式：JSON
    请求参数：
        userAvatar
        nickName
        gender
        signature
        mobile
        qqNumber
        wxNumber
    返回结果：
        userAvatar
        nickName
        gender
        signature
        mobile
        qqNumber
        wxNumber

```   

    