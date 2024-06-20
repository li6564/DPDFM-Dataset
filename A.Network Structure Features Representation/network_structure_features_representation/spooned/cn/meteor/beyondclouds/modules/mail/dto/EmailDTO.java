package cn.meteor.beyondclouds.modules.mail.dto;
import lombok.Data;
/**
 *
 * @author meteor
 */
@lombok.Data
public class EmailDTO implements java.io.Serializable {
    public EmailDTO() {
    }

    /**
     *
     * @param from
     * @param to
     * @param subject
     * @param text
     */
    public EmailDTO(java.lang.String from, java.lang.String[] to, java.lang.String subject, java.lang.String text) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    /**
     *
     * @param from
     * @param to
     * @param subject
     * @param text
     */
    public EmailDTO(java.lang.String from, java.lang.String to, java.lang.String subject, java.lang.String text) {
        this.from = from;
        this.to = new java.lang.String[]{ to };
        this.subject = subject;
        this.text = text;
    }

    /**
     * 发送者
     */
    private java.lang.String from;

    /**
     * 接受者
     */
    private java.lang.String[] to;

    /**
     * 邮件主题
     */
    private java.lang.String subject;

    /**
     * 邮件内容
     */
    private java.lang.String text;
}