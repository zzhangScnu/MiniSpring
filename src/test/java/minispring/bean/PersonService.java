package minispring.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import minispring.beans.factory.DisposableBean;
import minispring.beans.factory.InitializingBean;

/**
 * @author lihua
 * @since 2021/8/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonService implements InitializingBean, DisposableBean {

    private String name;

    private Integer gender;

    private PersonDao personDao;

    public Integer queryGenderByName(String name) {
        return personDao.queryGenderByName(name);
    }

    public PersonService(String name, Integer gender) {
        this.name = name;
        this.gender = gender;
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("destroy方法被执行了！");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet方法被执行了！");
    }
}
