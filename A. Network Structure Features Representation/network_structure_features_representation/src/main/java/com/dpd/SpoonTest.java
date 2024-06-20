package com.dpd;

import com.dpd.parse.NeoParser;
import com.dpd.parse.SpoonParse;
import com.dpd.utils.NeoUtil;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.internal.unsafe.IllegalAccessLoggerSuppressor;
import org.testng.annotations.Test;

/**
 * 启动类，生成网络结构特征表示
 */
public class SpoonTest {
    @Test
    public void Test(){

        IllegalAccessLoggerSuppressor.suppress();
        SpoonParse spoonParser = new SpoonParse();

        System.out.println("正在解析...");
        long start = System.currentTimeMillis();
        try {
            //spoonParser.parse(spoonParser.getFluentModel("D:\\dpdData\\6 - JHotDraw v5.1\\src\\CH\\ifa\\draw", 7));
            //spoonParser.parse(spoonParser.getFluentModel("D:\\dpdData\\3 - JRefactory v2.6.24\\src", 7));
            spoonParser.parse(spoonParser.getFluentModel("", 7));
            System.out.println("解析完成，用时 " + (System.currentTimeMillis() - start) + " ms.");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("开始构建图数据库...");
        start = System.currentTimeMillis();
        GraphDatabaseService service = NeoUtil.getService();
        NeoParser neoParser = new NeoParser();
        neoParser.createGraph(spoonParser);
        System.out.println("图创建完成，用时 " + (System.currentTimeMillis() - start) + " ms.");
    }
}
