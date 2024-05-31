# SSTI (Server-Side Template Injection)

## velocity

### stack

```
invoke:473, Method (java.lang.reflect)
doInvoke:395, UberspectImpl$VelMethodImpl (org.apache.velocity.util.introspection)
invoke:384, UberspectImpl$VelMethodImpl (org.apache.velocity.util.introspection)
execute:173, ASTMethod (org.apache.velocity.runtime.parser.node)
execute:280, ASTReference (org.apache.velocity.runtime.parser.node)
render:369, ASTReference (org.apache.velocity.runtime.parser.node)
render:342, SimpleNode (org.apache.velocity.runtime.parser.node)
render:1378, RuntimeInstance (org.apache.velocity.runtime)
evaluate:1314, RuntimeInstance (org.apache.velocity.runtime)
evaluate:1265, RuntimeInstance (org.apache.velocity.runtime)
evaluate:180, Velocity (org.apache.velocity.app)
A2:96, SSTIController (com.example.jgvwa.controller)
```

### A2:96, SSTIController (com.example.jgvwa.controller)

![image-20230310160936559](velocity.assets/image-20230310160936559.png)

### evaluate:180, Velocity (org.apache.velocity.app)

```java
public static boolean evaluate( Context context,  Writer out, String logTag, String instring )
    throws ParseErrorException, MethodInvocationException, ResourceNotFoundException{}
```

![image-20230310161011846](velocity.assets/image-20230310161011846.png)

### evaluate:1265, RuntimeInstance (org.apache.velocity.runtime)

```java
public boolean evaluate(Context context,  Writer out, String logTag, String instring){}
```

![image-20230310161321264](velocity.assets/image-20230310161321264.png)

### evaluate:1314, RuntimeInstance (org.apache.velocity.runtime)

```java
public boolean evaluate(Context context, Writer writer, String logTag, Reader reader){}
```

![image-20230310161440183](velocity.assets/image-20230310161440183.png)

### render:1378, RuntimeInstance (org.apache.velocity.runtime)

```java
public boolean render(Context context, Writer writer, String logTag, SimpleNode nodeTree){}
```

![image-20230310161604439](velocity.assets/image-20230310161604439.png)

### render:342, SimpleNode (org.apache.velocity.runtime.parser.node)

```java
public boolean render( InternalContextAdapter context, Writer writer)
    throws IOException, MethodInvocationException, ParseErrorException, ResourceNotFoundException{}
```

![image-20230310162045458](velocity.assets/image-20230310162045458.png)

### render:369, ASTReference (org.apache.velocity.runtime.parser.node)

```java
public boolean render(InternalContextAdapter context, Writer writer) throws IOException, MethodInvocationException{}
```

![image-20230310162212710](velocity.assets/image-20230310162212710.png)

### execute:280, ASTReference (org.apache.velocity.runtime.parser.node)

```java
public Object execute(Object o, InternalContextAdapter context) throws MethodInvocationException{}
```

![image-20230310163933579](velocity.assets/image-20230310163933579.png)

### execute:173, ASTMethod (org.apache.velocity.runtime.parser.node)

```java
public Object execute(Object o, InternalContextAdapter context) throws MethodInvocationException{}
```

![image-20230310162925543](velocity.assets/image-20230310162925543.png)

### invoke:384, UberspectImpl$VelMethodImpl (org.apache.velocity.util.introspection)

```java
public Object invoke(Object o, Object[] actual) throws Exception{}
```

![image-20230310163317351](velocity.assets/image-20230310163317351.png)

### doInvoke:395, UberspectImpl$VelMethodImpl (org.apache.velocity.util.introspection)

```java
protected Object doInvoke(Object o, Object[] actual) throws Exception {return method.invoke(o, actual);}
```

![image-20230310163433584](velocity.assets/image-20230310163433584.png)

### invoke:473, Method (java.lang.reflect)

![image-20230310163512911](velocity.assets/image-20230310163512911.png)