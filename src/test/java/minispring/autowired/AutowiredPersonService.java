package minispring.autowired;

import lombok.Data;
import minispring.beans.factory.annotation.Autowired;
import minispring.beans.factory.annotation.Value;
import minispring.stereotype.Component;

/**
 * @author lihua
 * @since 2021/8/31
 */
@Data
@Component
public class AutowiredPersonService {

    private String message;

    @Value("${msg}")
    private String messageOfAnnotation;

    @Autowired
    private AutowiredPersonDao autowiredPersonDao;

    public Integer queryGenderByName(String name) {
        return autowiredPersonDao.queryGenderByName(name);
    }
}
