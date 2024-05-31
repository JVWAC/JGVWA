package com.jvwac.jgvwa.controller;

import groovy.lang.GroovyShell;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@RequestMapping(value = "code")
@RestController
public class CodeInjectionExecController {

    @ApiOperation(value = "groovy.lang.GroovyShell.evaluate(java.lang.String)", notes = "https://mvnrepository.com/artifact/org.apache.groovy/groovy-all/4.0.15")
    @RequestMapping(value = "/v1", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeStr", value = "example input", required = true, dataType = "String",
                    defaultValue = "java.lang.Runtime.getRuntime().exec(\"calc\")")
    })
    public String V1(@RequestParam(value = "codeStr") String codeStr) throws Exception {
        GroovyShell groovyShell = new GroovyShell();
        Object object = groovyShell.evaluate(codeStr);
        if (object != null) {
            return object.toString();
        } else {
            return "evaluate return null";
        }
    }

    @ApiOperation(value = "javax.script.ScriptEngine.eval(java.lang.String)")
    @RequestMapping(value = "/v2", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeStr", value = "example input", required = true, dataType = "String",
                    defaultValue = "function test(){ return java.lang.Runtime};r=test();r.getRuntime().exec(\"calc\")")
    })
    public void V2(@RequestParam(value = "codeStr") String codeStr) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager(null);
        ScriptEngine engine = manager.getEngineByName("js");
        engine.eval(codeStr);
    }
}
