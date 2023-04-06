# 1  SpringBoot

### 1.1  初始化项目

创建项目时,请注意:

​		1.SpringBoot和JDK的版本对应问题——[SpringBoot与JDK的版本对应](https://blog.csdn.net/weixin_44545756/article/details/128267641?spm=1001.2101.3001.6650.6&utm_medium=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~Rate-6-128267641-blog-113830142.pc_relevant_3mothn_strategy_recovery&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~BlogCommendFromBaidu~Rate-6-128267641-blog-113830142.pc_relevant_3mothn_strategy_recovery&utm_relevant_index=10)

​		2.maven的路径、配置文件以及依赖路径:

![](E:\VSCode\3.png)

项目结构一般如下：

```properties
├─community
│  ├─.idea								--->idea生成,用于配置应用信息的文件目录(包括类库，应用字符编码，模块，工作区等)
│  ├─.mvn								--->SpringBoot生成,用于一键通过Maven构建、编译、打包和部署的文件目录
│  ├─src								--->SpringBoot生成,存放应用源代码的文件目录
│  │  ├─main							--->存放程序实际执行相关代码
│  │  │  ├─java.com.lesson.community
│  │  │  │  ├─config					--->配置文件
│  │  │  │  ├─controller				--->业务控制层,控制业务逻辑
│  │  │  │  ├─dao						--->数据库持久化层,主要与数据库交互
│  │  │  │  ├─entity					--->实体层,数据库在项目中的类
│  │  │  │  └─service					--->业务层,控制业务
│  │  │  └─resources					--->存放静态文件，模板文件，应用配置文件等资源或配置类文件
│  │  │      ├─static					--->存放静态资源(页面、样式等)
│  │  │      ├─templates				--->存放动态资源(页面等)
│  │  │      └─application.properties	--->用于存放程序的各种依赖模块的配置信息,比如服务端口、数据库连接配置等
│  │  └─test							--->存放程序测试相关代码
│  └─target								--->应用构建时生成,主要存放了源代码编译后的class文件,相关的配置文件以及打好的包												文件等用于实际执行的文件
│  ├─.gitignore							--->SpringBoot生成,版本控制系统Git的配置文件,表示忽略提交
│  ├─mvnw and mvnw.cmd					--->SpringBoot生成,和.mvn目录下文件搭配使用,是执行mvnw命令的入口
│  ├─pom.xml							--->SpringBoot生成的项目对象模型文档,主要描述项目的Maven坐标和依赖关系等信												息,是项目级别的配置文件
```

application.properties配置

```properties
# 数据库连接路径
# 	useUnicode=true  表示使用Unicode字符，因此可以使用中文
# 	characterEncoding=utf8  设置编码方式
# 	useSSL=true   设置安全连接
# 	serverTimezone=UTC    设置全球标准时间
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/community
# 链接数据库用户名及密码
spring.datasource.username=root
spring.datasource.password=root
# 数据库驱动
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

pom.xml依赖配置

​	由于一般是使用idea自动创建springboot项目，pom.xml中的基本信息无需添加。

```xml
<dependencies>
<!--        java模板引擎依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
<!--        提供Web开发场景所需的底层所有依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
<!--        实现指定目录（默认为classpath路径）下的文件进行更改后，项目自动重启，更改后的代码自动生效-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
<!--        添加测试单元功能-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
<!--        mysql的链接依赖-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.19</version>
        <scope>compile</scope>
    </dependency>
<!--        SpringData JPA依赖-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
<!--        单元测试框架-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```



# 2  SpringMVC

### 2.1  概念

MVC 模式，全称为 Model-View-Controller（模型-视图-控制器）模式，它是一种软件架构模式，其目标是将软件的用户界面（即前台页面）和业务逻辑分离，使代码具有更高的可扩展性、可复用性、可维护性以及灵活性。

![](E:\VSCode\MVC.png)

| 分层               | 描述                                                         |
| :----------------- | ------------------------------------------------------------ |
| Model(模型)        | 应用程序的主体部分，主要由以下 2 部分组成：<br />实体类 Bean：专门用来存储业务数据的对象，通常与数据库中的某个表对应<br />业务处理 Bean：指 Service 或 Dao 的对象，专门用于处理业务逻辑、数据库访问 |
| View(视图)         | 在应用程序中专门用来与浏览器进行交互，展示数据的资源。在 Web 应用中，View 就是常说的前台页面，通常由 HTML、JSP、CSS、JavaScript 等组成 |
| Controller(控制器) | 通常指的是应用程序的 Servlet。负责将用户的请求交给模型（Model）层进行处理，并将 Model 层处理完成的数据，返回给视图（View）渲染并展示给用户 |

### 2.2  thymeleaf组件

[Themleaf模板基础语法使用介绍](https://blog.csdn.net/weixin_45019350/article/details/108885934)

### 2.3  kaptcha验证码组件

pom.xml配置

```xml
<!-- https://mvnrepository.com/artifact/com.github.penggle/kaptcha -->
<dependency>
    <groupId>com.github.penggle</groupId>
    <artifactId>kaptcha</artifactId>
    <version>2.3.2</version>
</dependency>
```

编写配置类

```java
@Configuration
public class kaptcha {

    @Bean
    public Producer KaptchaProcedure() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "100");
        properties.setProperty("kaptcha.image.height", "40");
        properties.setProperty("kaptcha.textproducer.font.size", "32");
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789QWERTYUIOPLKJHGFDSAZXCVBMN");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");

        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
