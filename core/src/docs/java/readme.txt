-- maven repository
http://search.maven.org/

-- maven create project
mvn archetype:generate -B -DarchetypeGroupId=org.appfuse.archetypes -DarchetypeArtifactId=appfuse-modular-spring-archetype -DarchetypeVersion=3.0.0 -DgroupId=com.lefeng.core.framework -DartifactId=core-framework -DarchetypeRepository=https://oss.sonatype.org/content/repositories/appfuse

-- 项目格式
core-service
├─src
│  ├─docs           (文档目录)
│  │  └─java
│  │      ├─design           (设计文档)
│  │      ├─plan             (计划文档)
│  │      ├─project_items    (项目文档,格式:序号_日期_子项目名称, 包括子需求,上线文档)
│  │      │      ├─1_20140308_购物车重构
│  │      │      ├─2_20140408_订单确认页加入倒计时功能
│  │      │      └─3_20140508_增加百度支付功能
│  │      ├─reference        (参考文档)
│  │      └─requirement      (需求文档)
│  ├─main
│  │  ├─java
│  │  └─resources
│  │      └─META-INF
│  └─test
│      ├─java
│      └─resources
└─target
    ├─classes
    └─test-classes
    
DAO框架转用Spring Data JPA + Hibernate 4.x，同時增加MyBatis的演示。Tomcat Jdbc数据库连接池取代被批又慢又复杂的DBCP。
MVC框架转用Spring MVC 3，同时演示SiteMesh 2.x，及JSP2.0的直接用JSP编写的taglib。
Restful框架转用Spring MVC + Spring RestTemplate， 同时简单演示CXF实现的Jax-Rs规范。
CSS框架转用Twitter Bootstrap2。
安全框架转用Apache Shiro, 同时加强安全与加密方面的演示。
演示Redis，计有Session、Timer、Scheduler、Master Elector四款。
演示Hibernate Validator验证WebService， 同时加强JQuery Validation Plugin的演示。
演示Ehcache 2.5的RMI集群， 升级Spymemcached2.8的演示 及 Google Guava的JVM内简单Cache。
演示Jolokia 将JMX Restful JSON化。
Logback 替换后来被批评并发时缓慢的Log4j1.2，Logstash做日志的中央式处理。
其他系统特性，包括用Hystrix对访问资源进行并发、延时、短路控制，防止系统雪崩。Monitor and Metrics 包括了Metrics Reporter和Graphite。
对所有Utils类(SpringSide Core, Google Guava, Apache Commons Lange3, Jackson, JodaTime, Dozer...)的演示大升级。
单元测试转用Mockito + PowerMock的Mock组合。
功能测试架构大升级，Spring Profile管理多个运行环境，并演示Selenium2.x
性能测试，使用JMeter发送请求和JavaSimon度量方法调用与延时。
开发时嵌入式Jetty的使用方法大升级， 同时演示基于嵌入式Jetty的可运行War包的打包。
travis-ci集成测试将springside保护起来，并重新制定Sonar的规则。
m2e插件正式接班代替mvn eclipse:eclipse。
删除已过期演示: CXF的ws-security和ws附件协议,Flash Chart,POI, 驗證碼, JMX客户端,DBUnit。

Hystrix，对访问资源进行并发、延时、短路控制，防止系统雪崩。
Logstash，尝试日志的中央式处理。
JMeter，对Showcase进行性能测试，JavaSimon，在性能测试中监控应用方法调用时间。
修正性能测试中发现Restful Service的一些Bug。
Tomcat Maven插件，运行Tomcat，以及打包包含嵌入式Tomcat的可运行包。
将springside-test合并到springside-core与springside-extension中，解决springside-test与springside-core的循环依赖。    
-- 展示层(JSP | 建议使用SPRING MVC)

Spring MVC 升级3.2后对Restful的支持更好，比如更好的异常处理。
eg:
@Controller
@RequestMapping(value = "/account/user")
public class UserController {

    private AccountManager accountManager;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        List<User> users = accountManager.getAllUser();
        model.addAttribute("users", users);
        return "account/userList";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("user", new User());
        return "account/userForm";
        }

    @RequestMapping(value = "create",method = RequestMethod.POST)
    public String create(User user, RedirectAttributes redirectAttributes) {
        accountService.saveUser(user);
        redirectAttributes.addFlashAttribute("message", "Create user success");
        return "redirect:/account/user/";
    }
        ...............
}

-- spring profile

用Profile 多环境配置
Test, Dev, Production环境，搞几个只有细微区别的application.xml,
Spring 3.1的功能，以后就不用为了区分Test, Dev, Production环境，搞几个只有细微区别的application.xml, application-test.xml及引用它们的web.xml了。

