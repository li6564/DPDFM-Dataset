package com.dpd.parse;

import com.dpd.entity.ClassEntity;
import com.dpd.dao.NeoDao;
import com.dpd.dao.impl.NeoDaoImpl;
import com.dpd.utils.NeoUtil;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.Map;

/**
 *  构建软件网络结构
 */
public class NeoParser {
    private static GraphDatabaseService service = NeoUtil.getService();
    private static NeoDao dao = new NeoDaoImpl();

    public void createGraph(SpoonParse spoonParser) {
        Map<String, Node> nodeMap = getNodeMap();
        try (Transaction tx = service.beginTx()) {
            //在数据库中创建类节点以及关系
            for (ClassEntity entity : spoonParser.getClassEntities()) {
                dao.createClassNode(tx, entity);
            }
            spoonParser.getClassToClassRelation().forEach(classToClassRelation -> {
                ClassEntity source_entity = classToClassRelation.getSource();
                ClassEntity target_entity = classToClassRelation.getTarget();
                Node source_node = nodeMap.get(source_entity.getName());
                Node target_node = nodeMap.get(target_entity.getName());
                dao.createRelation(source_node, target_node, classToClassRelation.getRelation_type());

            });
            tx.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Map<String,Node> getNodeMap(){
        return NeoDao.node_map;
    }
}