```

使用

```java
@RequestMapping(path = "/kaptcha", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response, HttpSession session) {
        //生成验证码
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        //将验证码存入session
        session.setAttribute("kaptcha", text);

        //将图片传给浏览器
        response.setContentType("image/png");
        try {
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            logger.error("验证码响应失败:" + e.getMessage());
        }
    }
```

### 2.4  图片上传

前端代码

```html
<!--上传图片必须使用post方法,同时注意enctype属性-->
<form class="mt-5" method="post" enctype="multipart/form-data" th:action="@{/user/upload}">
    <div class="form-group row mt-4">
        <label class="col-sm-2 col-form-label text-right">选择头像:</label>
        <div class="col-sm-10">
            <div class="custom-file">
                <input type="file" th:class="|custom-file-input ${error!=null?'is-invalid':''}|"
                       name="headerImage" id="head-image" lang="es" required="">
                <label class="custom-file-label" for="head-image" data-browse="文件">选择一张图片</label>
                <div class="invalid-feedback" th:text="${error}"></div>
            </div>		
        </div>
    </div>
    <div class="form-group row mt-4">
        <div class="col-sm-2"></div>
        <div class="col-sm-10 text-center">
            <button type="submit" class="btn btn-info text-white form-control">立即上传</button>
        </div>
    </div>
</form>
```

后端上传代码

```java
// 上传图片传入的是MultipartFile类型的数据
public String uploadHeader(MultipartFile headerImage) {
    
    	// 获取图片名字
        String fileName = headerImage.getOriginalFilename();
        // 获取后缀名
        String suffix = fileName != null ? (fileName.substring(fileName.lastIndexOf("."))) : "";

        // 生成随机的文件名
        fileName = CommunityUtil.getUUID() + suffix;
        // 确认文件存储的路径
        File storagePath = new File(uploadPath + "/" + fileName);

        try {
            headerImage.transferTo(storagePath);
        } catch (IOException e) {
            throw new RuntimeException("上传文件失败,服务器发生异常！" + e);
        }
    	return "";
    }
```

读取上传的代码

```java
@RequestMapping(path = "/header/{filename}", method = RequestMethod.GET)
public void getHeader(@PathVariable("filename") String filename, HttpServletResponse response) {
        // 头像的服务器存放路径
        filename = uploadPath + "/" + filename;
        // 文件后缀
        String suffix = filename.substring(filename.lastIndexOf(".") + 1);
        // 响应图片
        response.setContentType("image/" + suffix);
        try (
                OutputStream os = response.getOutputStream();
                FileInputStream fis = new FileInputStream(filename);
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败:" + e.getMessage());
        }
    }
```

### 2.5  自定义注解

注解其实就是一种标记，可以在程序代码中的关键节点（类、方法、变量、参数、包）上打上这些标记，然后程序在编译时或运行时可以检测到这些标记从而执行一些特殊操作。因此可以得出自定义注解使用的基本流程：

1. 定义注解——相当于定义标记
2. 配置注解——把标记打在需要用到的程序代码中
3. 解析注解——在编译期或运行时检测到标记，并进行特殊操作

##### 2.5.1  基本语法

```java
public @interface CherryAnnotation {
	public String name();  // 注解类型元素
	int age() default 18;
	int[] array();
}
```

定义注解类型元素时需要注意如下几点：

1. 访问修饰符必须为public，不写默认为public
2. 该元素的类型只能是**基本数据类型**、**String**、**Class**、**枚举**类型、**注解**类型以及上述类型的一维数组
3. ()不是定义方法参数的地方，也不能在括号中定义任何参数，仅仅只是一个特殊的语法
4. default代表默认值，值必须和第2点定义的类型一致
5. 如果没有默认值，代表后续使用注解时必须给该类型元素赋值

##### 2.5.2  常用元注解

**@Target**			用来限定此自定义注解能够被应用于哪些Java元素上面

```java
@Target(value={ElementType.METHOD})	
```

|      字段       |                 说明                 |
| :-------------: | :----------------------------------: |
|      TYPE       | 类，接口（包括注解类型）或枚举的声明 |
|      FIELD      |              属性的声明              |
|     METHOD      |              方法的声明              |
|    PARAMETER    |           方法形式参数声明           |
|   CONSTRUCTOR   |            构造方法的声明            |
| LOCAL_VARIABLE  |             局部变量声明             |
| ANNOTATION_TYPE |             注解类型声明             |
|     PACKAGE     |               包的声明               |

**@Retention**		修饰自定义注解的声明力

```java
@Retention(RetentionPolicy.RUNTIME)

