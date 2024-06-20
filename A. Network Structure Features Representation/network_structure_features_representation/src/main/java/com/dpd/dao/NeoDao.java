package com.dpd.dao;

import com.dpd.entity.ClassEntity;
import com.dpd.type.RelType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

import java.util.HashMap;
import java.util.Map;

public interface NeoDao {
    public static Map<String,Node> node_map = new HashMap<>();

    /**
     *创建关系，关系由源节点指向目标节点
     * @param source
     * @param target
     * @param relType
     * @return
     */
    Relationship createRelation(Node source, Node target, RelType relType);

    /**
     * 创建类节点
     * @param tx    neo4j 事务实例
     * @param classEntity  类实体
     * @return   创建的节点
     */
    Node createClassNode(Transaction tx, ClassEntity classEntity);
}
