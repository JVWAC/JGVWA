# SSTI (Server-Side Template Injection)

## freemarker

### stack

```
exec:347, Runtime (java.lang)
exec:84, Execute (freemarker.template.utility)
_eval:62, MethodCall (freemarker.core)
eval:101, Expression (freemarker.core)
calculateInterpolatedStringOrMarkup:100, DollarVariable (freemarker.core)
accept:63, DollarVariable (freemarker.core)
visit:334, Environment (freemarker.core)
visit:340, Environment (freemarker.core)
process:313, Environment (freemarker.core)
process:383, Template (freemarker.template)
A1:79, SSTIController (com.example.jgvwa.controller)
```

### A1:79, SSTIController (com.example.jgvwa.controller)

![image-20230310184046314](freemarker.assets/image-20230310184046314.png)
### process:383, Template (freemarker.template)

```java
public void process(Object dataModel, Writer out)
throws TemplateException, IOException {
    createProcessingEnvironment(dataModel, out, null).process();
}
```

![image-20230310184155816](freemarker.assets/image-20230310184155816.png)

![image-20230310184347355](freemarker.assets/image-20230310184347355.png)

### process:313, Environment (freemarker.core)

```java
public void process() throws TemplateException, IOException {}
```

![image-20230310184548449](freemarker.assets/image-20230310184548449.png)
### visit:340, Environment (freemarker.core)

```java
void visit(TemplateElement element) throws IOException, TemplateException {
    // ATTENTION: This method body is manually "inlined" into visit(TemplateElement[]); keep them in sync!
    pushElement(element);
    try {
        TemplateElement[] templateElementsToVisit = element.accept(this);
        if (templateElementsToVisit != null) {
            for (TemplateElement el : templateElementsToVisit) {
                if (el == null) {
                    break;  // Skip unused trailing buffer capacity 
                }
                visit(el);
            }
        }
    } catch (TemplateException te) {
        handleTemplateException(te);
    } finally {
        popElement();
    }
    // ATTENTION: This method body above is manually "inlined" into visit(TemplateElement[]); keep them in sync!
}
```

![image-20230310185002456](freemarker.assets/image-20230310185002456.png)

![image-20230310185340091](freemarker.assets/image-20230310185340091.png)

![image-20230310185842146](freemarker.assets/image-20230310185842146.png)

### visit:334, Environment (freemarker.core)

![image-20230310190106186](freemarker.assets/image-20230310190106186.png)
### accept:63, DollarVariable (freemarker.core)

```java
TemplateElement[] accept(Environment env) throws TemplateException, IOException {}
```

![image-20230310190212865](freemarker.assets/image-20230310190212865.png)
### calculateInterpolatedStringOrMarkup:100, DollarVariable (freemarker.core)

```java
protected Object calculateInterpolatedStringOrMarkup(Environment env) throws TemplateException {
    return EvalUtil.coerceModelToStringOrMarkup(escapedExpression.eval(env), escapedExpression, null, env);
}
```

![image-20230310190659217](freemarker.assets/image-20230310190659217.png)

### eval:101, Expression (freemarker.core)

![image-20230310191044205](freemarker.assets/image-20230310191044205.png)
### _eval:62, MethodCall (freemarker.core)

![image-20230310191238228](freemarker.assets/image-20230310191238228.png)

### exec:84, Execute (freemarker.template.utility)

![image-20230310191411712](freemarker.assets/image-20230310191411712.png)

### exec:347, Runtime (java.lang)

![image-20230310191436739](freemarker.assets/image-20230310191436739.png)
