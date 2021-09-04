package minispring.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import minispring.beans.BeanException;
import minispring.beans.factory.BeanClassLoaderAware;
import minispring.beans.factory.BeanFactory;
import minispring.beans.factory.BeanFactoryAware;
import minispring.beans.factory.BeanNameAware;
import minispring.beans.factory.DisposableBean;
import minispring.beans.factory.InitializingBean;
import minispring.context.ApplicationContext;
import minispring.context.ApplicationContextAware;

/**
 * @author lihua
 * @since 2021/8/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonService implements InitializingBean, DisposableBean,
        BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, ApplicationContextAware {

    private String name;

    private Integer gender;

    private PersonDao personDao;

    public Integer queryGenderByName(String name) {
        return personDao.queryGenderByName(name);
    }

    @Override
    public void destroy() {
        System.out.println("destroy方法被执行了！");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("afterPropertiesSet方法被执行了！");
    }

    @Override
    public void setBeanName(String beanName) throws BeanException {
        System.out.println("获取了beanName：" + beanName);
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) throws BeanException {
        System.out.println("获取了classLoader：" + classLoader.toString());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        System.out.println("获取了beanFactory：" + beanFactory.toString());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeanException {
        System.out.println("获取了applicationContext：" + applicationContext.toString());
    }
}
