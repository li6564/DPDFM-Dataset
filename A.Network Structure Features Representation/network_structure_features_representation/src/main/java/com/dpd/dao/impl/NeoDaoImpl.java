package com.dpd.dao.impl;

import com.dpd.entity.ClassEntity;
import com.dpd.dao.NeoDao;
import com.dpd.type.RelType;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;

/**
 *  创建网络结构中的节点
 */
public class NeoDaoImpl implements NeoDao {
    private Integer node_id = 0;
    private Integer nodeid = 100000;
    @Override
    public Relationship createRelation(Node source, Node target, RelType relType) {
        return  source.createRelationshipTo(target,relType);
    }
    @Override
    public Node createClassNode(Transaction tx, ClassEntity classEntity) {
        Node node = tx.createNode();
        String type = "";
        Integer classType = classEntity.getClassType();
        switch (classType){
            case 0:
                type += "ABSTRACT";
                break;
            case 1:
                type += "CLASS";
                break;
            case 2:
                type += "INTERFACE";
                break;
            case 3:
                type += "ENUM";
                break;
            case 4:
                type += "ANNOTAION";
                break;
        }
        node.addLabel(Label.label(type));
        node.setProperty("classType",classEntity.getClassType().toString());
        node.setProperty("modifiers",classEntity.getModifiers());
        node.setProperty("name",classEntity.getName());
        node.setProperty("isTopLevel",classEntity.getIsTopLevel());
        node.setProperty("isShadow",classEntity.getIsShadow());
        node.setProperty("isAnonymous",classEntity.getIsAnonymous());
        node.setProperty("isGenerics",classEntity.getIsGenerics());
        node.setProperty("isLocalType",classEntity.getIsLocalType());
        node.setProperty("node_id",node_id);
        node_id++;
        node_map.put(classEntity.getName(),node);
        return node;
    }
}
