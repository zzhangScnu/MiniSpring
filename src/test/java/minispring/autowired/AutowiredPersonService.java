package minispring.autowired;

import lombok.Data;
import minispring.stereotype.Autowired;
import minispring.stereotype.Component;
import minispring.stereotype.Value;

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
