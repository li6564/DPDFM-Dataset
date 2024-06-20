package com.dpd.relation;

import com.dpd.entity.ClassEntity;
import com.dpd.type.RelType;

import java.util.Objects;

public class ClassToClassRelation {
    private ClassEntity source;             //源类节点
    private ClassEntity target;             //目标类节点
    private RelType relation_type;      //节点关系

    public ClassToClassRelation(ClassEntity source, ClassEntity target, RelType relation_type) {
        this.source = source;
        this.target = target;
        this.relation_type = relation_type;
    }

    public ClassEntity getSource() {
        return source;
    }

    public void setSource(ClassEntity source) {
        this.source = source;
    }

    public ClassEntity getTarget() {
        return target;
    }

    public void setTarget(ClassEntity target) {
        this.target = target;
    }

    public RelType getRelation_type() {
        return relation_type;
    }

    public void setRelation_type(RelType relation_type) {
        this.relation_type = relation_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassToClassRelation relation = (ClassToClassRelation) o;
        return source.equals(relation.source) &&
                target.equals(relation.target) &&
                relation_type == relation.relation_type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, target, relation_type);
    }
}
