package cn.meteor.beyondclouds.modules.search.entity.extra;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
@lombok.Data
public class UserExtra {
    private static final long serialVersionUID = 1L;

    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Long)
    private java.lang.Integer followedNum;

    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Long)
    private java.lang.Integer fansNum;

    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Long)
    private java.lang.Integer blogNum;

    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Long)
    private java.lang.Integer projectNum;

    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Long)
    private java.lang.Integer postNum;

    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Long)
    private java.lang.Integer questionNum;

    @org.springframework.data.elasticsearch.annotations.Field(index = false, type = org.springframework.data.elasticsearch.annotations.FieldType.Long)
    private java.lang.Integer visitedNum;
}