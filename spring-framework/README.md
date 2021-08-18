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
+ spring是如何为应用程序提供修改spring容器中的bean的描述信息提供可扩展性的
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
![AbstractBeanDefinition.png](src\main\resources\images\AbstractBeanDefinition.png)
![AbstractBeanDefinitionReader.png](src\main\resources\images\AbstractBeanDefinitionReader.png)
![DefaultListableBeanFactory.png](src\main\resources\images\DefaultListableBeanFactory.png)
![AbstractApplication.png](src\main\resources\images\AbstractApplicationContext.png)
![AbstractEnvironment.png](src\main\resources\images\AbstractEnvironment.png)
![MutablePropertySources.png](src\main\resources\images\MutablePropertySources.png)
![PropertySourcesPropertyResolver.png](src\main\resources\images\PropertySourcesPropertyResolver.png)
![ResourcePropertySource.png](src\main\resources\images\ResourcePropertySource.png)
### 创建
#### xml启动源码分析
####构造器过程分析
1.xml方式启动spring容器的代码：ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-${env}.xml");
* spring容器启动首先需要加载一个xml配置文件且在xml配置文件中配置bean的描述信息，疑问点：如果用户给出的xml文件path路径中含有占位符，此时spring是如何处理的？
  * 如果由我来设计，我会如此处理占位符:
    * 初始化一个资源池，资源池的来源：环境变量或系统参数
    * 初始化一个占位符处理器，该占位符处理器功能：找出占位符并将占位符替换成原始值，则该占位符必须有的属性：占位符表达式、资源池
    * 将配置文件路径交给占位符处理器
  * spring的设计：
    * 在构造器中调用setConfigLocations(configLocations)完成资源路径的设置
      * 请求链：setConfigLocations()->resolvePath()->getEnvironment()初始化AbstractEnvironment->AbstractEnvironment.resolvePlaceholders()
      * 初始AbstractEnvironment时会初始化如下组件：
        * 初始化资源池：MutablePropertySources.java类似于java中的集合对象拥有各种PropertySource资源
        * 初始化占位符处理器：PropertySourcesPropertyResolver.java
* spring容器现在有了资源文件路径了，接下来就是读取并解析资源文件（解析成一个个BeanDefinition对象），但在解析资源文件之前必须先创建BeanDefinitionRegister存放bean的描述信息
  * 1.初始化BeanDefinitionRegister之前，先做一些准备工作prepareRefresh()：比如设置BeanDefinitionRegister启动标识为true和设置关闭标识为false、初始化资源池和判断程序运行必须满足的状态是否已满足、初始化存放事件的容器
  * 2.创建BeanDefinitionRegister容器，refreshBeanFactory()：先判断BeanDefinitionRegister容器是否已存在，若存在则销毁，再创建DefaultListableBeanFactory即：BeanDefinitionRegister注册器
  * 3.读取(xxx.xml)，解析xxx.xml文件，将解析好的内容分装成一个个BeanDefinition对象，并将解析好的对象放入创建好的BeanDefinitionRegister容器中：loadBeanDefinitions(DefaultListableBeanFactory beanFactory)
  * 4.初始化AbstractBeanDefinitionReader读取
### 依赖注入

## AOP