SOURCE  	注解将被编译器忽略掉
CLASS		注解将被编译器记录在class文件中，但在运行时不会被虚拟机保留，这是一个默认的行为
RUNTIME		注解将被编译器记录在class文件中，而且在运行时会被虚拟机保留，因此它们能通过反射被读取到
```

**@Documented**		指定自定义注解是否能随着被定义的java文件生成到JavaDoc文档当中

**@Inherited**				指定某个自定义注解如果写在了父类的声明部分，那么子类的声明部分也能自动拥有该注解

### 2.6  HTTP 服务器交互

[HTTP中Get、Post、Put与Delete的区别](https://blog.csdn.net/haif_city/article/details/78333213)

### 2.7  统一异常处理

```java
@ControllerAdvice(annotations = Controller.class)	//  表明是全局配置类，annotations设置监听注解
public class ExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Exception.class})	// controller出现异常后被调用，用于处理捕获到的异常
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生异常:"  + e.getMessage());
        for (StackTraceElement element : e.getStackTrace()){    // 循环输出详细信息
            logger.error(element.toString());
        }

        // 判断异步请求 or 普通请求
        String xRequestdWith = request.getHeader("x-requested-with");
        if(xRequestdWith.equals("XMLHttpRequest")){ // 异步请求
            // /plain 普通类型 但是是json格式
            response.setContentType("application/plain;charset=utf-8");
        }
        else{
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }
}

```



### 2.8  Tips

1、关于在其他页面返回首页路径失败:

例如：首页路径设为$/community/index$,然后访问其他页面$/community/discuss/detail/1$，在此页面下返回首页，此时首页的路径变为$/community/discuss/index$

原因：MVC中，一个$Controller$对应一个路径，将$Controller$类的路径整合到每个方法上,如下。那么当在$MessageController$所代表的路径下进行返回首页时，由方法返回至$Controller$，发现没有$RequestMapping$，那以为默认路径为“/”，而返回首页的$HomeController$也没有$RequestMapping$，则程序以为这两个类是同级，就会出现错误，只要将每个$Controller$指明各自路径即可。

```java
// 样例
@Controller
@RequestMapping("/letter")    //修改后
public class MessageController {

    @RequestMapping(path = "/letter/list", method = RequestMethod.GET)//修改前
    @RequestMapping(path = "/list", method = RequestMethod.GET)//修改后
    public String getLetterList(Model model, PageObject page) {}

    @RequestMapping(path = "/letter/detail/{conversationId}", method = RequestMethod.GET)//修改前
    @RequestMapping(path = "/detail/{conversationId}", method = RequestMethod.GET)//修改后
    public String getLetterDetail(@PathVariable("conversationId") String conversationId) {}
     
    @RequestMapping(path = "/letter/send", method = RequestMethod.POST)//修改前
    @RequestMapping(path = "/send", method = RequestMethod.POST)//修改后
    @ResponseBody
    public String sendLetter(String toName, String content) {}
}
```

```java
// 返回首页
@Controller
public class HomeController {

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, PageObject page) {
    	return "/index";
}

```

# 3  Spring Data JPA

### 3.1  配置 Spring Data JPA

pom.xml依赖项

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

application.properties配置

```properties
#----JPA----
#自动创建、更新、验证数据库表结构
#   create：每次加载hibernate时都会删除上一次的生成的表，然后根据你的model类再重新来生成新表，
#       哪怕两次没有任何改变也要这样执行，这就是导致数据库表数据丢失的一个重要原因。
#   create-drop：每次加载hibernate时根据model类生成表，但是sessionFactory一关闭,表就自动删除。
#   update：最常用的属性，第一次加载hibernate时根据model类会自动建立起表的结构（前提是先建立好数据库），
#       以后加载hibernate时根据model类自动更新表结构，即使表结构改变了但表中的行仍然存在不会删除以前的行。
#       要注意的是当部署到服务器后，表结构是不会被马上建立起来的，是要等应用第一次运行起来后才会。
#   validate：每次加载hibernate时，验证创建数据库表结构，只会和数据库中的表进行比较，不会创建新表，但是会插入新值。
spring.jpa.properties.hibernate.hbm2ddl.auto=update
# Hibernate所支持的SQL方言
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
# 是否显示SQL语句
spring.jpa.properties.show-sql=false
```

### 3.2  使用Spring Data JPA

##### 3.2.1  注解介绍

JPA通过编写entity实体（在连接数据库的情况下）可以自动建立对应数据表，通过注解对其进行自定义：

|      注解       |                             解释                             |
| :-------------: | :----------------------------------------------------------: |
|     @Entity     |           声明类为实体或表，如果要自动创建必须添加           |
| @Table(name="") |                           声明表名                           |
|       @Id       |                           声明主键                           |
|     @Column     | 指定持久属性栏属性，其中的columnDefinition属性可以为字段设置属性默认值 |
| @GeneratedValue |                      提供主键的生成策略                      |
| @TableGenerator | 将当前主键的值单独保存到数据库的一张表里去,主键的值每次都是从该表中查询获得<br />适用于任何数据库，不必担心兼容问题 |

<b>example:</b>

```java
@Entity
//通过 uniqueConstraints 设定唯一约束键
@Table(name="user", uniqueConstraints = @UniqueConstraint(columnNames = {"username","email"}))
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "idKey") //id自增策略
    @TableGenerator(
            name = "idKey",
            table = "primarykeys",  //用来自定义主键生成表的表名称
            pkColumnName = "pre_Key", //指定主键生成表的主键列的名称
            valueColumnName = "prekey_value", //用来自定义在主键生成表中存储ID的列名称
            pkColumnValue = "user_key",  //为当前主键生成器指定在生成器表中的主键值,用于将这组生成的值与可能存储在表中的其他值区分开来
            allocationSize = 1,  //每次获取主键增量值,默认为50
            initialValue = 150   //初始化主键值
    )
    @Column(name = "id", columnDefinition = "int(11) NOT NULL")
    private int id;

    @Column(name = "username", columnDefinition = "varchar(50) DEFAULT NULL")
    private String username;

    @Column(name = "password", columnDefinition = "varchar(50) DEFAULT NULL")
    private String password;
    }
