#simple-mvc-java框架

##框架说明 
    系统架构Spring + SpringMVC + Mybatis 并使用全java配置，废弃了传统的xml配置，让代码更简洁。 
    消息队列：Redis、Spring、ActiveMQ
    版本号:v1.2.0 2019/05/02

##框架技术 
    开发工具（IDE）:IntelliJ IDEA 2017.1.5 
    核心库：Spring-4.3.18.RELEASE 
            Spring MVC-4.3.10.RELEASE 
            mybatis-spring:1.3.2 
            mybatis-3.4.6.jar 
            mysql-connector:6.0.6
            ActiveMQ 2.7.0


##配置方式 
    全局使用java代码进行配置，配置文件包含： 
        Bootstrap.class -> 系统核心启动文件，类似于web.xml 
        RootContextConfiguration.class -> 框架核心配置文件 
        DispatcherContextConfiguration.class -> 容器核心配置文件
        MybatisConfiguration.class -> mybatis数据库配置文件 
        ViewResolverConfiguration.class -> WebMVC视图配置文件 
        WebMvcAdapterConfiguration.class -> WebMVC适配器配置文件 
        AsyncConfiguration.class -> 异步&计划任务配置文件

    自定义配置文件：
        可新建一个configuration配置文件类，保存在config或自定义，标注:@Configuration 即可。
        
##控制器(例子) 
    通常的方法： @RestController public class ATestController{...}

    可以继承默认Restful接口：
    @RestController
    public class ATestController implements IDefaultControllerMethod<User> {...}
    
    建议使用继承基础父类
    @RestController
    public class ATestController extends BasicApiController implements IDefaultControllerMethod<User> {...}

##视图控制器(例子) 
```
    @Controller 
    public class DownloadViewController { 
        @RequestMapping(value = "/download/index",method = RequestMethod.GET) 
        public String index(HttpServletRequest request){ 
            //Redirect to /WEB-INF/jsp/views/Download/index.jsp return "Download/index"; 
        } 
    }
```

##表单验证 
    需要在model类中标注对应的验证注解（system/annotation）如@Mobile;

    接着在Contorller使用@RequestBody自动装箱请求参数，例子：
    @Autowired
    private ValidatorProvider<User> validatorProvider;
    
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResultResp<Map<String, Object>> _doGet(@RequestBody User user, HttpServletRequest request) {
        ResultResp<Map<String, Object>> resp = new ResultResp<>();
    
        try {
            validatorProvider.validate(user);
            resp.setInfo("The params is valid.");
        } catch (IllegalValidateException e) {
            e.printStackTrace();
            resp.setCode(e.getCode());
            resp.setInfo(e.getMessage());
        }
        return resp;
    }
    
##本地化(默认:Constant.defaultLocale:zh_CN) 
    框架集成了本地化功能，接口请求的Url地址如果传递，如：locale=en_US，则优先使用Url传递的语言请求，默认读取客户端系统本地化语言。 注：locale 为语言key值参数，en_US为语言名称。其中语言文件保存在/lang目录中，en_US对应其文件夹名称；

    en_US目录解析：
        errors.properties 错误提示语言信息
        lang.properties 信息提示语言信息

    读取语言信息：/system/core/Application.java->getLang(String name,String type):83  
    @param name 为errors.properties|lang.properties文件中的key值。
    @param type lang|errors|null
    
    默认会获取http请求过来的locale值,如果没有则读取:
    request.getLocale().getLanguage() + "_" + request.getLocale().getCountry();
    Application.getInstance(null).getLang(Lang String);
    
    注：本次框架并没有使用本地化规则配置。