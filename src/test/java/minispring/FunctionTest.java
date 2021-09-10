package minispring;

import minispring.bean.NeoPersonService;
import minispring.bean.PersonDao;
import minispring.bean.PersonService;
import minispring.beans.BeanException;
import minispring.beans.PropertyValue;
import minispring.beans.PropertyValues;
import minispring.beans.factory.config.BeanDefinition;
import minispring.beans.factory.config.BeanReference;
import minispring.beans.factory.strategy.SimpleInstantiationStrategy;
import minispring.beans.factory.support.DefaultListableBeanFactory;
import minispring.beans.factory.xml.XmlBeanDefinitionReader;
import minispring.context.support.ClassPathXmlApplicationContext;
import minispring.listener.CustomizedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author lihua
 * @since 2021/8/23
 */
class FunctionTest {

    private static final String SERVICE_NAME = "personService";

    private static final String DAO_NAME = "personDao";

    private static final String PERSON_NAME = "喵喵";

    private static final Integer PERSON_GENDER = 0;

    private static final String CONFIG_LOCATION = "classpath:spring.xml";

    @Test
    @DisplayName("容器类功能-不存在bean定义")
    void testStep1() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        Assertions.assertThrows(BeanException.class, () -> beanFactory.getBean(SERVICE_NAME));
    }

    @Test
    @DisplayName("容器类功能-默认cglib方式创建bean")
    void testStep2() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        getAndCompareBean(beanFactory);
    }

    private void getAndCompareBean(DefaultListableBeanFactory beanFactory) {
        BeanDefinition beanDefinition = new BeanDefinition(PersonService.class);
        beanFactory.registerBeanDefinition(SERVICE_NAME, beanDefinition);
        PersonService newPersonService = (PersonService) beanFactory.getBean(SERVICE_NAME);
        Assertions.assertNotNull(newPersonService);
        Assertions.assertNull(newPersonService.getName());
        PersonService cachedPersonService = (PersonService) beanFactory.getBean(SERVICE_NAME);
        Assertions.assertEquals(newPersonService.hashCode(), cachedPersonService.hashCode());
    }

    @Test
    @DisplayName("容器类功能-jdk方式创建bean")
    void testStep3() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.setInstantiationStrategy(new SimpleInstantiationStrategy());
        getAndCompareBean(beanFactory);
    }

    /**
     * 使用到的构造方法：
     *
     * @see PersonService#PersonService(java.lang.String, java.lang.Integer)
     */
    @Test
    @DisplayName("有参构造方法")
    void testStep4() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(PersonService.class);
        beanFactory.registerBeanDefinition(SERVICE_NAME, beanDefinition);
        PersonService newPersonService = (PersonService) beanFactory.getBean(SERVICE_NAME, PERSON_NAME, PERSON_GENDER);
        Assertions.assertNotNull(newPersonService);
        Assertions.assertEquals(PERSON_NAME, newPersonService.getName());
    }

    @Test
    @DisplayName("实例化后初始化属性")
    void testStep5() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition(DAO_NAME, new BeanDefinition(PersonDao.class));
        BeanDefinition beanDefinition = getServiceBeanDefinition();
        beanFactory.registerBeanDefinition(SERVICE_NAME, beanDefinition);
        PersonService newPersonService = (PersonService) beanFactory.getBean(SERVICE_NAME);
        Assertions.assertNotNull(newPersonService);
        Assertions.assertEquals(PERSON_NAME, newPersonService.getName());
        Integer gender = newPersonService.getGender();
        Assertions.assertEquals(PERSON_GENDER, gender);
    }

    private BeanDefinition getServiceBeanDefinition() {
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", PERSON_NAME));
        propertyValues.addPropertyValue(new PropertyValue("gender", PERSON_GENDER));
        propertyValues.addPropertyValue(new PropertyValue(DAO_NAME, new BeanReference(DAO_NAME)));
        return new BeanDefinition(PersonService.class, propertyValues);
    }

    @Test
    @DisplayName("xml配置对象信息")
    void testStep6() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinitions(CONFIG_LOCATION);
        Object bean = beanFactory.getBean(SERVICE_NAME);
        Assertions.assertNotNull(bean);
        Integer gender = ((PersonService) bean).queryGenderByName(PERSON_NAME);
        Assertions.assertEquals(PERSON_GENDER, gender);
    }

    @Test
    @DisplayName("applicationContext和postProcessor")
    void testStep7() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_LOCATION);
        Object bean = applicationContext.getBean(SERVICE_NAME);
        Assertions.assertNotNull(bean);
        // postProcessors改变了属性
        Assertions.assertEquals("张喵喵", ((PersonService) bean).getName());
        Assertions.assertEquals(1, ((PersonService) bean).getGender());
        Integer gender = ((PersonService) bean).queryGenderByName(PERSON_NAME);
        Assertions.assertEquals(PERSON_GENDER, gender);
    }

    @Test
    @DisplayName("初始化方法和销毁方法&aware注入")
    void testStep8() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_LOCATION);
        applicationContext.registerShutdownHook();
        Object bean = applicationContext.getBean(SERVICE_NAME);
        Assertions.assertNotNull(bean);
    }

    @Test
    @DisplayName("对象作用域-prototype")
    void testStep9() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(CONFIG_LOCATION);
        applicationContext.registerShutdownHook();
        Object bean = applicationContext.getBean(DAO_NAME);
        Assertions.assertNotNull(bean);
        System.out.println("第一个获取的bean的hashCode是： " + bean.hashCode());
        Object newBean = applicationContext.getBean(DAO_NAME);
        Assertions.assertNotEquals(bean, newBean);
        System.out.println("第二个获取的bean的hashCode是： " + newBean.hashCode());
    }

    @Test
    void testHook() {
        Assertions.assertDoesNotThrow(() ->
                Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("close！"))));
    }

    @Test
    @DisplayName("FactoryBean")
    void testStep10() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-factory-bean.xml");
        applicationContext.registerShutdownHook();
        NeoPersonService personService = (NeoPersonService) applicationContext.getBean(SERVICE_NAME);
        Assertions.assertNotNull(personService);
        Integer gender = personService.queryGenderByName(PERSON_NAME);
        Assertions.assertEquals(PERSON_GENDER, gender);
    }

    @Test
    @DisplayName("事件发布和监听")
    void testStep11() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-listener.xml");
        Assertions.assertNotNull(applicationContext);
        applicationContext.registerShutdownHook();
        applicationContext.publishEvent(new CustomizedEvent(applicationContext, "你好~"));
    }
}