```

##### 3.2.2  CRUD操作步骤

**(1)  创建实体类**

```java
//entity层
@Entity
@Table(name="user")
public class UserEntity {}
```

**(2)  创建数据库访问接口**

```java
//dao层
//同时继承JpaRepository<T, S>接口   --T:要操作的实体类  --S:该实体类的主键类型
@Repository
public interface UserDao extends JpaRepository<UserEntity, Integer> {
    //如果想添加自定义方法,可以直接通过@Query注解进行操作--JPQL,JPQL不能实现INSERT
    //nativeQuery = true 表明是可以执行原生语句,JPA一般使用JPQL,其中表明替换为实体类名
    @Query(value = "SELECT u FROM UserEntity u WHERE u.id = ?1")
    UserEntity getUserEntityByID(Integer id); //通过ID查询用户
    
    @Modifying //通知 SpringData 这是一个 DELETE 或 UPDATE 操作,必须添加
    @Transactional //代码执行出错的时候能够进行事务的回滚
    @Query(value = "UPDATE UserEntity SET status = :status WHERE id = :id")
    int UpdateStatus(@Param("id") Integer id, @Param("status") Integer status);
}
```

**(3)  创建业务逻辑接口**

```java
//service层
public interface UserService {
    public List<UserEntity> findAll();
    public void saveAndFlush(UserEntity userEntity);
}
```

**(4)  创建业务逻辑类**

```java
//service层下的impl (通过userDao中的方法来对userService方法进行实现)
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao ;
    @Override
    public List<UserEntity> findAll() {
        return userDao.findAll();
    }

    @Override
    public void saveAndFlush(UserEntity userEntity) {
        userDao.saveAndFlush(userEntity);
    }
}
```

**(5)  执行测试**

```java
//创建测试类
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommunityApplication.class)
public class MyTest {
    @Autowired
    private UserService userService; //注入对应业务逻辑接口

    @Test
    public void test(){

        UserEntity userEntity = new UserEntity(1, "user1", "pw","sa","com", 1, 2, "hu", "g",new Timestamp(new java.util.Date().getTime())); //初始化实体类

        userService.saveAndFlush(userEntity); //进行数据库插入
        
        //ERRORL:多次插入同样的数据,会插入多次,同时主键的值与插入对象设定的值不同
        //ANSWER:由于在声明实体类时,将主键设置为从主键生成表中获取,所以插入时主键的作用不大,可以另设一个唯一约束

        for(UserEntity user: userService.findAll()) { //进行数据库查找
            System.out.println(user.getUsername());
        }
    }

}
```

上述流程是首先创建实体，然后定义Dao接口；在Dao层只是继承**JpaRepository<T, S>**接口（如果要实现Jpa接口中没有的方法，在Dao层编写，一般是数据库相关，利用**@Query**注解实现）；然后定义Service层的接口，定义所使用的方法；在Service下的impl中对Service接口进行实现。

根据实际情况，可以选择直接在Dao层实现类，自己编写方法，不需要每个实体都对应一个Dao、一个Service、一个ServiceImpl。

Repository 模式与 DAO 模式对比：

- DAO 是数据持久化的抽象；而 Repository 是一组对象的抽象；
- DAO 是偏底层的概念，靠近存储系统，例如 DB；Repository 是偏高层的概念，靠近应用中的域对象，例如 POJO；
- DAO 是数据映射、访问层，目的是消除丑陋的 SQL 语句；Repository 存在于域对象与数据访问层之间，目的是隐藏收集、准备域对象的烦琐细节；
- DAO 不可通过 Repository 实现；但 Repository 可以使用 DAO 访问底层的数据存储；

实际开发中，根据实际情况使用。

##### 3.2.3  Tips

**(1)  利用SpringData JPA建表后，表中字段与实体类的字段顺序不一致**

**原因**

hibernate源码中用的是TreeMap存储实体类字段，TreeMap属性是无序的

**解决**

从以下路径中找到**PropertyContainer**类

```properties
External Libraries-->org.hibernate.hibernate-core.5.6.15.Final
	-->hibernate-core-5.6.15.Final.jar!-->org.hibernate-->cfg-->PropertyContainer.class
