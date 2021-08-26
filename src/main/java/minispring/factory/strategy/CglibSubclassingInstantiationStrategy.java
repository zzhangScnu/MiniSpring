package minispring.factory.strategy;

import minispring.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;

/**
 * @author lihua
 * @since 2021/8/26
 */
@SuppressWarnings("rawtypes")
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy {

	@Override
	public Object instantiate(BeanDefinition beanDefinition, Constructor constructor, Object[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(beanDefinition.getBeanClass());
		enhancer.setCallback(new NoOp() {
		});
		return args.length == 0 ?
				enhancer.create() : enhancer.create(constructor.getParameterTypes(), args);
	}
}
