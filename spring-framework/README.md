# spring-framework 源码解析


## IOC
> IOC概念：控制反转（Inversion of Control）  
* 控制反正的理解：比如：BookService和ItemService操作数据库时都需要使用Datasource，这时我们需要在这个两个服务类创建的时候分别实例化Datasource并分别将这个类注入到服务类中  
* 利用spring的IOC容器后，spring容器自动创建BookService、ItemService、Datasource类，然后由spring容器在初始化BookService和ItemService时自动地注入Datasource  
* 此时控制权发生了反转，从以前类的创建和依赖由应用程序控制变为了由spring的IOC容器控制  
> 作用：创建和管理java中的object对象以及处理object对象中的依赖关系
+ spring是如何为应用程序创建object类的？ 
    - java中创建对象的方式：new Object()、class.getDeclaredConstructor(Class<?>... parameterTypes).newInstance(Object ... )
    - spring为应用程序创建对象是通过：class.getDeclaredConstructor(Class<?>... parameterTypes).newInstance(Object ... )
+ spring通过那些'描述信息'就能创建和初始化object类？
    + 比如：创建object需要的基本描述信息：object类全限定名class、初始化object类需要的基本描述信息：属性名和属性值（key-value）、object的依赖
    + '描述信息'：BeanDefinition
        - BeanDefinition.getBeanClassName()：获取全限定名
        + BeanDefinition extends AttributeAccessor 
            - AttributeAccessor.getAttribute(name)根据属性名获取构造属性值
        + BeanDefinition extends BeanMetadataElement
            - BeanMetadataElement.getSource()获取属性源
        - BeanDefinition.getDependsOn()：bean依赖的object，体现在code中：类的属性名
+ spring容器是如何读取描述信息（BeanDefinition）的？
  + spring是通过BeanDefinitionReader接口来读取object的描述信息的。
    - BeanDefinitionReader.getRegistry()获取BeanDefinition的注册器（保存描述信息的寄存器）
    - BeanDefinitionReader.getResourceLoader()资源加载器（加载描述信息的器）
    - BeanDefinitionReader.getBeanClassLoader()类加载器（描述信息创建object时的类加载器）
    - BeanDefinitionReader.loadBeanDefinitions(Resource... resource)加载描述信息（加载描述信息的流程）
    - BeanDefinitionReader.loadBeanDefinitions(String... locations)加载描述信息（加载描述信息的流程）
+ spring将这些读取到的'描述信息'BeanDefinition保存在哪？
    + spring是通过BeanDefinitionRegistry接口来保存读取到的BeanDefinition
        - BeanDefinitionRegistry.registerBeanDefinition(beanName, beanDefinition)保存beanName-beanDefinition
        - BeanDefinition extends AliasRegistry
          + AliasRegistry别名注册器，由于有可能一个BeanDefinition的名称会很长，这是可以使用简短的别名来代表这个BeanDefinition，案例：java.util.concurrent并发包，开发人员简称juc方便记忆
          + AliasRegistry.registerAlias(String name, String alias)保存name-alias，故一个BeanDefinition有多个别名
+ spring是如何存储已经创建好了object类的？
    + spring内部是通过DefaultSingletonBeanRegistry实体类来存储bean的
    + spring通过BeanFactory工厂接口来为应用程序获取创建好的object类
        - BeanFactory.getBean()获取创建好了的类
        - BeanFactory.getBeanProvider()bean的提供器，另一种方式获取bean的接口
        - BeanFactory.isTypeMatch(name, typeToMatch)类型匹配
        - BeanFactory.getAliases()bean的别名
        - BeanFactory.getType(name)获取bean的class
        - BeanFactory.containsBean(name)是否包含指定的bean
+ spring是如何为应用程序提供修改spring容器中的BeanFactory提供可扩展性的
    - 通过BeanFactoryPostProcessors来提供扩展性的
+ spring是如何为应用程序的bean提供个性化扩展的？
    - spring通过bean的生命周期的方式来为程序提供个性化方式的扩展（个人理解）
    - bean的生命周期：bean在IOC容器中的创建、使用、销毁整个流程（个人理解），其实就是人为地添加了一些钩子方法方便程序扩展
    - bean.反射实例化（在堆中新建内存空间）、XxxAware.setXxx(Xxx xxx)一系列接口、BeanPostProcessors.postProcessBeforeInitialization()、InitializingBean.afterPropertiesSet()、custom.initMethod()、BeanPostProcessors.postProcessAfterInitialization()、DestructionAwareBeanPostProcessors.postProcessBeforeDestruction()、DisposableBean.destroy()、custom.destroy()