```

然后在当前项目的**com.lesson.community**下面新建**org.hibernate.cfg**包，其下新建**PropertyContainer**类，然后将原**PropertyContainer**类中的源码复制到新创建的**PropertyContainer**类里，将其中的**TreeMap**替换为**LinkedHashMap**，删除数据表，重新运行。

**(2)  JPQL动态查询报错**

```java
ERROR:')', ',', <expression> or <operator> expected, got '='
```

**解决**

​	设置**@Query()**的属性**nativeQuery = true**(别管为什么，改就对了，问就是我也不知道)，即可使用原生语句，此时查询语句中应为表名以及表的字段，而不是实体类名及属性

```java
@Query(value = "SELECT count(*) from discuss_post  where " +
            "if(:userid = 0, user_id=:userid,1=1)", nativeQuery = true)
int getDiscussPostRows(@Param("userid") int userid);
```

# 4  Spring Email

### 4.1  配置

pom.xml文件配置

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-mail -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

application.properties配置

```properties
# 邮箱配置
spring.mail.host=smtp.163.com
# 邮箱端口配置
spring.mail.port=465
# 邮箱密码配置(授权码,非密码)
spring.mail.password=XXXXXXX
# 邮箱账号配置
spring.mail.username=MailName
# 协议设置
spring.mail.protocol=smtp
# 启用 SSL 安全链接
spring.mail.properties.mail.smtp.ssl.enable=true
```

### 4.2  使用

负责邮件发送的工具类

```java
@Service
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    public void senMail(String to, String subject, String content) {
        try {
            // JavaMainlSender 通过构建出的 mimeMessage 发送邮件
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            // 利用 MimeMessageHelper 构建 mimeMessage
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

            mimeMessageHelper.setFrom(from);        // 发送人
            mimeMessageHelper.setTo(to);            // 接收人
            mimeMessageHelper.setSubject(subject);  // 邮件主题
            mimeMessageHelper.setText(content, true);     // 邮件内容

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("邮件发送失败:" + e.getMessage());
        }
    }
}
```

发送测试

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommunityApplication.class)
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;  // 注入模板引擎

    @Test // 发送普通文件
    public void TextMail(){
        mailClient.senMail("2356461336@qq.com", "Test", "这是一个小测试");
    }

    @Test // 利用 thymeleaf 发送模板文件
    public void HtmlMail(){
        Context context = new Context();
        context.setVariable("username", "Today");

        String content = templateEngine.process("/mail/demo",context);

        mailClient.senMail("2356461336@qq.com", "Test", content);
    }
}
```

# 5  Spring Data MongoDB

应对“三高”需求：

• **High performance** - 对数据库高并发读写的需求。

• **Huge Storage** - 对海量数据的高效率存储和访问的需求。

• **High Scalability && High Availability**- 对数据库的高可扩展性和高可用性的需求。

### 5.1  介绍

##### 5.1.1  简介

MongoDB是一个开源、高性能、无模式的文档型数据库，当初的设计就是用于简化开发和方便扩展，是NoSQL数据库产品中的一种。是最像关系型数据库（MySQL）的非关系型数据库。

它支持的数据结构非常松散，是一种类似于 JSON 的 格式叫BSON，所以它既可以存储比较复杂的数据类型，又相当的灵活。

MongoDB中的记录是一个文档，它是一个由字段和值对（field:value）组成的数据结构。MongoDB文档类似于JSON对象，即一个文档认为就是一个对象。字段的数据类型是字符型，它的值除了使用基本的一些类型外，还可以包括其他文档、普通数组和文档数组。

##### 5.1.2  体系结构

| 关系型数据库 |  MongoDB数据库  |
| :----------: | :-------------: |
|    数据库    |     数据库      |
|      表      | 集合-Collection |
|      行      |  文档-Document  |

##### 5.1.3  数据模型

| 数据类型      | 描述                                                         | 举例                  |
| ------------- | ------------------------------------------------------------ | --------------------- |
| 字符串        | UTF-8字符串都可以表示为字符串类型的数据                      | {"key":"value"}       |
| 对象id        | 对象id是文档的12字节的唯一ID，一般为_id                      | {"_id":"Object()"}    |
| 布尔值        | true or false                                                | {"key":true}          |
| 数组          | 值的集合或列表                                               | {"key":["a","b","c"]} |
| 64(32)位整数  | 不支持此类型，32位整数会被自动转换为64位浮点数，<br />64位整数可以通过NumberInt/Long来显示 |                       |
| 64位浮点数    | shell中的数字默认为此类型                                    | {"key":"3"}           |
| null          | 空值或未定义的对象                                           | {"key":null}          |
| undefined     | 未定义类型                                                   | {"key":undefined}     |
| 符号          | shell将数据库中的符号类型数据自动转换为字符串                |                       |
| 正则表达式    | 采用JavaScript的正则表达式语法                               | {"key":/foobar/i}     |
| 代码          | JavaScript代码                                               | {"key":function(){}}  |
| 二进制数据    | 任意字节的串组成，shell中无法使用                            |                       |
| 最大值/最小值 | BSON中一个特殊类型，shell中没有                              |                       |