AOP陷阱
一般用基于CGI-Lib的AOP<tx:annotation-driven proxy-target-class="true" />， 那如果用到<aop:aspectj-autoproxy/>也应该加上proxy-target-class="true"，另外shiro的方法级AOP用到的DefaultAdvisorAutoProxyCreator也同样需要设置target-class属性为true。


首先，将applicationContext.xml中的namespace从3.0升级到3.1.xsd， 然后就可以在文件末尾加入不同环境的定义，比如不同的dataSource
<beans profile="test">
    <jdbc:embedded-database id="dataSource">
        <jdbc:script location="classpath:com/bank/config/sql/schema.sql"/>
    </jdbc:embedded-database>
</beans>

<beans profile="production">
    <jee:jndi-lookup id="dataSource" jndi-name="java:comp/env/jdbc/datasource"/>
</beans>
2.在web.xml里，你需要定义使用的profile，最聪明的做法是定义成context-param，注意这里定义的是default值，在非生产环境，可以用系统变量"spring.profiles.active"进行覆盖。

    <context-param>
            <param-name>spring.profiles.default</param-name>
            <param-value>production</param-value>
    </context-param>
3.在其他地方进行覆盖

3.1 在development和functional test启动Jetty前设置系统变量

    System.setProperty("spring.profiles.active", "development");
    server.start()
3.2 在用到ApplicationContext的单元测试用例中，用 @ActiveProfiles定义

    @ContextConfiguration(locations = { "/applicationContext.xml" })
    @ActiveProfiles("test")
    public class AccountDaoTest extends SpringTxTestCase {
    }
在springside里有演示了production,development，test,functional三个环境， 大家可以根据实际情况组合自己的环境管理。另外可以与Spring的properties文件加载时可顺位覆盖的特性(放一些不在版本管理中的xx.local.properties文件)，更好的支持本地开发环境，Jenkins上的functional test等其他环境。


-- spring propagation
 *  PROPAGATION_REQUIRED：支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。<br>
 * PROPAGATION_SUPPORTS：支持当前事务，如果当前没有事务，就以非事务方式执行。<br>
 * PROPAGATION_MANDATORY：支持当前事务，如果当前没有事务，就抛出异常。<br>
 * PROPAGATION_REQUIRES_NEW：新建事务，如果当前存在事务，把当前事务挂起。<br>
 * PROPAGATION_NOT_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。<br>
 * PROPAGATION_NEVER：以非事务方式执行，如果当前存在事务，则抛出异常。<br>
 * PROPAGATION_NESTED：支持当前事务，如果当前事务存在，则执行一个嵌套事务，如果当前没有事务，就新建一个事务。
 * 嵌套事务实现了隔离机制，例如B事务嵌套在A事务中，B失败不会影响A提交。而PROPAGATION_REQUIRED则全部回滚<br>

-- redis
Key 不能太长，比如1024字节，但antirez也不喜欢太短如"u:1000:pwd"，要表达清楚意思才好。他私人建议用":"分隔域，用"."作为单词间的连接，如"comment:12345:reply.to"。

SetNx， 仅当key不存在时才Set。可以用来选举Master或做分布式锁：所有Client不断尝试使用SetNx master myName抢注Master，成功的那位不断使用Expire刷新它的过期时间。如果Master倒掉了key就会失效，剩下的节点又会发生新一轮抢夺。

GetBit/SetBit/BitOp,与或非/BitCount， BitMap的玩法，比如统计今天的独立访问用户数时，每个注册用户都有一个offset，他今天进来的话就把他那个位设为1，用BitCount就可以得出今天的总人数。

Append/SetRange/GetRange/StrLen，对文本进行扩展、替换、截取和求长度，只对特定数据格式如字段定长的有用，json就没什么用。

Hash

Key-HashMap结构，相比String类型将这整个对象持久化成JSON格式，Hash将对象的各个属性存入Map里，可以只读取/更新对象的某些属性。这样有些属性超长就让它一边呆着不动，另外不同的模块可以只更新自己关心的属性而不会互相并发覆盖冲突。

另一个用法是土法建索引。比如User对象，除了id有时还要按name来查询。可以有如下的数据记录:

(String) user:101 -> {"id":101,"name":"calvin"...}
(String) user:102 -> {"id":102,"name":"kevin"...}
(Hash) user:name:index-> "calvin"->101, "kevin" -> 102
底层实现是hash table，一般操作复杂度是O(1)，要同时操作多个field时就是O(N)，N是field的数量。

List

