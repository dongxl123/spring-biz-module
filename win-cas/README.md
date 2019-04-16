## win-cas
> 适用基于springboot或spring创建的项目，依赖spring-webmvc、cas-client-core，与springboot集成较方便

- 提供登出接口
- 提供注解@EnableWinCasClient

---
## SPRINGBOOT项目集成方法

### 引入jar包
```text
 compile("com.winbaoxian.module:win-cas:1.0.0-SNAPSHOT")
```

### application.yml增加配置
```yaml
cas:
  server-url-prefix: https://sso.winbaoxian.cn
  server-login-url: https://sso.winbaoxian.cn/login
  server-logout-url: https://sso.winbaoxian.cn/logout
  client-host-url: http://www.winbaoxian.com:9998
  logout-redirect-url: http://test.winbaoxian.com
  use-single-sign-out: true
```

### Application加上注解@EnableWinCasClient
```java
   @EnableWinCasClient
```

---
## Spring项目集成方法
### 引入jar包
```text
 compile("com.winbaoxian.module:win-cas:1.0.0-SNAPSHOT")
```
    
### 增加配置文件win-cas.properties
```text
cas.server-url-prefix=https://sso.winbaoxian.cn
cas.server-login-url=https://sso.winbaoxian.cn/login
cas.server-logout-url=https://sso.winbaoxian.cn/logout
cas.client-host-url=http://www.winbaoxian.com:9998
cas.logout-redirect-url=http://test.winbaoxian.com
cas.use-single-sign-out=true
```

### 修改dispatcher-servlet.xml
- dispatcher-servlet.xml增加 
```xml
    <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
        <property name="detectHandlerMethodsInAncestorContexts">
            <value>true</value>
        </property>
    </bean>
```

###增加配置文件WinCasConfiguration.java
```java
@Configuration
@EnableWinCasClient
public class WinCasConfiguration {
}
```
  