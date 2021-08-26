import lombok.extern.slf4j.Slf4j;
import minispring.BeanException;
import minispring.factory.config.BeanDefinition;
import minispring.factory.support.DefaultListableBeanFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.PersonService;

/**
 * @author lihua
 * @since 2021/8/23
 */
@Slf4j
class FunctionTest {

    private static final String BEAN_NAME = "personService";

    @Test
    @DisplayName("测试容器类功能-不存在bean定义")
    void testStep1() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        Assertions.assertThrows(BeanException.class, () -> beanFactory.getBean(BEAN_NAME));
    }

    @Test
    @DisplayName("测试容器类功能-正常")
    void testStep2() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(PersonService.class);
        beanFactory.registerBeanDefinition(BEAN_NAME, beanDefinition);
        PersonService newPersonService = (PersonService) beanFactory.getBean(BEAN_NAME);
        Assertions.assertNotNull(newPersonService);
        PersonService cachedPersonService = (PersonService) beanFactory.getBean(BEAN_NAME);
        Assertions.assertEquals(newPersonService.hashCode(), cachedPersonService.hashCode());
    }
}