List是一个双向链表，支持双向的Pop/Push，江湖规矩一般从左端Push，右端Pop——LPush/RPop，而且还有Blocking的版本BLPop/BRPop，客户端可以阻塞在那直到有消息到来，所有操作都是O(1)的好孩子，可以当Message Queue来用。当多个Client并发阻塞等待，有消息入列时谁先被阻塞谁先被服务。任务队列系统Resque是其典型应用。

还有RPopLPush/ BRPopLPush，弹出来返回给client的同时，把自己又推入另一个list，LLen获取列表的长度。

还有按值进行的操作：LRem(按值删除元素)、LInsert(插在某个值的元素的前后)，复杂度是O(N)，N是List长度，因为List的值不唯一，所以要遍历全部元素，而Set只要O(log(N))。

按下标进行的操作：下标从0开始，队列从左到右算，下标为负数时则从右到左。

LSet ，按下标设置元素值。
LIndex，按下标返回元素。
LRange，不同于POP直接弹走元素，只是返回列表内一段下标的元素，是分页的最爱。
LTrim，限制List的大小，比如只保留最新的20条消息。
复杂度也是O(N)，其中LSet的N是List长度，LIndex的N是下标的值，LRange的N是start的值+列出元素的个数，因为是链表而不是数组，所以按下标访问其实要遍历链表，除非下标正好是队头和队尾。LTrim的N是移除元素的个数。

在消息队列中，并没有JMS的ack机制，如果消费者把job给Pop走了又没处理完就死机了怎么办？

解决方法之一是加多一个sorted set，分发的时候同时发到list与sorted set，以分发时间为score，用户把job做完了之后要用ZREM消掉sorted set里的job，并且定时从sorted set中取出超时没有完成的任务，重新放回list。
另一个做法是为每个worker多加一个的list，弹出任务时改用RPopLPush，将job同时放到worker自己的list中，完成时用LREM消掉。如果集群管理(如zookeeper)发现worker已经挂掉，就将worker的list内容重新放回主list。

Set

Set就是Set，可以将重复的元素随便放入而Set会自动去重，底层实现也是hash table。
SAdd/SRem/SIsMember/SCard/SMove/SMembers，各种标准操作。除了SMembers都是O(1)。
SInter/SInterStore/SUnion/SUnionStore/SDiff/SDiffStore，各种集合操作。交集运算可以用来显示在线好友(在线用户 交集 好友列表)，共同关注(两个用户的关注列表的交集)。O(N)，并集和差集的N是集合大小之和，交集的N是小的那个集合的大小*2。

事务
用Multi(Start Transaction)、Exec(Commit)、Discard(Rollback)实现。 在事务提交前，不会执行任何指令，只会把它们存到一个队列里，不影响其他客户端的操作。在事务提交时，批量执行所有指令。《Redis设计与实现》中的详述。

注意，Redis里的事务，与我们平时的事务概念很不一样：

它仅仅是保证事务里的操作会被连续独占的执行。因为是单线程架构，在执行完事务内所有指令前是不可能再去同时执行其他客户端的请求的。
它没有隔离级别的概念，因为事务提交前任何指令都不会被实际执行，也就不存在"事务内的查询要看到事务里的更新，在事务外查询不能看到"这个让人万分头痛的问题。
它不保证原子性——所有指令同时成功或同时失败，只有决定是否开始执行全部指令的能力，没有执行到一半进行回滚的能力。在redis里失败分两种，一种是明显的指令错误，比如指令名拼错，指令参数个数不对，在2.6版中全部指令都不会执行。另一种是隐含的，比如在事务里，第一句是SET foo bar， 第二句是LLEN foo，对第一句产生的String类型的key执行LLEN会失败，但这种错误只有在指令运行后才能发现，这时候第一句成功，第二句失败。还有，如果事务执行到一半redis被KILL，已经执行的指令同样也不会被回滚。
Watch指令，类似乐观锁，事务提交时，如果Key的值已被别的客户端改变，比如某个list已被别的客户端push/pop过了，整个事务队列都不会被执行。

测试结果

