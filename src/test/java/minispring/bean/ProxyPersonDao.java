package minispring.bean;

import minispring.beans.factory.FactoryBean;
import minispring.util.ClassUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lihua
 * @since 2021/9/7
 */
public class ProxyPersonDao implements FactoryBean<IPersonDao> {

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public IPersonDao getObject() {
		// 函数式接口
		InvocationHandler handler = (proxy, method, args) -> {
			Map<String, Integer> NAME_GENDER_MAP = new HashMap<String, Integer>() {{
				put("喵喵", 0);
				put("牛奶", 1);
			}};
			return NAME_GENDER_MAP.get(args[0].toString());
		};

		return (IPersonDao) Proxy.newProxyInstance(ClassUtils.getDefaultClassLoader(), new Class[]{IPersonDao.class}, handler);
	}
}
