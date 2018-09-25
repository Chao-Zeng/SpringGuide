# Spring Usage

## Spring Glance

Spring

* is a java framework 
* is a IoC container
* is a dependency injection framework
* is a project family built on top of the Spring Framework

Projects

* Spring Framework
* Spring Boot
* Spring Data
* Spring Cloud
* Spring Cloud Data Flow
* Others

## Core Concept

### Inversion of control (IoC)

Object not create it’s dependency object, but use it’s reference. Framework/Container will create and set dependency object automatically.

Advantage

* decoupling
* modularity
* extensible
* testable

Implementation techniques

* dependency injection
* dependency lookup

### Dependency Injection(DI)

Primary DI method

1. Constructor Injection
    * spring advice method
    * required dependencies are not null
    * enables one to implement application components as immutable objects    
2. Setter Injection
    * circular dependencies
    * optional dependencies
    * re-injection

## Spring Usage

### How Spring Works

![The Spring IoC Container](/doc/SpringIoCContainer.png)

Container

The interface `org.springframework.context.ApplicationContext` represents the Spring IoC container and is responsible for instantiating, configuring, and assembling the aforementioned beans. Usually using implementation class: `AnnotationConfigApplicationContext`, `ClassPathXmlApplicationContext`, `GenericApplicationContext`

Configuration Metadata

The configuration metadata is represented in XML, Java annotations, or Java code.

### Bean

Bean define

* What

    A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container. Otherwise, a bean is simply one of many objects in your application. 

* which

    In Spring, the objects that form the backbone of your application and that are managed by the Spring IoC container are called beans.

* how

    Beans, and the dependencies among them, are reflected in the configuration metadata used by a container.

annotation class

```java
@Component
@Service @Repository @Controller
```

Java configuration typically uses `@Bean` annotated methods within a `@Configuration` class.

```java
@Configuration
public class AppConfig {
    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }
}
```

Bean Property

Property | Annotation/Explain
---------|--------------------
class | class type
name | used to look up bean
scope | `@Scope("prototype")`
constructor arguments | 
properties | 
autowiring mode | autowire collaborators `@Autowired` (fine-grained)
lazy-initialization mode | `@Lazy`
initialization method | `@PostConstruct` `@Bean(initMethod = "init")`
destruction method | `@PreDestroy` `@Bean(destroyMethod = "cleanup")`

Bean Naming Conventions

bean names start with a lowercase letter, and are camel-cased from then on.

ex: `accountService`, `loginController`

Bean Dependency

For each bean, its dependencies are expressed in the form of properties, constructor arguments, or arguments to the static-factory method if you are using that instead of a normal constructor. The container then injects those dependencies when it creates the bean.

annotations

`@Autowired`: inject by Type

`@Resource`: inject by Name

`@Value`: inject build-in type

`@Primary`: mark primary bean for same type bean.

Bean Scope

scope | description
------|------------
singleton | (Default) Scopes a single bean definition to a single object instance per Spring IoC container.
prototype | Scopes a single bean definition to any number of object instances.
request | Scopes a single bean definition to the lifecycle of a single HTTP request
session | Scopes a single bean definition to the lifecycle of an HTTP Session
application | Scopes a single bean definition to the lifecycle of a ServletContext
websocket | Scopes a single bean definition to the lifecycle of a WebSocket

Beans that are singleton-scoped and set to be pre-instantiated (the default) are created when the container is created. Otherwise, the bean is created only when it is requested. Creation of a bean potentially causes a graph of beans to be created, as the bean’s dependencies and its dependencies' dependencies (and so on) are created and assigned.

Non-lazy singleton beans get instantiated according to their declaration order.

method injection

```java
public abstract class CommandManager {

    public Object process(Object commandState) {
        MyCommand command = createCommand();
        command.setState(commandState);
        return command.execute();
    }

    @Lookup
    protected abstract MyCommand createCommand();
}
```

Bean config

To autodetect component classes and register the corresponding beans, you need to add `@ComponentScan` to your `@Configuration` class, where the basePackages attribute is a common parent package for classes.

```java
@Configuration
@ComponentScan(basePackages = "org.example")
public class AppConfig  {
    ...
}
```

When `@Configuration` classes are provided as input, the `@Configuration` class itself is registered as a bean definition, and all declared `@Bean` methods within the class are also registered as bean definitions.

```
ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
```

Composing Java-based configurations

```
@Import({ConfigA.class, ConfigB.class})
```

properties

The `@PropertySource` annotation provides a convenient and declarative mechanism for adding a PropertySource to Spring’s Environment.

```java
@Configuration
@PropertySource("classpath:/com/${my.placeholder:default/path}/app.properties")
public class AppConfig {

    @Autowired
    Environment env;

    @Bean
    public TestBean testBean() {
        TestBean testBean = new TestBean();
        testBean.setName(env.getProperty("testbean.name"));
        return testBean;
    }
}
```

In order to resolve ${...} placeholders in bean definitions or `@Value` annotations using properties from a `PropertySource`, one must register a `PropertySourcesPlaceholderConfigurer`.

```java
@Configuration
@PropertySource("classpath:test-config.properties")
public class TestConfig {

     @Value("${name:defaultName}")
     public String name;
     
     @Bean
     public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
     }

}
```
