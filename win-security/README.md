# winSecurity
> 适用基于springboot或spring创建的项目，依赖spring-data-jpa、shiro，与springboot集成较方便

- 提供权限管理相关的接口
- 提供接口文档（使用[apidoc](http://apidocjs.com/)生成）
- winSecurity提供的接口可动态配置，默认全部提供
- 用户、角色信息支持扩展
- 关键业务逻辑支持扩展
- 使用shiro对请求进行拦截处理, 支持扩展
- 请求拦截规则：对配置的请求做拦截，未配置的请求默认都可以访问；特殊: winSecurity提供的接口未配置时, 只有用户登录后才能访问
- 不提供登录、登出接口

---
## SPRINGBOOT项目集成方法

### 引入jar包
```text
 compile("com.winbaoxian.module:win-security:1.0.0-SNAPSHOT")
```

### 创建表
```text
   使用jar包中security.sql创建相关表，用户、角色、资源及关系表
```
    
### Application加上注解@EnableWinSecurity
```java
   @EnableWinSecurity(entityManagerFactoryRef = "entityManagerFactoryTob",
    transactionManagerRef = "transactionManagerTob")
```
> 说明
- entityManagerFactoryRef jpa相关配置的EntityManagerFactory
- transactionManagerRef 事务管理

### 系统接口改造
- 登录接口增加代码
```java   
   WinSecurityAccessService.login(String userName);
```
- 注销接口增加代码
```java   
   WinSecurityAccessService.logout();
```

### 相关接口文档
[http://docs.winbaoxian.cn/api/spring-biz-module/](http://docs.winbaoxian.cn/api/spring-biz-module/)

---

## 扩展

### 表前缀设置

- @EnableWinSecurity设置tablePrefix
- 创建表加上前缀

### Controller暴露接口控制

> 可分别控制用户管理、资源管理、角色管理、登录用户数据接口，默认全部生效

- @EnableWinSecurity设置controllerScopes
    - NONE: 全部失效
    - ALL: 全部生效
    - USER: 用户管理
    - ROLE: 角色管理
    - RESOURCE: 资源管理
    - ACCESS: 登录用户数据接口

### 用户扩展

- @EnableWinSecurity设置extensionUserDTO、extensionUserEntity

- extensionUserDTO 用户前端请求对象
```java
    @Data
    public class BrokerageAdminUserDTO extends WinSecurityBaseUserDTO {
        @JsonIgnore
        private String password;
        private String token;
        private Long topDepartmentId;
        private Long subDepartmentId;
        private String position;
        private String logoImg;
        private Integer sex;
        private Date entryTime;
        private String idCard;
        private String cityName;
        private Long cityId;
        private String storeCode;
        private String ossUserName;
        private String remark;
        private Integer type;
        private Integer serviceCount;
        private Integer bindingCount;
        private Boolean isPerson;
        private Boolean isCar;
    }
```

- extensionUserEntity entity实体
```java
    @Entity
    @DynamicInsert
    @DynamicUpdate
    @Data
    public class BrokerageAdminUserEntity extends WinSecurityBaseUserEntity {
    
        @Column(name = "password")
        private String password;
        @Column(name = "token")
        private String token;
        @Column(name = "top_department_id")
        private Long topDepartmentId;
        @Column(name = "sub_department_id")
        private Long subDepartmentId;
        @Column(name = "position")
        private String position;
        @Column(name = "logo_img")
        private String logoImg;
        @Column(name = "sex")
        private Integer sex;
        @Column(name = "entry_time")
        private Date entryTime;
        @Column(name = "id_card")
        private String idCard;
        @Column(name = "city_name")
        private String cityName;
        @Column(name = "city_id")
        private Long cityId;
        @Column(name = "store_code")
        private String storeCode;
        @Column(name = "oss_user_name")
        private String ossUserName;
        @Column(name = "remark")
        private String remark;
        @Column(name = "type")
        private Integer type;
        @Column(name = "service_count")
        private Integer serviceCount;
        @Column(name = "binding_count")
        private Integer bindingCount;
        @Column(name = "is_person")
        private Boolean isPerson;
        @Column(name = "is_car")
        private Boolean isCar;  
    }
```

### 角色扩展

- @EnableWinSecurity设置extensionRoleDTO、extensionRoleEntity

- extensionRoleDTO 角色前端请求对象
```java
    @Data
    public class BrokerageAdminRoleDTO extends WinSecurityBaseRoleDTO {
        private Integer departmentLevel;
    }
```

- extensionRoleEntity entity实体
```java
    @Entity
    @DynamicInsert
    @DynamicUpdate
    @Data
    public class BrokerageAdminRoleEntity extends WinSecurityBaseRoleEntity {  
        @Column(name = "DEPARTMENT_LEVEL")
        private Integer departmentLevel;  
    }
```

### <a name="kuozhan">业务处理扩展</a>

- @EnableWinSecurity设置extensionServiceProcessors

> 支持四种场景的业务扩展，需要实现特定的接口

  - 增加用户（IUserAddProcessor）
  - 更新用户（IUserUpdateProcessor）
  - 增加角色（IRoleAddProcessor）
  - 更新角色（IRoleUpdateProcessor）
  
> 例：

```java
    @Slf4j
    public class UserAddProcessorImpl implements IUserAddProcessor<BrokerageAdminUserDTO, BrokerageAdminUserEntity> {
    
        @Resource
        private OrgDepartmentService orgDepartmentService;
        @Resource
        private BrokerageAdminService brokerageAdminService;
    
        @Override
        public void preProcess(BrokerageAdminUserDTO dto) throws WinSecurityException {
    
        }
    
        @Override
        public void customValidateAfterCommon(BrokerageAdminUserDTO dto) throws WinSecurityException {
            if (dto.getSubDepartmentId() == null) {
                throw new WinSecurityException("未选择机构");
            }
            BrokerageOrgDepartment selectDepartment = orgDepartmentService.findById(dto.getSubDepartmentId());
            if (!CollectionUtils.isEmpty(dto.getRoleIdList())) {
                for (Long roleId : dto.getRoleIdList()) {
                    BrokerageAdminRoleDTO selectRole = brokerageAdminService.getRoleById(roleId);
                    if (!selectDepartment.getLevel().equals(selectRole.getDepartmentLevel())) {
                        throw new WinSecurityException("机构等级与角色等级不符");
                    }
                }
            }
        }
    
        @Override
        public void customMappingAfterCommon(BrokerageAdminUserDTO dto, BrokerageAdminUserEntity entity) throws WinSecurityException {
            if (StringUtils.isNotBlank(dto.getPassword())) {
                entity.setPassword(DigestUtils.md5Hex(dto.getUserName() + dto.getPassword()));
            }
        }
    
        @Override
        public void postProcess(BrokerageAdminUserDTO dto) throws WinSecurityException {
    
        }
    }
```
---
## Spring项目集成方法
### 引入jar包
```text
 compile("com.winbaoxian.module:win-security:1.0.0-SNAPSHOT")
```
- jar包版本升级
```text
    compile('org.hibernate:hibernate-core:5.0.12.Final')
    compile('org.aspectj:aspectjrt:1.8.13')
    compile('org.aspectj:aspectjweaver:1.8.13')
```
- spring版本升级到4.3.19.RELEASE，其他版本兼容也可以

### 创建表
```text
   使用jar包中security.sql创建相关表，用户、角色、资源及关系表
```
    
### 增加配置文件
```java
  @Configuration
  @EnableWinSecurity(transactionManagerRef = "transactionManagerWinSecurity", entityManagerFactoryRef = "entityManagerFactoryWinSecurity", tablePrefix = "security")
  public class WinSecurityConfiguration {
  
      @Resource
      private DataSource dataSource;
      @Resource
      private SessionFactoryImpl sessionFactory;
  
      @Bean
      public LocalContainerEntityManagerFactoryBean entityManagerFactoryWinSecurity() {
          LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
          factoryBean.setDataSource(dataSource);
          factoryBean.setPackagesToScan(new String[]{});
          factoryBean.setPersistenceUnitName("winSecurity");
          factoryBean.setJpaProperties(sessionFactory.getProperties());
          factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
          return factoryBean;
      }
  
      @Bean
      PlatformTransactionManager transactionManagerWinSecurity() {
          return new JpaTransactionManager(entityManagerFactoryWinSecurity().getObject());
      }
  
  }
```
> @EnableWinSecurity扩展方式 <a href="#kuozhan">**点击查看**</a>

### 修改spring配置文件 *.xml
- 将org.springframework.orm.hibernate**4**.* 改成 org.springframework.orm.hibernate**5**.*
- dispatcher-servlet.xml增加 
```xml
    <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
        <property name="detectHandlerMethodsInAncestorContexts">
            <value>true</value>
        </property>
    </bean>
```
### 系统接口改造
- 登录接口增加代码
```java   
   WinSecurityAccessService.login(String userName);
```
- 注销接口增加代码
```java   
   WinSecurityAccessService.logout();
```

### 相关接口文档
[http://docs.winbaoxian.cn/api/spring-biz-module/](http://docs.winbaoxian.cn/api/spring-biz-module/)

### 项目冲突解决
- Q:不能找到项目的repository？
  
  A:增加注解@EnableJpaRepositories(basePackages = {"项目repository文件夹路径", "com.winbaoxian.module.security.repository"})
