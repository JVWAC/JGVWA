package com.jvwac.jgvwa.controller.xxe;

import com.jvwac.jgvwa.controller.xxe.auxiliary.Input;
import io.swagger.annotations.ApiOperation;
import org.jdom2.input.SAXBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

import com.thoughtworks.xstream.XStream;
import org.dom4j.io.SAXReader;

@RequestMapping(value = "/xxe")
@RestController
public class XXEController {

    @ApiOperation(value = "org.xml.sax.XMLReader#parse")
    @RequestMapping(value = "/v1", method = RequestMethod.POST)
    public String V1(@RequestParam("content") String content) {
        try {
            XMLReader reader = XMLReaderFactory.createXMLReader();
//            reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);  //修复：禁用外部实体
            reader.parse(new InputSource(new StringReader(content)));
            return "xxe";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @ApiOperation(value = "javax.xml.parsers.DocumentBuilder#parse")
    @RequestMapping(value = "/v2", method = RequestMethod.POST)
    public String V2(@RequestParam("content") String content) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(content)));
            return "xxe";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @ApiOperation(value = "javax.xml.parsers.SAXParser#parse")
    @RequestMapping(value = "/v3", method = RequestMethod.POST)
    public String V3(@RequestParam("content") String content) {
        try {
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(new InputSource(new StringReader(content)), new DefaultHandler());
            return "xxe";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @ApiOperation(value = "org.dom4j.io.SAXReader#read", notes = "https://mvnrepository.com/artifact/org.dom4j/dom4j/2.1.3")
    @RequestMapping(value = "/v4", method = RequestMethod.POST)
    public String V4(@RequestParam("content") String content) {
        try {
            SAXReader sax = new SAXReader();
            // 修复：禁用外部实体
            // sax.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            // org.dom4j.DocumentHelper#parseText中的saxReader是由SAXReader.createDefault()创建而来，默认关闭加载外部实体
            sax.read(new InputSource(new StringReader(content)));
            return "xxe";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @ApiOperation(value = "javax.xml.bind.Unmarshaller#unmarshal")
    @RequestMapping(value = "/v5", method = RequestMethod.POST)
    public String V5(@RequestParam("content") String content) {
        try {
            JAXBContext context = JAXBContext.newInstance(Input.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            // 修复：禁用外部实体
            // xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            // xmlInputFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

            // 默认情况下在1.8版本上不能加载外部dtd文件，需要更改设置
            xmlInputFactory.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES, true);
            xmlInputFactory.setProperty(XMLInputFactory.SUPPORT_DTD, true);
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StringReader(content));
            unmarshaller.unmarshal(xmlStreamReader);
            return "xxe";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @ApiOperation(value = "org.jdom2.input.SAXBuilder#build", notes = "https://mvnrepository.com/artifact/org.jdom/jdom2/2.0.6")
    @RequestMapping(value = "/v6", method = RequestMethod.POST)
    public String V6(@RequestParam("content") String content) {
        try {
            SAXBuilder saxbuilder = new SAXBuilder();
            // saxbuilder.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            saxbuilder.build(new InputSource(new StringReader(content)));
            return "xxe";
        } catch (Exception e) {
            return e.toString();
        }
    }

    @ApiOperation(value = "com.thoughtworks.xstream.XStream#fromXML", notes = "https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream/1.4.6")
    @RequestMapping(value = "/v7", method = RequestMethod.POST)
    public String V7(@RequestParam("content") String content) {
        try {
            // https://x-stream.github.io/CVE-2016-3674.html
            String payload = "<sorted-set>" + "<string>foo</string>" + "<dynamic-proxy>" + "<interface>java.lang.Comparable</interface>" + "<handler class=\"java.beans.EventHandler\">" + " <target class=\"java.lang.ProcessBuilder\">" + " <command>" + " <string>calc.exe</string>" + " </command>" + " </target>" + " <action>start</action>" + "</handler>" + "</dynamic-proxy>" + "</sorted-set>";
            XStream xstream = new XStream();
            xstream.fromXML(content);
            return "xxe";
        } catch (Exception e) {
            return e.toString();
        }
    }
}