##### 5.1.4  安装

（1） 将MongoDB的bin目录添加至环境变量

（2）运行服务

​		--在data目录下创建db文件夹

​		--在bin目录下运行cmd，执行以下命令

```bash
mongod   --dbpath D:\Tools\Mongodb\data\db
```

​	(3)  配置本地服务

​		--在根目录下创建**mongo.config**文件，并输入以下名下

```bash
dbpath=D:\Tools\Mongodb\data\db
logpath=D:\Tools\Mongodb\data\log\mongo.log
```

​		--在bin目录下运行cmd，执行以下命令

```bash
mongod -dbpath "D:\Tools\Mongodb\data\db" -logpath "D:\Tools\Mongodb\log\mongo.log" -install -serviceName "MongoDB"
```

然后在cmd同过 **net start mongodb** 和 **stop mongodb** 即可开启/关闭服务

### 5.2  基本操作	

```bash
mongo   or   mongo --host=127.0.0.1 --port=27017		--登录
show databases											--显示数据库
```

Example （Student）结构如下：

| 字段名称 | 字段含义 |       类型       |       备注       |
| :------: | :------: | :--------------: | :--------------: |
|   _id    |    ID    | ObjectId或String |   Mongo的主键    |
|  stuid   |   学号   |      String      |                  |
|   name   | 学生姓名 |      String      |                  |
|   age    | 学生年龄 |      int64       |                  |
|   sex    | 学生性别 |      int64       | 0：女生  1：男生 |
|  phone   | 学生电话 |      String      |                  |
| jointime | 入学时间 |       Date       |                  |

##### 5.2.1  数据库操作

```bash
use student									---如果数据库不存在则自动创建,存在则切换至此数据库
show dbs/databases							---查看有权限查看的所有的数据库
db											---查看当前数据库,如果未选择,默认为test
db.dropDatabase()							---删除当前数据库(主要用来删除已经持久化的数据库)

----------------------------------------------------tips----------------------------------------------------
-数据库名可以是满足以下条件的任意UTF-8字符串:
    不能是空字符串("")
    不得含有' '(空格)、.、$、/、\和\0 (空字符)
    应全部小写
    最多64字节
-有一些数据库名是保留的，可以直接访问这些有特殊作用的数据库
	**admin**:从权限的角度来看，这是"root"数据库。要是将一个用户添加到这个数据库，这个用户自动继承所有数据库的权限。一些特定的服务器端命令也只能从这个数据库运行，比如列出所有的数据库或者关闭服务器
	**local**:这个数据永远不会被复制，可以用来存储限于本地单台服务器的任意集合
	**confifig**:当Mongo用于分片设置时，confifig数据库在内部使用，用于保存分片的相关信息
----------------------------------------------------tips----------------------------------------------------
```

##### 5.2.2  集合操作

```bash
db.createCollection("student")					---创建名为 student 的集合	(集合的显式创建)
show collections/table							---查看当前库中的集合(表)

----------------------------------------------------tips----------------------------------------------------
tips:
-集合的命名规范:
	集合名不能是空字符串""
	集合名不能含有\0字符（空字符)，这个字符表示集合名的结尾
	集合名不能以"system."开头，这是为系统集合保留的前缀
	用户创建的集合名字不能含有保留字符
----------------------------------------------------tips----------------------------------------------------

db.student.drop()								---删除名为 student 的集合,返回 true or false
```

##### 5.2.3  文档基本CRUD

