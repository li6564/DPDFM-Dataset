package cn.meteor.beyondclouds.config.properties;
import lombok.Data;
/**
 *
 * @author 段启岩
 */
@lombok.Data
public class SmsProperties {
    private java.lang.String domain;

    private java.lang.String version;

    private java.lang.String action;

    private java.lang.String regionId;

    private java.lang.String signName;

    private java.util.Map<java.lang.String, java.lang.String> templateCodeMap;
}