>接口主要实现类
1. AbstractBeanDefinition抽象类实现了BeanDefinition接口中的方法，且提供了两个主要继承类：
    + GenericBeanDefinition
    + RootBeanDefinition
2. AbstractBeanDefinitionReader抽象类实现了BeanDefinitionReader接口中的方法，提供了三个主要继承类
    + PropertiesBeanDefinitionReader已弃用
    + XmlBeanDefinitionReader
    + GroovyBeanDefinitionReader
3. DefaultListBeanFactory实现了BeanDefinitionRegistry接口，重要的几个属性：
    + private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    + private volatile List<String> beanDefinitionNames = new ArrayList<>(256);
4. DefaultSingletonBeanRegistry实现了保存Bean的功能，重要的几个属性：
    + private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);一级缓存（网上说法），存储初始化完成的完整Bean
    + private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);三级缓存（网上说法），存储能获取到实例化但未初始化的bean函数式接口
    + private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);二级缓存（网上说法），存储实例化但未初始化的Bean
5. AbstractBeanFactory抽象类implement BeanFactory接口实现了getBean()方法，并实际实现是通过doGetBean()来实现，在doGetBean()通过getSingleton()来获取Bean
6. AbstractAutowireCapableBeanFactory extends AbstractBeanFactory类中getSingleton(beanName, singletonFactory)->singletonFactory.getObject()->createBean(beanName, mbd, args)->doCreateBean()->populate()->initializeBean()->addSingleton(beanName, singletonObject)将Bean放入一级缓存
> 依赖包：spring-beans、spring-core、spring-context-support、spring-context  
>主要类图
![AbstractBeanDefinition.png](src\main\resources\images\AbstractBeanDefinition.png)<br/>
![AbstractBeanDefinitionReader.png](src\main\resources\images\AbstractBeanDefinitionReader.png)<br/>
![DefaultListableBeanFactory.png](src\main\resources\images\DefaultListableBeanFactory.png)<br/>
![AbstractApplication.png](src\main\resources\images\AbstractApplicationContext.png)<br/>
![AbstractEnvironment.png](src\main\resources\images\AbstractEnvironment.png)<br/>
![MutablePropertySources.png](src\main\resources\images\MutablePropertySources.png)<br/>
![PropertySourcesPropertyResolver.png](src\main\resources\images\PropertySourcesPropertyResolver.png)<br/>
![ResourcePropertySource.png](src\main\resources\images\ResourcePropertySource.png)<br/>
![PlaceholderConfigurerSupport.png](src\main\resources\images\PlaceholderConfigurerSupport.png)<br/>
![InstantiationAwareBeanPostProcessor.png](src\main\resources\images\InstantiationAwareBeanPostProcessor.png)<br/>
![InitDestroyAnnotationBeanPostProcessor.png](src\main\resources\images\InitDestroyAnnotationBeanPostProcessor.png)<br/>

### 创建
#### xml启动源码分析
####构造器过程分析
1.spring容器读取和解析资源文件（xxx-${variable}.xml）和注册BeanDefinition
* spring容器获取与处理application.xml资源文件（），疑问点：如果用户给出的xml文件path路径中含有占位符，此时spring是如何处理的？
  * 如果由我来设计，我会如此处理占位符:
    * 初始化一个资源池，资源池的来源：环境变量或系统参数
    * 初始化一个占位符处理器，该占位符处理器功能：找出占位符并将占位符替换成原始值，则该占位符必须有的属性：占位符表达式、资源池
    * 将配置文件路径交给占位符处理器
  * spring的设计：
    * 在构造器中调用setConfigLocations(configLocations)完成资源路径的设置
      * 请求链：setConfigLocations()->resolvePath()->getEnvironment()初始化AbstractEnvironment->AbstractEnvironment.resolvePlaceholders()->PropertySourcesPropertyResolver.resolvePlaceholders()
      * 初始AbstractEnvironment时会初始化如下组件：
        * 初始化资源池：MutablePropertySources.java类似于java中的集合对象拥有各种PropertySource资源
        * 初始化占位符处理器：PropertySourcesPropertyResolver.java
