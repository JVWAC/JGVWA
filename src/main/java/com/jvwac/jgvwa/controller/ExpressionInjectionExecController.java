package com.jvwac.jgvwa.controller;

import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.mvel2.sh.ShellSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@RequestMapping(value = "expr")
@RestController
public class ExpressionInjectionExecController {

    @ApiOperation(value = "org.mvel2.sh.ShellSession.exec", notes = "https://mvnrepository.com/artifact/org.mvel/mvel2/2.4.0.Final")
    @RequestMapping(value = "/v1", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeStr", value = "example input", required = true, dataType = "String",
                    defaultValue = "java.lang.Runtime.getRuntime().exec(\"calc\")")})
    public void V1(@RequestParam(value = "codeStr") String codeStr) throws Exception {
        Class clazz = Class.forName("org.mvel2.sh.ShellSession");
        Constructor constructor = clazz.getConstructor(null);
        Method method = clazz.getMethod("exec", new Class[]{String.class});
        method.invoke(constructor.newInstance(null), new String[]{codeStr});
        org.mvel2.sh.ShellSession session = new ShellSession();
        session.exec("pwd");  // command white list, ls can calc does not can
    }

    @ApiOperation(
            value = "com.ql.util.express.ExpressRunner.execute(java.lang.String, com.ql.util.express.IExpressContext<java.lang.String,java.lang.Object>, java.util.List<java.lang.String>, boolean, boolean)",
            notes = "https://mvnrepository.com/artifact/com.alibaba/QLExpress/3.3.2")
    @RequestMapping(value = "/v2", method = RequestMethod.POST)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codeStr", value = "example input", required = true, dataType = "String",
                    defaultValue = "java.lang.Runtime.getRuntime().exec(\"calc\")")})
    public void V2(@RequestParam(value = "codeStr") String codeStr) throws Exception {
        ExpressRunner expressRunner = new ExpressRunner();
        expressRunner.execute(codeStr, new DefaultContext<>(), null, false, true);
    }
}