测试环境： RHEL 6.3 / HP Gen8 Server/ 2 * Intel Xeon 2.00GHz(6 core) / 64G DDR3 memory / 300G RAID-1 SATA / 1 master(writ AOF), 1 slave(write AOF & RDB)
数据准备： 预加载两千万条数据，占用10G内存。
测试工具：自带的redis-benchmark，默认只是基于一个很小的数据集进行测试，调整命令行参数如下，就可以开100条线程(默认50)，SET 1千万次(key在0-1千万间随机)，key长21字节，value长256字节的数据。
redis-benchmark -t SET -c 100 -n 10000000 -r 10000000 -d 256 
测试结果(TPS)： 1.SET：4.5万， 2.GET：6万 ，3.INCR：6万，4.真实混合场景: 2.5万SET & 3万GET
单条客户端线程时6千TPS，50与100条客户端线程差别不大，200条时会略多。
Get/Set操作，经过了LAN，延时也只有1毫秒左右，可以反复放心调用，不用像调用REST接口和访问数据库那样，每多一次外部访问都心痛。
资源监控:
1.CPU: 占了一个处理器的100%，总CPU是4%(因为总共有2CPU*6核*超线程 = 24个处理器)，可见单线程下单处理器的能力是瓶颈。 AOF rewrite时另一个处理器占用50-70%。
2.网卡：15-20 MB/s receive, 3Mb/s send(no slave) or 15-20 MB/s send (with slave) 。当把value长度加到4K时，receive 99MB/s，已经到达千兆网卡的瓶颈，TPS降到2万。
3.硬盘：15MB/s(AOF append), 100MB/s(AOF rewrite/AOF load，普通硬盘的瓶颈)，

-- spring 注入配置？

用@Autowired还是@Resource?

@Autowired是Spring自己定义的，按属性的Class注入， @Resource是JSR规范，按属性的名称注入，个人更喜欢用前者，因为@Autowired的耦合度更低，而且还有一个required=true/false的有用属性。


-- 持久层(Hibernate, JdbcTemplate, 建议使用Ibatis)


HttpClient4.3 Fluent API

HttpClient原来的API非常复杂，而且还要记着关闭InputStream，Http4.3终于提供了Fluent API, 同样在showcase的RemoteContentServlet中演示。

Request.Get(url).execute().returnContent().asString();
通过翻代码，可以看到它线程安全，所有请求会使用一个公共的连接池，总共200连接，每个destination最多100个连接。而且内容会立刻全部读出然后关闭inputsream，不需要再用代码去关闭。

如果你想设置自己的连接池，或者设置超时，则需要先设置好httpClient，然后传入。

Executor executor = Executor.newInstance(httpClient);
String resultString = executor.execute(Request.Get(url)).returnContent().asString();
3. JDK HttpConnectionURL

同样在showcase的RemoteContentServlet中演示。

缺陷是长连接只能JVM全局统一配置， 系统变量 http.keepAlive默认为true，http.maxConnections默认为5，是每个destination 的最大连接数。

-- 缓存
eccache:
参考 http://ehcache.org/ehcache.xml ,配置中有一些default值可以直接采用的，就没有在配置文件里重复配置， 减少眼睛疲劳。
<!-- spring对ehcache的缓存工厂支持 -->
    <bean id="ehCacheManagerFactory" class="org.springframework.cache.ehcache.EhCacheManagerFactoryBean">
        <property name="configLocation" value="classpath:ehcache.xml" />
        <property name="acceptExisting" value="true" />
    </bean>
<!-- spring对ehcache的缓存管理 -->
    <bean id="ehCacheManager" class="org.springframework.cache.ehcache.EhCacheCacheManager">
        <property name="cacheManager" ref="ehCacheManagerFactory"></property>
    </bean>
    
    <!-- 使用缓存annotation 配置 -->
    <cache:annotation-driven cache-manager="ehCacheManager" proxy-target-class="true" />    
memoryStoreEvictionPolicy，默认为LRU。
diskPersistent, 默认为false。
diskExpiryThreadIntervalSeconds, 默认为120秒。
diskSpoolBufferSizeMB, 默认为30Mb。
statistics,默认为false。

-- 数据库
数据库设计的一般性原则

主键的列名统一为id。
为方便数据操作及维护，不建立任何外键，用程序去保证关联关系。
为表名添加前缀以便日后管理。比如有几十个表，将联系比较紧密的表，使用相同的前缀。
表名全小写，因为MySQL在Linux下默认区分表名大小写。

Complete Reference

Web

Spring MVC, JSP/JSTL/Servlet
JQuery and plugins, Ajax, Twitter Bootstrap CSS
SiteMesh, YUICompressor
WebService

Spring Restful Service, JAX-WS/CXF
Http Client
Data

Spring Data JPA, JPA/Hibernate, MyBatis
DataBases Overview, H2 Database, DataSource, Transaction
NOSQL, Redis
Cache Overview, Guava Cache, Ehcache, Memcached
Service

Shiro Security, Crypto
Schedule/Quartz
JMS/ActiveMQ
JMX
System Protection, Hystrix, Rate Limiter
Monitoring and Metrics, Metrics Library, Graphite
Logging/Slf4j/Logback, Centralized Logging/Logstash
General Library

