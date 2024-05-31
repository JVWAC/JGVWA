package com.jvwac.jgvwa.controller.jndi;

import io.swagger.annotations.ApiOperation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

import com.sun.rowset.JdbcRowSetImpl;

import java.sql.SQLException;
import java.util.Hashtable;

@RequestMapping(value = "/jndi")
@RestController
public class JNDIController {
    private static final Logger logger = LogManager.getLogger(JNDIController.class);

    @ApiOperation(value = "javax.naming.Context#lookup")
    @RequestMapping(value = "/v1", method = RequestMethod.GET)
    public void V1(@RequestParam(value = "name") String name) throws NamingException {
        Context context = new InitialContext();
        context.lookup(name);
    }

    @ApiOperation(value = "com.sun.rowset.JdbcRowSetImpl#setDataSourceName")
    @RequestMapping(value = "/v2", method = RequestMethod.GET)
    public void V2(@RequestParam(value = "name") String name) throws SQLException {
        JdbcRowSetImpl JdbcRowSetImpl_inc = new JdbcRowSetImpl();
        JdbcRowSetImpl_inc.setDataSourceName(name);
        JdbcRowSetImpl_inc.setAutoCommit(true);
    }

    @ApiOperation(value = "org.apache.logging.log4j.Logger#error", notes = "https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core/2.8.2")
    @RequestMapping(value = "/v3", method = RequestMethod.GET)
    public void V3(@RequestParam(value = "name") String name) {
        logger.error(name);
    }

    @ApiOperation(value = "javax.naming.ldap.InitialLdapContext", notes = "https://mvnrepository.com/artifact/org.apache.xbean/xbean-spring/3.1")
    @RequestMapping(value = "/v4", method = RequestMethod.GET)
    public void V4(@RequestParam(value = "factoryClass") String factoryClass, @RequestParam(value = "fileUrl") String fileUrl) throws SQLException {
        Hashtable<String, String> env = new Hashtable<>();
//        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.xbean.spring.jndi.SpringInitialContextFactory");
        env.put(Context.INITIAL_CONTEXT_FACTORY, factoryClass);
        env.put(Context.PROVIDER_URL, fileUrl);

        try {
            LdapContext ctx = new InitialLdapContext(env, null);
            ctx.close();
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
