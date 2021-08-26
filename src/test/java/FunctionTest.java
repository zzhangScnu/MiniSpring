import minispring.BeanException;
import minispring.PropertyValue;
import minispring.PropertyValues;
import minispring.factory.config.BeanDefinition;
import minispring.factory.strategy.SimpleInstantiationStrategy;
import minispring.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.PersonService;

/**
 * @author lihua
 * @since 2021/8/23
 */
class FunctionTest {

    private static final String BEAN_NAME = "personService";

    private static final String PERSON_NAME = "miaomiao";

    private static final Integer PERSON_GENDER = 0;

    @Test
    @DisplayName("容器类功能-不存在bean定义")
    void testStep1() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        Assertions.assertThrows(BeanException.class, () -> beanFactory.getBean(BEAN_NAME));
    }

    @Test
    @DisplayName("容器类功能-默认cglib方式创建bean")
    void testStep2() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        getAndCompareBean(beanFactory);
    }

    private void getAndCompareBean(DefaultListableBeanFactory beanFactory) {
        BeanDefinition beanDefinition = new BeanDefinition(PersonService.class);
        beanFactory.registerBeanDefinition(BEAN_NAME, beanDefinition);
        PersonService newPersonService = (PersonService) beanFactory.getBean(BEAN_NAME);
        Assertions.assertNotNull(newPersonService);
        Assertions.assertNull(newPersonService.getName());
        PersonService cachedPersonService = (PersonService) beanFactory.getBean(BEAN_NAME);
        Assertions.assertEquals(newPersonService.hashCode(), cachedPersonService.hashCode());
    }

    @Test
    @DisplayName("容器类功能-jdk方式创建bean")
    void testStep3() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.setInstantiationStrategy(new SimpleInstantiationStrategy());
        getAndCompareBean(beanFactory);
    }

    @Test
    @DisplayName("有参构造方法")
    void testStep4() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(PersonService.class);
        beanFactory.registerBeanDefinition(BEAN_NAME, beanDefinition);
        PersonService newPersonService = (PersonService) beanFactory.getBean(BEAN_NAME, PERSON_NAME, PERSON_GENDER);
        Assertions.assertNotNull(newPersonService);
        Assertions.assertEquals(PERSON_NAME, newPersonService.getName());
    }

    @Test
    @DisplayName("实例化后初始化属性")
    void testStep5() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", PERSON_NAME));
        propertyValues.addPropertyValue(new PropertyValue("gender", PERSON_GENDER));
        BeanDefinition beanDefinition = new BeanDefinition(PersonService.class, propertyValues);
        beanFactory.registerBeanDefinition(BEAN_NAME, beanDefinition);
        PersonService newPersonService = (PersonService) beanFactory.getBean(BEAN_NAME);
        Assertions.assertNotNull(newPersonService);
        Assertions.assertEquals(PERSON_NAME, newPersonService.getName());
    }
}