Spring
Validation Overview, JQuery Validation, Hibernate Validator
General Utilizes, JSON/JAXB, Date, Email
Test

Test Overview, Unit Test/Mockito/AssertJ, Selenium2, BDD
Performance Test Overview, JMeter, Profiler
Simulator Overview, Node.js Simulator
Environment

Eclipse, Maven, Sonar, Git, Travis CI
Jetty, Micro-Service Architecture/Executable War

-- Guava 工具
Guava 是一个 Google 的基于java1.6的类库集合的扩展项目，包括 collections, caching, primitives support, concurrency libraries, common annotations, string processing, I/O, 等等. 这些高质量的 API 可以使你的JAVa代码更加优雅，更加简洁，让你工作更加轻松愉悦。下面我们就开启优雅Java编程学习之旅！

　　项目相关信息：

　　官方首页：http://code.google.com/p/guava-libraries
　　官方下载：http://code.google.com/p/guava-libraries/downloads/list
　　官方文档：http://docs.guava-libraries.googlecode.com/git/javadoc
                    http://www.ostools.net/apidocs/apidoc?api=guava

　　源码包的简单说明： 
　　com.google.common.annotations：普通注解类型。 
　　com.google.common.base：基本工具类库和接口。 
　　com.google.common.cache：缓存工具包，非常简单易用且功能强大的JVM内缓存。 
　　com.google.common.collect：带泛型的集合接口扩展和实现，以及工具类，这里你会发现很多好玩的集合。 
　　com.google.common.eventbus：发布订阅风格的事件总线。 
　　com.google.common.hash： 哈希工具包。 
　　com.google.common.io：I/O工具包。 
　　com.google.common.math：原始算术类型和超大数的运算工具包。 
　　com.google.common.net：网络工具包。 
　　com.google.common.primitives：八种原始类型和无符号类型的静态工具包。 
　　com.google.common.reflect：反射工具包。 
　　com.google.common.util.concurrent：多线程工具包。

　　类库使用手册：

　　一.  基本工具类：让使用Java语言更令人愉悦。

　　1. 使用和避免 null：null 有语言歧义， 会产生令人费解的错误， 反正他总是让人不爽。很多 Guava 的工具类在遇到 null 时会直接拒绝或出错，而不是默默地接受他们。
　　2. 前提条件：更容易的对你的方法进行前提条件的测试。
　　3. 常见的对象方法： 简化了Object常用方法的实现， 如 hashCode() 和 toString()。
　　4. 排序： Guava 强大的 "fluent Comparator"比较器， 提供多关键字排序。
　　5. Throwable类： 简化了异常检查和错误传播。

　　二.  集合类：集合类库是 Guava 对 JDK 集合类的扩展， 这是 Guava 项目最完善和为人所知的部分。

　　1. Immutable collections（不变的集合）： 防御性编程， 不可修改的集合，并且提高了效率。
　　2. New collection types(新集合类型)：JDK collections 没有的一些集合类型，主要有：multisets，multimaps，tables， bidirectional maps等等
　　3. Powerful collection utilities（强大的集合工具类）： java.util.Collections 中未包含的常用操作工具类
　　4. Extension utilities（扩展工具类）: 给 Collection 对象添加一个装饰器? 实现迭代器? 我们可以更容易使用这些方法。

　　三.  缓存: 本地缓存，可以很方便的操作缓存对象，并且支持各种缓存失效行为模式。

　　四.  Functional idioms（函数式）: 简洁, Guava实现了Java的函数式编程，可以显著简化代码。

　　五. Concurrency（并发）：强大,简单的抽象,让我们更容易实现简单正确的并发性代码。

　　1. ListenableFuture（可监听的Future）: Futures,用于异步完成的回调。
　　2. Service: 控制事件的启动和关闭，为你管理复杂的状态逻辑。

　　六. Strings: 一个非常非常有用的字符串工具类: 提供 splitting，joining， padding 等操作。

　　七. Primitives: 扩展 JDK 中未提供的对原生类型（如int、char等）的操作， 包括某些类型的无符号的变量。

　　八. Ranges: Guava 一个强大的 API，提供 Comparable 类型的范围处理， 包括连续和离散的情况。

　　九. I/O: 简化 I/O 操作, 特别是对 I/O 流和文件的操作, for Java 5 and 6.

　　十. Hashing: 提供比 Object.hashCode() 更复杂的 hash 方法, 提供 Bloom filters.

　　十一. EventBus: 基于发布-订阅模式的组件通信，但是不需要明确地注册在委托对象中。

　　十二. Math: 优化的 math 工具类，经过完整测试。

　　十三. Reflection: Guava 的 Java 反射机制工具类。