/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseclasses;

import java.io.File;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javafx.scene.image.Image;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abanoub samy
 */

@XmlRootElement(name="message")
public class Message implements Serializable{
    
    private Integer messageId;
    private String messageText;
    private Timestamp messageTime;
    private Integer senderId;
    private Integer chatId;
    private String messageColor;
    private Integer fontSize;
    private String fontType;
    private int pos;

    /**
     *
     */
    public Message() {
        //esraaa
        this.fontSize=20;
        this.fontType="Serif";
        this.messageColor="000000";
    }

    /**
     *
     * @param messageId
     * @param messageText
     * @param messageTime
     * @param senderId
     * @param chatId
     * @param messageColor
     * @param fontSize
     * @param fontType
     */
    public Message(Integer messageId,String messageText,Timestamp messageTime, 
            Integer senderId,Integer chatId,String messageColor, Integer fontSize, String fontType) {
        this.messageId = messageId;
        this.messageText = messageText;
        this.messageTime = messageTime;
       
        this.senderId = senderId;
        this.chatId = chatId;
        this.messageColor = messageColor;
        this.fontSize = fontSize;
        this.fontType = fontType;
    }

    /**
     *
     * @return
     */
    public Integer getMessageId() {
        return messageId;
    }

    /**
     *
     * @param messageId
     */
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    /**
     *
     * @return
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     *
     * @param messageText
     */
    @XmlElement
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    /**
     *
     * @return
     */
    public Timestamp getMessageTime() {
        return messageTime;
    }

    /**
     *
     * @param messageTime
     */
    public void setMessageTime(Timestamp messageTime) {
        this.messageTime = messageTime;
    }

    /**
     *
     * @return
     */
    public Integer getSenderId() {
        return senderId;
    }

    /**
     *
     * @param senderId
     */
    @XmlElement
    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    /**
     *
     * @return
     */
    public Integer getChatId() {
        return chatId;
    }

    /**
     *
     * @param chatId
     */
    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }
    
    /**
     *
     * @return
     */
    public String getMessageColor() {
        return messageColor;
    }

    /**
     *
     * @param messageColor
     */
    @XmlElement
    public void setMessageColor(String messageColor) {
        this.messageColor = messageColor;
    }

    /**
     *
     * @return
     */
    public Integer getFontSize() {
        return fontSize;
    }

    /**
     *
     * @param fontSize
     */
    @XmlElement
    public void setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
    }

    /**
     *
     * @return
     */
    public String getFontType() {
        return fontType;
    }

    /**
     *
     * @param fontType
     */
    @XmlElement
    public void setFontType(String fontType) {
        this.fontType = fontType;
    } 

    /**
     * @return the pos
     */
    public int getPos() {
        return pos;
    }

    /**
     * @param pos the pos to set
     */
    
    @XmlElement
    public void setPos(int pos) {
        this.pos = pos;
    }
}
