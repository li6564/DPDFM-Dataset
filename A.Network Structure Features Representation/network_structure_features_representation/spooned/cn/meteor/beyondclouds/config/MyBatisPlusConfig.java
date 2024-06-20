package cn.meteor.beyondclouds.config;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 *
 * @author 段启岩
 */
@org.mybatis.spring.annotation.MapperScan("cn.meteor.beyondclouds.modules.*.mapper")
@org.springframework.transaction.annotation.EnableTransactionManagement
@org.springframework.context.annotation.Configuration
public class MyBatisPlusConfig {
    /**
     * 分页插件
     *
     * @return  */
    @org.springframework.context.annotation.Bean
    public com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor paginationInterceptor() {
        com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor paginationInterceptor = new com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor();
        paginationInterceptor.setOverflow(false);
        paginationInterceptor.setLimit(100);
        paginationInterceptor.setCountSqlParser(new com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
}