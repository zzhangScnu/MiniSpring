package minispring.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lihua
 * @since 2021/8/31
 */
public class PersonDao {

    private static final Map<String, Integer> NAME_GENDER_MAP = new HashMap<>();

    public void initialize() {
        NAME_GENDER_MAP.put("喵喵", 0);
        NAME_GENDER_MAP.put("牛奶", 1);
    }

    public void destroy() {
        NAME_GENDER_MAP.clear();
    }

    public Integer queryGenderByName(String name) {
        return NAME_GENDER_MAP.get(name);
    }
}