```bash
******文档插入******
db.collection.insert({									--单文档插入
		<document or array of documents>,				--要插入到集合中的文档或文档数组(json格式)
		writeConcern: <document>,
        ordered: <boolean>								--true则有序插入,插入出错则返回且不处理后续文档
        												--false则无序插入,插入出错继续处理其他文档
	})
db.student.insert({"stuid":"1001","name":"张三","age":NumberInt(18),"sex":NumberInt(1),
		"phone":"12345","jointime":new Date()})
----------------------------------------------------tips----------------------------------------------------
1.集合不存在则会隐式创建
2.涉及到数字,要分清楚是整数还是浮点数,整数一定要用NumberInt()
3.插入数据无需指定_id,会自动生成
----------------------------------------------------tips----------------------------------------------------
db.collection.insertMany(								--批量插入
		[<doc1>, <doc2>,...],							--要插入到集合中的文档或文档数组(json格式)
    {								
		writeConcern: <document>,
        ordered: <boolean>								--true则有序插入,插入出错则返回且不处理后续文档
        												--false则无序插入,插入出错继续处理其他文档
	})
db.student.insert([{"stuid":"1001","name":"张三","age":NumberInt(18),"sex":NumberInt(1),"phone":"12345","jointime":new Date()},{"stuid":"1002","name":"李四","age":NumberInt(18),"sex":NumberInt(0),"phone":"12346","jointime":new Date()},{"stuid":"1003","name":"王五","age":NumberInt(18),"sex":NumberInt(0),"phone":"12347","jointime":new Date()},{"stuid":"1004","name":"赵六","age":NumberInt(18),"sex":NumberInt(1),"phone":"12348","jointime":new Date()}]);
----------------------------------------------------tips----------------------------------------------------
1.如果某条数据插入失败，将会终止插入，但已经插入成功的数据不会回滚掉
2.因为批量插入由于数据较多容易出现失败，因此，可以使用try catch进行异常捕捉处理
	try{ <插入语句> }catch(e){ print(e); }
----------------------------------------------------tips----------------------------------------------------

******文档查询******
db.collection.find(<query>, [projection])				
                        --query 使用查询运算符指定选择筛选器.若要返回集合中的所有文档,请省略此参数或传递空文档
                        --projection 指定要在与查询筛选器匹配的文档中返回的字段,若要返回匹配文档中所有字段,请省略此参数
                        
1.查询所有
db.student.find()/find({})
db.student.find({stuid:"1001"})							--查询所有 stuid="1001" 的数据
db.student.findOne({stuid:"1001"})						--查询第一条满足 stuid="1001" 的数据
2.投影查询
db.student.find({stuid:"1001"},{name:1,phone:1})		--只返回 _id(默认)、name 和 phone 字段
db.student.find({stuid:"1001"},{name:1,phone:1, _id:0})	--只返回 name 和 phone 字段

******文档更新******
db.collection.update(query, update, options)			--query 更新选择条件 --update 更新内容 --options 选项

1.覆盖修改
db.student.update({stuid:"1001"},{phone:"11111"})		--更新后,除了phone字段,其他字段都不见了
2.局部修改
db.student.update({stuid:"1001"},{$set:{phone:"11111"}}) --利用修改器$set可以实现局部更新
3.批量修改
db.student.update({stuid:"1001"},{$set:{phone:"11111"}}, {multi:true})
4.列值增长上的修改
db.student.update({stuid:"1001"},{$inc:{age:NumberInt(1)}})

******文档删除******
db.collection.remove(条件)

1.db.student.remove({})						--删除全部数据
2.db.student.remove({stuid:"1001"})			--删除满足 stuid="1001" 的数据
```

##### 5.2.4  更多使用方法

