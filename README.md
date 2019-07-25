# spring-biz-module

spring-biz-module 业务组件项目，目标是5分钟内集成通用业务模块，目前已实现权限管理。

---
## win-security

> 适用基于springboot或spring创建的项目，依赖spring-data-jpa、shiro，与springboot集成较方便

- 提供权限管理相关的接口
- 提供接口文档（使用[apidoc](http://apidocjs.com/)生成）
- winSecurity提供的接口可动态配置，默认全部提供
- 用户、角色信息支持扩展
- 关键业务逻辑支持扩展
- 权限可控制菜单、后端请求、页面的按钮，只要配置就可以使用
- 使用shiro对请求进行拦截处理, 支持扩展
- 请求拦截规则：对配置的请求做拦截，未配置的请求默认都可以访问；特殊: winSecurity提供的接口未配置时, 只有用户登录后才能访问
- 不提供登录、登出接口

> 使用文档参考：win-security/README.md

---
## win-cas

> 适用基于springboot或spring创建的项目，依赖spring-webmvc、cas-client-core，与springboot集成较方便

- 提供登出接口
- 提供注解@EnableWinCasClient

> 使用文档参考：win-cas/README.md