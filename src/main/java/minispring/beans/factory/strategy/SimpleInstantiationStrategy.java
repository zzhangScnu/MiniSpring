package minispring.beans.factory.strategy;

import minispring.beans.BeanException;
import minispring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author lihua
 * @since 2021/8/26
 */
@SuppressWarnings("rawtypes")
public class SimpleInstantiationStrategy implements InstantiationStrategy {

	@Override
	public Object instantiate(BeanDefinition beanDefinition, Constructor constructor, Object[] args) {
		try {
			if (args.length == 0) {
				return constructor.newInstance();
			}
			return constructor.newInstance(args);
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new BeanException("create bean failed: ", e);
        }
	}
}
