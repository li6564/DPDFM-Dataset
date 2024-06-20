package cn.meteor.beyondclouds.modules.resource.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/**
 * 文件相关Api
 *
 * @author 段启岩
 */
@io.swagger.annotations.Api(tags = "资源Api")
@org.springframework.web.bind.annotation.RestController
@org.springframework.web.bind.annotation.RequestMapping("/api/resource")
public class FileUploadApi {
    private cn.meteor.beyondclouds.modules.resource.service.IFileUploadService fileUploadService;

    @org.springframework.beans.factory.annotation.Autowired
    public void setFileUploadService(cn.meteor.beyondclouds.modules.resource.service.IFileUploadService fileUploadService) {
        this.fileUploadService = fileUploadService;
    }

    @io.swagger.annotations.ApiOperation("文件上传")
    @org.springframework.web.bind.annotation.PostMapping("/file")
    public cn.meteor.beyondclouds.core.api.Response upload(@org.springframework.web.bind.annotation.RequestParam("file")
    org.springframework.web.multipart.MultipartFile file, @org.springframework.web.bind.annotation.RequestParam("type")
    java.lang.Integer type, @cn.meteor.beyondclouds.core.annotation.CurrentSubject
    cn.meteor.beyondclouds.core.authentication.Subject subject) {
        // 1.获取上传类型
        cn.meteor.beyondclouds.modules.resource.enums.UploadType uploadType = cn.meteor.beyondclouds.modules.resource.enums.UploadType.valueOf(type);
        if (null == uploadType) {
            return cn.meteor.beyondclouds.core.api.Response.error(cn.meteor.beyondclouds.modules.resource.enums.FileUploadErrorCode.UPLOAD_TYPE_ERROR);
        }
        // 2.获取文件后缀
        java.lang.String originalFilename = file.getOriginalFilename();
        java.lang.String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 3.上传文件
        try {
            java.lang.String downloadUrl = fileUploadService.upload(file.getInputStream(), file.getSize(), uploadType, suffix, ((java.lang.String) (subject.getId())));
            return cn.meteor.beyondclouds.core.api.Response.success(downloadUrl);
        } catch (cn.meteor.beyondclouds.modules.resource.exception.FileUploadServiceException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(e);
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return cn.meteor.beyondclouds.core.api.Response.error(cn.meteor.beyondclouds.modules.resource.enums.FileUploadErrorCode.UPLOAD_FAILURE);
        }
    }
}