* spring容器读取资源文件并解析文件
  * 1.初始化BeanDefinitionRegister之前，先做一些准备工作prepareRefresh()：比如设置BeanDefinitionRegister启动标识为true和设置关闭标识为false、初始化资源池和判断程序运行必须满足的状态是否已满足、初始化存放事件的容器
  * 2.创建BeanDefinitionRegister容器，refreshBeanFactory()->createBeanFactory()：先判断BeanDefinitionRegister容器是否已存在，若存在则销毁，再创建DefaultListableBeanFactory即：BeanDefinitionRegister注册器
  * 3.读取并解析xxx.xml文件，并将解析好的对象（BeanDefinition）放入创建好的BeanDefinitionRegister容器中：loadBeanDefinitions(DefaultListableBeanFactory beanFactory)
    * 1.读取过程：
      * 1.实例化初始化XmlBeanDefinitionReader对象，初始化属性BeanDefinitionRegister、Environment环境资源池（程序运行时外界变量）、资源加载器（ResourceLoader）、xml文件校验实体
      * 2.调用XmlBeanDefinitionReader.loadBeanDefinitions(configLocations)完成资源的加载
      * 3.完整的读取过程调用链：loadBeanDefinitions(XmlBeanDefinitionReader)->XmlBeanDefinitionReader.loadBeanDefinitions(String... configLocations)->ResourceLoader.getResource(configLocations)->DefaultResourceLoader.getResource(locationPattern)->new ClassPathContextResource()
    * 2.解析过程：
      * 1.根据Resource获取资源文件流->初始化一个Document对象->初始化一个DocumentReader对象->初始化一个XmlReaderContext对象，含有资源Reasource、BeanDefinitionRegister->并将XmlReaderContext放入DocumentReader中->DocumentReader.doRegisterBeanDefinitions()完成解析动作并注册
      * 2.流程XmlBeanDefinitionReader.loadBeanDefinitions(Resource)->doLoadBeanDefinitions(InputSource inputSource, Resource resource)->doLoadDocument(inputSource, resource)->registerBeanDefinitions(doc, resource)->registerBeanDefinitions(Document doc, XmlReaderContext readerContext)->doRegisterBeanDefinitions(Element root)->parseBeanDefinitions(Element root, BeanDefinitionParserDelegate delegate)
      * 3.若root是一个默认的命名空间：Beans->parseDefaultElement(Element ele, BeanDefinitionParserDelegate delegate)->importBeanDefinitionResource(ele),processAliasRegistration(ele),processBeanDefinition(ele, delegate)->delegate.parseBeanDefinitionElement(ele)->registerBeanDefinition()
      * 4.若root不是一个默认的命名空间：delegate.parseCustomElement(ele)->parseCustomElement(Element ele, @Nullable BeanDefinition containingBd)->DefaultNamespaceHandlerResolver.getNamespaceURI(ele)->DefaultNamespaceHandlerResolver.resolve(namespaceURI)->NamespaceHandler.parse(Element element, ParserContext parserContext)
      * 5.自主扩展xml标签流程
        * 1.在META-INF/spring.schemas添加网络.xsd-本地.xsd映射关系，例如：https\://www.springframework.org/schema/context/spring-context-2.5.xsd=org/springframework/context/config/spring-context.xsd
        * 2.编写NamespaceHandler实现类或者继承NamespaceHandlerSupport，添加标签属性解析器（BeanDefinitionParser）用于解析标签中的属性
        * 3.在META-INF/spring.handlers中添加NamespaceHandler处理器
        * 4.例如：配置文件中有：<context:component-scan base-package="com.github.springframework.ioc.xml" />
          * 1.spring容器先扫描META-INF/spring.schemas获取或标签的规范信息
          * 2.读取META-INF/spring.handlers获取.xsd对应的NamespaceHandler处理器：ContextNamespaceHandler
          * 3.解析context标签，根据context标签的属性获取对应的标签处理器，例如：component-scan属性，则启用：ComponentScanBeanDefinitionParser
          * 4.调用ComponentScanBeanDefinitionParser.parse()方法解析标签
          * 5.执行parse()时，spring容器会扫描base-package包下所有被spring注解标签的类将其解析成一个个BeanDefinition，然后将其放入BeanDefinitionRegister中
            * 此过程通过：ClassPathBeanDefinitionScanner组件来完成，通过：ClassPathBeanDefinitionScanner.doScan("package-name")->findCandidateComponents()来完成包下加载
          * 6.扫描完base-package下的类后，spring还会额外地注入：BeanDefinition：ConfigurationClassPostProcessor(BeanDefinitionRegistryPostProcessor)、CommonAnnotationBeanPostProcessor(JSR-250 support)、AutowiredAnnotationBeanPostProcessor、PersistenceAnnotationBeanPostProcessor(JPA support)、EventListenerMethodProcessor、DefaultEventListenerFactory
        * 5.例如：配置文件中有：<context:property-placeholder location="classpath:student.xml" />
          * 1.spring容器先扫描META-INF/spring.schemas获取或标签的规范信息
          * 2.读取META-INF/spring.handlers获取.xsd对应的NamespaceHandler处理器：ContextNamespaceHandler
          * 3.解析context标签，根据context标签的属性获取对应的标签处理器，例如：property-placeholder属性，则启用：PropertyPlaceholderBeanDefinitionParser
          * 4.执行parse()->parseInternal()->getBeanClass()来注册一个BeanDefinition：PropertySourcesPlaceholderConfigurer(完成类中属性占位符的替换)
