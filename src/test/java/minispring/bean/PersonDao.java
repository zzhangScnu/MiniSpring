package minispring.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lihua
 * @since 2021/8/31
 */
public class PersonDao {

	private static final Map<String, Integer> NAME_GENDER_MAP = new HashMap<String, Integer>() {{
		put("喵喵", 0);
		put("牛奶", 1);
	}};

	public Integer queryGenderByName(String name) {
		return NAME_GENDER_MAP.get(name);
	}
}
