package com.jvwac.jgvwa.controller.xxe.auxiliary;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Input {

    @XmlElement(name = "content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Input{" + "content='" + content + '\'' + '}';
    }
}