2. spring通过BeanFactoryPostProcessor来为应用程序提供扩展BeanFactory
* 通过BeanFactoryPostProcessor可以做到什么？
  * 1.无需配置xml，通过代码的方式往IOC容器中注入BeanDefinition
    * 1.实现：BeanDefinitionRegisterPostProcessor.java来额外地注入BeanDefinition
    * 2.例如：ConfigurationClassPostProcessor用于处理被@Configuration注解的类，可在@Configuration类中添加@Bean标签往容器中注入BeanDefinition对象
  * 2.修改已注入的BeanDefinition
    * 1.实现PlaceHolderConfigurerSupport，用于动态地替换BeanDefinition中含有占位符的属性
    * 2.例如：PropertySourcesPlaceholderConfigurer替换属性中的占位符
  * 3.BeanFactoryPostProcessor的执行顺序
    * 1.执行应用程序自己配置的BeanDefinitionRegisterPostProcessor即：postProcessBeanDefinitionRegistry()
    * 2.查找并执行spring容器内置的Bean implements BeanDefinitionRegisterPostProcessor，执行顺序为：
      * 1.若Bean还实现了：PriorityOrdered、Ordered，则先对这些接口按照getOrder()的顺序进行排序，然后在对排好序的BeanDefinitionRegisterPostProcessor接口，依次执行：postProcessBeanDefinitionRegistry
      * 2.若Bean未实现：PriorityOrdered、Ordered，则按照查找顺序依次执行postProcessBeanDefinitionRegistry()
    * 3.执行应用程序配置的BeanFactoryPostProcessor.postProcessBeanFactory()，执行顺序为：
      * 1.配置的BeanFactoryPostProcessor还实现了PriorityOrdered、Ordered，则执行顺序为getOrder()
      * 2.执行普通的BeanFactoryPostProcessor.postProcessBeanFactory()
    * 4.查找并执行spring容器内置的Bean implements BeanFactoryPostProcessor.postProcessBeanFactory(),执行顺序为查找顺序
