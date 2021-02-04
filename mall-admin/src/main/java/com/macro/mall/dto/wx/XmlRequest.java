package com.macro.mall.dto.wx;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class XmlRequest {
    @XmlElement(name="ToUserName")
    private String toUserName;
    @XmlElement(name="Encrypt")
    private String encrypt;
}
