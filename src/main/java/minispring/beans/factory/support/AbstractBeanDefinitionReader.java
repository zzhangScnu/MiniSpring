package minispring.beans.factory.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import minispring.core.io.loader.DefaultResourceLoader;
import minispring.core.io.loader.ResourceLoader;

/**
 * @author lihua
 * @since 2021/8/27
 */
@Getter
@AllArgsConstructor
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

	private final BeanDefinitionRegistry beanDefinitionRegistry;

	private ResourceLoader resourceLoader;

	protected AbstractBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
		this(beanDefinitionRegistry, new DefaultResourceLoader());
	}
}