3. spring通过BeanDefinition实例化Bean，并将Bean放入：singletonObjects（一级缓存）的过程
    * 1.实例化：BeanFactory.preInstantiateSingletons()->getBean()->doGetBean()->先从缓存中查找，查找顺序：一级、二级、三级，即：getSingleton(String beanName, boolean allowEarlyReference)，若在三级缓存中查找到了，则将对象放入二级缓存中，并返回二级缓存中的对象
      * 1.未找到：getSingleton(String beanName, ObjectFactory<?> singletonFactory)->从一级缓存中查找即：this.singletonObjects.get(beanName)，若找到，则直接放回实例
        * 1.未从一级缓存中找到时：singletonFactory.getObject()->singletonFactory.getObject()->createBean(beanName, mbdToUse, args)->doCreateBean(beanName, mbdToUse, args)->createBeanInstance(beanName, mbd, args)->instantiateBean(beanName, mbd)->InstantiationStrategy.instantiate(mbd, beanName, this)
        * 2.createBeanInstance(beanName, mbd, args)->applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName)->MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition(mbd, beanType, beanName)
        * 3.postProcessMergedBeanDefinition->addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean))，将该对象放入singletonFactories（三级缓存）中
      * 2.找到，立即返回实例化的bean
    * 2.属性赋值：调用populateBean(String beanName, RootBeanDefinition mbd, @Nullable BeanWrapper bw)完成对象属性的初始化
      * 1.调用spring容器中实现了InstantiationAwareBeanPostProcessor extends BeanPostProcessor接口的postProcessAfterInstantiation(Object bean, String beanName)
      * 2.调用spring容器中实现了InstantiationAwareBeanPostProcessor接口的postProcessAfterInstantiation(Object bean, String beanName)
        * 例如：AutowiredAnnotationBeanPostProcessor extends SmartInstantiationAwareBeanPostProcessor，调用AutowiredAnnotationBeanPostProcessor.postProcessProperties(pvs, bean, beanName)
          * postProcessProperties(beanName, bean, pvs)->findAutowiringMetadata(beanName, bean.getClass(), pvs)->metadata.inject(bean, beanName, pvs)完成bean中被@Autowired属性的自动注入
          * metadata.inject(bean, beanName, pvs)->AutowiredFieldElement..inject(bean, beanName, pvs)->resolveFieldValue(field, bean, beanName)->beanFactory.resolveDependency(desc, beanName, autowiredBeanNames, typeConverter)->doResolveDependency(descriptor, beanName, autowiredBeanNames, typeConverter)
          * resolveCandidate(beanName, requiredType, beanFactory)->beanFactory.getBean(beanName)实例化被@Autowired标注的属性的值
      * 3.调用：applyPropertyValues(beanName, mbd, bw, pvs)注入其它属性值
    * 3.初始化：initializeBean(String beanName, Object bean, @Nullable RootBeanDefinition mbd)
      * 1.invokeAwareMethods(beanName,Object)->BeanNameAware.setBeanName(beanName)
      * 2.applyBeanPostProcessorsBeforeInitialization(existingBean, beanName)->BeanPostProcessor.postProcessBeforeInitialization(existingBean, beanName)
      * 3.invokeInitMethods()->InitializingBean.afterPropertiesSet()
      * 4.invokeCustomInitMethod(beanName, bean, mbd)
    * 4.放入一级缓存中（singletonObjects），即：执行完getSingleton(String beanName, ObjectFactory<?> singletonFactory)->addSingleton(beanName, singletonObject)：将对象放入一级缓存，删除二级、三级缓存中的Bean
    * 5.BeanPostProcessor执行顺序：
      * 1.createBean()->resolveBeforeInstantiation()->applyBeanPostProcessorsBeforeInstantiation(beanClass, beanName)不为空->applyBeanPostProcessorsAfterInitialization(bean, beanName)
        * 1.applyBeanPostProcessorsBeforeInstantiation(targetType, beanName)->InstantiationAwareBeanPostProcessor.postProcessBeforeInstantiation(beanClass, beanName)
        * 2.applyBeanPostProcessorsAfterInitialization(bean, beanName)->BeanPostProcessor.postProcessAfterInitialization(bean, beanName)
      * 2.doCreateBean()->applyMergedBeanDefinitionPostProcessors()->MergedBeanDefinitionPostProcessor.postProcessMergedBeanDefinition(beanDefinition, beanClass, beanName)
      * 3.populate()->InstantiationAwareBeanPostProcessor.postProcessAfterInstantiation(bean, beanName)->InstantiationAwareBeanPostProcessor.postProcessProperties(propertyValues, bean, beanName)完成@Resource/@Autowired属性的注入
      * 4.initializeBean()->applyBeanPostProcessorsBeforeInitialization()->BeanPostProcessor.postProcessAfterInitialization(bean, beanName)->applyBeanPostProcessorsAfterInitialization()->BeanPostProcessor.postProcessAfterInitialization(bean, beanName)
      * 5.getEarlyBeanReference()->SmartInstantiationAwareBeanPostProcessor.getEarlyBeanReference(bean, beanName)
    * 6.实例化与初始化过程主要流程：
      * 1.getBean(name, beanClass, args)->doGetBean(name, beanClass, args, typeCheckOnly)->getSingleton(name)
        * 若getSingleton(name)从缓存中查找到了，流程终止。缓存中查找的过程：先从一级缓存中查找，若没有，再从二级缓存中查找，若没有，再从三级缓存中查找，若找到了，则执行getEarlyBeanReference()返回三级缓存中的对象，并删除三级缓存，将其放入二级缓存中，并返回
      * 2.getSingleton(name)->getSingleton(beanName, () -> {createBean(beanName, beanDefinition, args)}(ObjectFactory<?> objectFactory))->singletonFactory.getObject()->createBean(beanName, beanDefinition, args)
      * 3.createBean(beanName, beanDefinition, args)->resolveBeforeInstantiation(beanName, beanDefinition)->doCreateBean(beanName, beanDefinition, args)->createBeanInstance(beanName, beanDefinition, args)
      * 4.createBeanInstance(beanName, beanDefinition, args)->applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName)->addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean))放入三级缓存
      * 5.addSingletonFactory(beanName,objectFactory)->populateBean(beanName, beanDefinition, bean)完成属性依赖注入，有如下两种方式：
        * 1.InstantiationAwareBeanPostProcessor.postProcessProperties()完成@Resource/@Autowired属性依赖的注入
        * 2.applyPropertyValues(beanName, beanDefinition, beanWrapper, propertyValues)
          * 1.简单属性依赖（基础类型依赖，String）-> beanWrapper.setPropertyValues(MutablePropertyValues)完成属性依赖
          * 2.复杂属性依赖（非基础类型依赖，String）BeanDefinitionValueResolver.resolveValueIfNecessary(PropertyValue, originalValue)->resolveReference(argName, runtimeBeanReference)->beanFactory.getBean(resolvedName)
      * 6.populateBean(beanName, beanDefinition, bean)->initializeBean(beanName, bean, beanDefinition)->addSingleton(beanName, singletonObject)放入一级缓存中
    * 7.spring解决循环依赖流程
      * 例如：a 依赖 b，b -> c，c -> a,假设a优先被加载，这时一级、二级、三级缓存中都没有a,b,c对象
      * a执行到createBeanInstance()执行完后得到实例beanA(addSingletonFactory)并放入三级缓存中->调用a.populate()发现依赖b，则调用getBean(b)，此时三级缓存中有a
      * b执行到createBeanInstance()执行完后得到实例beanB(addSingletonFactory)并放入三级缓存中->调用b.populate()发现依赖c，则调用getBean(c)，此时三级缓存中有a,b
      * c执行到createBeanInstance()执行完后得到实例beanC(addSingletonFactory)并放入三级缓存中->调用b.populate()发现依赖a，则调用getBean(a)，此时三级缓存中有a,b,c
      * c.getBean(a)->getSingleton(name)从三级缓存中能获取到a，放入二级缓存，移除3级缓存中的a，即：a = singletonFactory.getObject()->getEarlyBeanReference()->earlySingletonObjects.put(beanName, singletonObject)->singletonFactories.remove(beanName)，此时三级缓存中有c,b，二级缓存a
      * c.populate(a)->c.addSingleton()，此时c是一个完整对象，并返回c对象。c放入一级缓存中，并移除二级、三级缓存中的c，此时三级缓存：b，二级缓存：a，一级缓存：c
      * b.populate(c)->b.addSingleton()，此时b是一个完整对象，并返回b对象。b放入一级缓存中，并移除二级、三级缓存中的b，此时三级缓存：无，二级缓存：a，一级缓存：b,c
      * a.populate(b)->a.addSingleton()，此时a是一个完整对象，并返回a对象。a放入一级缓存中，并移除二级、三级缓存中的a，此时三级缓存：无，二级缓存：无，一级缓存：a,b,c

