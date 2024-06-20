package cn.meteor.beyondclouds;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
/**
 *
 * @author meteor
 */
@org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories(basePackages = "cn.meteor.beyondclouds.modules.search")
@org.springframework.boot.autoconfigure.SpringBootApplication
public class BeyondCloudsApplication {
    public static void main(java.lang.String[] args) {
        org.springframework.boot.SpringApplication.run(cn.meteor.beyondclouds.BeyondCloudsApplication.class, args);
    }
}