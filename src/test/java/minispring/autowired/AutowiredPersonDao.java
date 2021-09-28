package minispring.autowired;

import minispring.stereotype.Component;
import minispring.stereotype.Scope;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lihua
 * @since 2021/8/31
 */
@Component
@Scope("prototype")
public class AutowiredPersonDao {

    private static final Map<String, Integer> NAME_GENDER_MAP = new HashMap<String, Integer>() {{
        put("喵喵", 0);
        put("牛奶", 1);
    }};

    public Integer queryGenderByName(String name) {
        return NAME_GENDER_MAP.get(name);
    }
}
