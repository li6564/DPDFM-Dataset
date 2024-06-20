package cn.meteor.beyondclouds.common.generator;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
/**
 *
 * @author meteor
 */
public class CodeGenerator {
    static class Module {
        private java.lang.String moduleName;

        private java.lang.String[] include;

        public Module(java.lang.String moduleName, java.lang.String[] include) {
            this.moduleName = moduleName;
            this.include = include;
        }

        public static cn.meteor.beyondclouds.common.generator.CodeGenerator.Module of(java.lang.String moduleName, java.lang.String... include) {
            return new cn.meteor.beyondclouds.common.generator.CodeGenerator.Module(moduleName, include);
        }
    }

    public static void main(java.lang.String[] args) {
        cn.meteor.beyondclouds.common.generator.CodeGenerator.Module[] modules = new cn.meteor.beyondclouds.common.generator.CodeGenerator.Module[]{ // 博客模块
        cn.meteor.beyondclouds.common.generator.CodeGenerator.Module.of("blog", "blog", "blog_category", "blog_comment", "blog_ext", "blog_tag"), // 资讯模块
        cn.meteor.beyondclouds.common.generator.CodeGenerator.Module.of("news", "news"), // 动态模块
        cn.meteor.beyondclouds.common.generator.CodeGenerator.Module.of("post", "post", "post_comment"), // 项目模块
        cn.meteor.beyondclouds.common.generator.CodeGenerator.Module.of("project", "project", "project_category", "project_comment", "project_ext"), // 问答模块
        cn.meteor.beyondclouds.common.generator.CodeGenerator.Module.of("question", "question", "question_category", "question_ext", "question_reply", "question_reply_comment", "question_tag"), // 标签模块
        cn.meteor.beyondclouds.common.generator.CodeGenerator.Module.of("tag", "tag"), // 话题模块
        cn.meteor.beyondclouds.common.generator.CodeGenerator.Module.of("topic", "topic", "topic_follow", "topic_reference"), // 上传资源模块
        cn.meteor.beyondclouds.common.generator.CodeGenerator.Module.of("resource", "upload_resource"), // 用户模块
        cn.meteor.beyondclouds.common.generator.CodeGenerator.Module.of("user", "user", "user_auth_app", "user_auth_local", "user_follow") };
        // for (Module module :
        // modules) {
        // execute(module.moduleName, module.include);
        // }
        // execute("blog", "blog_praise");
        // execute("post", "post_praise");
        // execute("project", "project_praise");
        // execute("question", "question_praise");
        // execute("app", "app_version");
    }

    private static void execute(java.lang.String moduleName, java.lang.String... include) {
        // 代码生成器
        com.baomidou.mybatisplus.generator.AutoGenerator mpg = new com.baomidou.mybatisplus.generator.AutoGenerator();
        // 全局配置
        com.baomidou.mybatisplus.generator.config.GlobalConfig gc = new com.baomidou.mybatisplus.generator.config.GlobalConfig();
        gc.setIdType(IdType.ASSIGN_UUID);
        java.lang.String projectPath = "/Users/meteor/code/java/beyond-clouds";
        gc.setOutputDir(projectPath + "/src/gen/java");
        gc.setAuthor("段启岩");
        gc.setOpen(false);
        gc.setSwagger2(true);// 实体属性 Swagger2 注解

        mpg.setGlobalConfig(gc);
        // 数据源配置
        com.baomidou.mybatisplus.generator.config.DataSourceConfig dsc = new com.baomidou.mybatisplus.generator.config.DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/beyond_clouds?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("100Centa30821%mysql");
        mpg.setDataSource(dsc);
        com.baomidou.mybatisplus.generator.config.StrategyConfig strategy = new com.baomidou.mybatisplus.generator.config.StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setInclude(include);
        mpg.setStrategy(strategy);
        // 包配置
        com.baomidou.mybatisplus.generator.config.PackageConfig pc = new com.baomidou.mybatisplus.generator.config.PackageConfig();
        pc.setParent("cn.meteor.beyondclouds.modules");
        pc.setModuleName(moduleName);
        mpg.setPackageInfo(pc);
        mpg.execute();
    }
}