[MongoDb 更多使用方法](https://www.aliyundrive.com/s/G6EyLJvNs7b)

### 5.3  整合SpringBoot

##### 5.3.1  配置

添加pom.xml依赖

```xml
<!--        Spring Data MongoDB-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
```

application.properties配置

```properties
# 链接本地 mongodb 数据库
# uri 格式: 协议://用户名:密码 @ IP:端口/数据库名?认证数据库
spring.data.mongodb.uri=mongodb://root:root@localhost:27017/test?authSource=admin
```

##### 5.3.2  使用

**注解说明**

| 注解               | 描述                                                         |
| ------------------ | ------------------------------------------------------------ |
| @Document          | 把一个java类声明为MongoDB的文档，通过collection参数指定这个类对应的文档，标注在实体类上 |
| @Id                | 文档的唯一标识，在mongodb中为ObjectId，唯一的，不可重复，自带索引 |
| @Transient         | 映射忽略的字段，该字段不会保存到mongodb，只作为普通的javaBean属性 |
| @Field             | 映射 mongodb中的字段名，可以不加，不加的话默认以参数名为列名。 |
| @Indexed           | 声明该字段需要索引，建索引可以大大的提高查询效率。           |
| @CompoundIndex     | 复合索引的声明，建复合索引可以有效地提高多字段的查询效率     |
| @GeoSpatialIndexed | 声明该字段为地理信息的索引                                   |
| @DBRef             | 关联另一个document对象。类似于mysql的表关联，但并不一样，mongo不会做级联的操作。 |

**编写实体类**

```java
@Document(collection = "raw_data")
@Data
public class RawDataEntity {

    @Id
    private int id;

    private String rawDataContent;
}
```

**编写接口**

```java
//Spring Data提供了对mongodb数据访问的支持,只需要继承MongoRepository类,按照Spring Data规范即可
@Repository
public interface RawDataReposity extends MongoRepository<RawDataEntity, Integer> {}
```

**进行测试**

```java
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConsumerSaveApplication.class)
public class MyTest {

    @Autowired
    RawDataReposity rawDataReposity;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void test(){
        mongoTemplate.createCollection("raw_data"); //创建集合(显式创建)

        RawDataEntity rawDataEntity = new RawDataEntity();
        rawDataEntity.setId(1);
        rawDataEntity.setRawDataContent("test data");
        System.out.println(rawDataEntity);
        rawDataReposity.save(rawDataEntity);
    }
}
```

  

# 6.  Kafka





# 7.  事务管理

### 7.1  概念

事务是由N步数据库操作序列组成的逻辑执行单元，这系列操作要么全执行，要么全放弃执行。

### 7.2  特性(ACID)

- 原子性：事务是应用中不可再分的最小执行体
- 一致性：事务执行的结果，须使数据从一个一致性状态，变为另一个一致性状态
- 隔离性：各个事务的执行互不干扰，任何事务的内部操作对其他事务都是隔离的
- 持久性：事务一旦提交，对数据所做的任何改变都要记录到永久存储器中

### 7.3  事务的隔离性

**常见并发异常**

- 第一类丢失更新：某一个事务的**导致**另一个事务已更新的数据丢失了
- 第二类丢失更新：某一个事务的**提交**导致另一个事务已更新的数据丢失了
- 脏读：某一个事务读取了另一个事务未提交的数据
- 不可重复读：某一个事务对同一个数据前后读取的结果不一致
- 幻读：某一个事务对同一个表前后查询到的行数不一致

**常见隔离级别**

- Read Uncommitted：读取未提交的数据
- Read Committed：读取已提交的数据
- Repeatable Read：可重复读
- Serializable：串行化

|     隔离级别     |      第一类丢失更新      |           脏读           |      第二类丢失更新      |        不可重复读        |           幻读           |
| :--------------: | :----------------------: | :----------------------: | :----------------------: | :----------------------: | :----------------------: |
| Read Uncommitted | <font color=Red>Y</font> | <font color=Red>Y</font> | <font color=Red>Y</font> | <font color=Red>Y</font> | <font color=Red>Y</font> |
|  Read Committed  |            N             |            N             | <font color=Red>Y</font> | <font color=Red>Y</font> | <font color=Red>Y</font> |
| Repeatable Read  |            N             |            N             |            N             |            N             | <font color=Red>Y</font> |
|   Serializable   |            N             |            N             |            N             |            N             |            N             |

### 7.4  实现机制

- 悲观锁（数据库）

​		--共享锁（S锁）：事务对某数据加了共享锁，其他事务只能对该数据加共享锁，但不能加排他锁

​		--排他锁（X锁）：事务对某数据加了排他锁，其他事务对该数据既不能加共享锁，也不能加排他锁

- 乐观锁（自定义）

​		--版本、时间戳等，在更新数据前查看版本号是否发生变化，变化则取消本次更新，反之则更新

# 8.  AOP

### 8.1  介绍

在软件业，AOP为Aspect Oriented Programming的缩写，意为：**面向切面编程**，通过预编译方式和运行期间动态代理实现程序功能的统一维护的一种技术（编程思想）。AOP是[OOP](https://baike.baidu.com/item/OOP/1152915)的延续，是软件开发中的一个热点，也是Spring框架中的一个重要内容，是函数式编程的一种衍生范型。利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率。

![](E:\VSCode\aop.png)

### 8.2  实现

- AspectJ

  ​	-- 语言级的实现，扩展了Java语言，定义了AOP语法

  ​	-- 在编译期织入代码，有一个专门的编译器，用来生成遵守Java字节码规范的class文件

- Spring AOP

  ​	-- 使用纯Java实现，不需要专门的编译过程，也不需要特殊的类装载器

  ​	-- 在运行时通过代理的方式织入代码，只支持方法类型的连接点

  ​	-- 支持对AspectJ的集成

### 8.3  Spring AOP

- JDK动态代理

  ​	-- Java提供的动态代理技术，可以在运行时创建接口的代理实例

  ​	-- Spring AOP默认使用此方式，在接口的代理实例中织入代码

- CGLib动态代理

  ​	-- 采用底层的字节码技术，在运行时创建子类代理实例

  ​	-- 当目标对象不存在接口时，Spring AOP会采用此种方式，在子类实例中织入代码

### 8.4  使用

```java
package com.lesson.community.aspect;

@Component
@Aspect
public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    // @Pointcut("execution(方法返回类型 项目位置.包名.符合条件类名.符合条件方法名(符合条件参数))")
    @Pointcut("execution(* com.lesson.community.service.*.*(..))")
    public void pointcut(){}	// 初始化切面

    // 方法执行前调用(参数是目标对象)
    @Before("pointcut()")
    public void before(JoinPoint joinPoint){}
    
    @After("pointcut()")
    public void after() {}          // 调用后执行

    @AfterReturning("pointcut()")
    public void afterReturning() {}     // 返回时执行

    @AfterThrowing("pointcut()")
    public void afterThrowing() {}      // 抛出异常时执行

    @Around("pointcut()")               // 调用前后运行
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 调用前执行代码块
        Object obj = joinPoint.proceed();       // 调用目标对象
        // 调用后执行代码块
        return obj;
    }
}
```

