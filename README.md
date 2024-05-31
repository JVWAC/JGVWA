# JGVWA

Java General Vulnerable Web Application

## Usage
Run `com.jvwac.jgvwa.StartApplication.main`

Access `http://localhost:8080/swagger-ui.html`

## API

### Server Side Request Forgery
#### URLConnection
| No. | api       | implements                                                 |
|-----|-----------|------------------------------------------------------------|
| 1   | /ssrf/v1  | `org.apache.commons.io.IOUtils#toByteArray`                |
| 2   | /ssrf/v2a | `java.net.URLConnection#openConnection`                    |
| 3   | /ssrf/v2b | `java.net.HttpURLConnection#openConnection`                |
| 4   | /ssrf/v3  | `org.apache.commons.httpclient.HttpClient#executeMethod`   |
| 5   | /ssrf/v4  | `org.apache.http.impl.client.CloseableHttpClient#execute`  |
| 6   | /ssrf/v5  | `org.springframework.web.client.RestTemplate#getForEntity` |
| 6   | /ssrf/v5  | `okhttp3.Call#execute`                                     |


### Open Redirect

| No. | api          | implements                                                   |
|-----|--------------|--------------------------------------------------------------|
| 1   | /redirect/v1 | `org.springframework.web.servlet.ModelAndView`               |
| 2   | /redirect/v2 | `javax.servlet.http.HttpServletResponse#sendRedirect`        |
| 3   | /redirect/v3 | `javax.servlet.http.HttpServletResponse#setStatus&setHeader` |
| 4   | /redirect/v4 | `org.springframework.web.servlet.view.RedirectView`          |

### XML External Entity

| No. | api     | implements                                 |
|-----|---------|--------------------------------------------|
| 1   | /xxe/v1 | `org.xml.sax.XMLReader#parse`              |
| 2   | /xxe/v2 | `javax.xml.parsers.DocumentBuilder#parse`  |
| 3   | /xxe/v3 | `javax.xml.parsers.SAXParser#parse`        |
| 4   | /xxe/v4 | `org.dom4j.io.SAXReader#read`              |
| 5   | /xxe/v5 | `javax.xml.bind.Unmarshaller#unmarshal`    |
| 6   | /xxe/v6 | `org.jdom2.input.SAXBuilder#build`         |
| 7   | /xxe/v7 | `com.thoughtworks.xstream.XStream#fromXML` |

### Path Traversal
| No. | api           | implements                          |
|-----|---------------|-------------------------------------|
| 1   | /traversal/v1 | `java.io.File#<init>`               |
| 2   | /traversal/v2 | `java.nio.path.Paths#get`           |
| 3   | /traversal/v3 | `java.nio.file.FileSystem#getPath`  |
| 1   | /traversal/v4 | `java.nio.file.Path#resolveSibling` |
| 2   | /traversal/v5 | `java.nio.file.Path#resolve`        |
| 3   | /traversal/v6 | `java.io.FileWriter#<init>`         |
| 1   | /traversal/v7 | `java.io.FileReader#<init>`         |
| 2   | /traversal/v8 | `java.io.FileInputStream#<init>`    |
| 3   | /traversal/v9 | `java.io.FileOutputStream#<init>`   |

### JNDI Injection
| No. | api      | implements                                        |
|-----|----------|---------------------------------------------------|
| 1   | /jndi/v1 | `javax.naming.Context#lookup`                     |
| 2   | /jndi/v2 | `com.sun.rowset.JdbcRowSetImpl#setDataSourceName` |
| 3   | /jndi/v3 | `org.apache.logging.log4j.Logger#error`           |
| 3   | /jndi/v4 | `javax.naming.ldap.InitialLdapContext`            |

### Command Execute
| No. | api    | implements                                        |
|-----|--------|---------------------------------------------------|
| 1   | /ce/v1 | `java.lang.Runtime#exec`                          |
| 2   | /ce/v2 | `java.lang.ProcessBuilder#start`                  |
| 3   | /ce/v3 | `org.apache.commons.exec.DefaultExecutor#execute` |
| 4   | /ce/v4 | `com.sun.jna.Native#load`(JNA&JNI)                |
| 5   | /ce/v5 | `java.lang.ProcessImpl#start`                     |


### Server-Side Template Injection
| No. | api                      | implements                                  | stack trace                          |
|-----|--------------------------|---------------------------------------------|--------------------------------------|
| 1   | /ssti/v1                 | `freemarker`                                |                                      |
| 2   | /ssti/v2                 | `thymeleaf#path`                            |                                      |
| 3   | /ssti/v3/{path}          | `thymeleaf#path`                            |                                      |
| 4   | /ssti/v4                 | `thymeleaf#fragment`                        |                                      |
| 5   | /ssti/freemarker_example | `freemarker.template:v2.3.23`               | [freemarker](doc/ssti/freemarker.md) |
| 6   | /ssti/velocity_example   | `org.apache.velocity.app.Velocity#evaluate` | [velocity](doc/ssti/velocity.md)     |


### Serial
| No. | api        | implements                                      |
|-----|------------|-------------------------------------------------|
| 1   | /serial/v1 | `cn.hutool.core.util.XmlUtil#readObjectFromXml` |


### Code Injection Execute
| No. | api      | implements                                           |
|-----|----------|------------------------------------------------------|
| 1   | /code/v1 | `groovy.lang.GroovyShell.evaluate(java.lang.String)` |
| 2   | /code/v2 | `javax.script.ScriptEngine.eval(java.lang.String)`   |


### Expression Injection Execute
| No. | api      | implements                                                                                                                                                                                |
|-----|----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 1   | /expr/v1 | `org.mvel2.sh.ShellSession.exec`                                                                                                                                                          |
| 2   | /expr/v2 | `com.ql.util.express.ExpressRunner.execute(java.lang.String, com.ql.util.express.IExpressContext<java.lang.String,java.lang.Object>, java.util.List<java.lang.String>, boolean, boolean)` |


## Support
 - [CodeQL](https://github.com/github/codeql/tree/main/java/ql/src/Security)
 - [OpenAI](https://chat.openai.com/chat)
 - [Hello-Java-Sec](https://github.com/j3ers3/Hello-Java-Sec)