#### 注解启动源码分析
####构造器过程分析
1.new AnnotationConfigApplicationContext()
    1. 初始化new DefaultListableBeanFactory()即：初始化：BeanDefinitionRegister和DefaultSingletonBeanRegistry，疑问点为啥要在构造函数中优先初始化new DefaultListableBeanFactory()
        1.AnnotationConfigApplicationContext构建完成后且初始化new DefaultListableBeanFactory()，此时调用：register()或scan()来完成容器加载bean动作，这时加载到的BeanDefinition无处存放
    2. 初始化AnnotatedBeanDefinitionReader和ClassPathBeanDefinitionScanner
        1.AnnotatedBeanDefinitionReader用于读取解析使用register()加载的bean
            * 初始化AnnotatedBeanDefinitionReader时，会加载spring容器用于处理内部注解的XxxPost：例如：ConfigurationClassPostProcessor、AutowiredAnnotationBeanPostProcessor等
            * 调用register()加载bean过程：register()->doRegister()
            * ConfigurationClassPostProcessor加载被@ComponentScan注入的package中bean的过程：ConfigurationClassPostProcessor.postProcessBeanDefinitionRegistry()->processConfigBeanDefinitions(registry)->ConfigurationClassParser.parse()->processConfigurationClass()->doProcessConfigurationClass()->ComponentScanAnnotationParser.parse()->ClassPathBeanDefinitionScanner.doScan()
            * ConfigurationClassPostProcessor加载被@Import注入的Bean的过程：ConfigurationClassParser.doProcessConfigurationClass()加载@ComponentScan完后->getImports()->processImports()->判断被@Import注解表示的类是实现了：ImportSelector、ImportBeanDefinitionRegistrar、普通的java类
                * 普通的class类，processImports()->processConfigurationClass()->doProcessConfigurationClass(),sourceClass=null->configurationClasses.put(configClass, configClass)完成将资源放入configurationClasses集合中
                * ImportSelector接口时调用：processImports()->ImportSelector.getExclusionFilter()->ImportSelector.selectImports()->Predicate.test()->processImports()->然后再走不同class类的流程
                * ImportBeanDefinitionRegistrar接口时调用：configClass.addImportBeanDefinitionRegistrar(importBeanDefinitionRegistrars)放入map集合中
                * ConfigurationClassPostProcessor加载被@Import注入的Bean的过程采用了递归的方式解决
            * ConfigurationClassPostProcessor调用：ConfigurationClassParser.parse()完成了对@Import资源的加载但还未解析成BeanDefinition，例如：@Import注入的资源放入：ConfigurationClassParser.configurationClasses集合中和ConfigurationClass.importBeanDefinitionRegistrars集合中
            * ConfigurationClassPostProcessor加载被@ImportSource注入的资源的过程：ConfigurationClassParser.parse()->ConfigurationClassParser.doProcessConfigurationClass()加载@ComponentScan完后->addImportedResource()->ConfigurationClass.importedResources集合中
            * ConfigurationClassPostProcessor加载被@Bean注入的资源的过程：ConfigurationClassParser.parse()->ConfigurationClassParser.doProcessConfigurationClass()加载@ComponentScan完后->retrieveBeanMethodMetadata()解析出来@Bean注解的bean->addBeanMethod()->放入ConfigurationClass.beanMethods集合
            * ConfigurationClassPostProcessor调用：ConfigurationClassParser.parse()完成了对@Bean资源的加载但还未解析成BeanDefinition，例如：@Bean注入的资源放入：ConfigurationClass.beanMethods集合中
            * ConfigurationClassPostProcessor调用ConfigurationClassParser.parse()完成资源的加载但资源还未被解析，ConfigurationClassParser.parse()->ConfigurationClassBeanDefinitionReader.loadBeanDefinitions()完成资源的解析并放入BeanDefinitionRegister中
                * ConfigurationClassBeanDefinitionReader.loadBeanDefinitions()->loadBeanDefinitionsForConfigurationClass()->registerBeanDefinitionForImportedConfigurationClass()完成@Import注入的普通的class类和被ImportSelector筛选出来的普通class类
                * ConfigurationClassBeanDefinitionReader.loadBeanDefinitions()->loadBeanDefinitionsForConfigurationClass()->loadBeanDefinitionsForBeanMethod()完成被@Bean注入的普通的class类
                    * 使用@Conditional标签满足特定条件才加载Bean流程->loadBeanDefinitionsForBeanMethod()->ConditionEvaluator.shouldSkip()->Condition.matches()
                * ConfigurationClassBeanDefinitionReader.loadBeanDefinitions()->loadBeanDefinitionsForConfigurationClass()->loadBeanDefinitionsFromImportedResources()
                * ConfigurationClassBeanDefinitionReader.loadBeanDefinitions()->loadBeanDefinitionsForConfigurationClass()->loadBeanDefinitionsFromRegistrars()->ImportBeanDefinitionRegistrar.registerBeanDefinitions()完成资源的加载
        2.ClassPathBeanDefinitionScanner用于读取使用scan()包名加载的bean
            * 初始化ClassPathBeanDefinitionScanner时，会初始化bean记载过滤器：AnnotationTypeFilter和环境以及资源加载器
            * scan()加载bean过程：scan()->ClassPathBeanDefinitionScanner.doScan()
    3. 调用register()或scan()方法完成整体spring容器需要加载的bean操作
    4.调用refresh()完成bean的初始化操作

### 依赖注入

## AOP

