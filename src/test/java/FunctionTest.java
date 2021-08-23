import lombok.extern.slf4j.Slf4j;
import minispring.component.BeanRegistry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.PersonService;

/**
 * @author lihua
 * @since 2021/8/23
 */
@Slf4j
public class FunctionTest {

    @Test
    @DisplayName("测试容器类功能")
    void testStep1() {
        BeanRegistry beanRegistry = new BeanRegistry();
        PersonService newPersonService = beanRegistry.getBean(PersonService.class);
        PersonService cachedPersonService = beanRegistry.getBean(PersonService.class);
        Assertions.assertEquals(newPersonService.hashCode(), cachedPersonService.hashCode());
    }
}
