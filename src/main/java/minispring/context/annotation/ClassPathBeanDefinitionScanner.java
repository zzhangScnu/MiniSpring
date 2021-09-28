package minispring.context.annotation;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import minispring.beans.factory.config.BeanDefinition;
import minispring.beans.factory.support.BeanDefinitionRegistry;
import minispring.stereotype.Component;
import minispring.stereotype.Scope;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author lihua
 * @since 2021/9/28
 */
@RequiredArgsConstructor
public class ClassPathBeanDefinitionScanner extends ClassPathScanningCandidateComponentProvider {

	private final BeanDefinitionRegistry beanDefinitionRegistry;

	public void scanPackages(String... packages) {
		Arrays.stream(packages)
				.forEach(this::doScanPackage);
	}

	private void doScanPackage(String basePackage) {
		findCandidateComponents(basePackage).stream()
				.peek(this::fillBeanDefinition)
				.forEach(this::registerBeanDefinition);
	}

	private void fillBeanDefinition(BeanDefinition beanDefinition) {
		Class<?> beanClass = beanDefinition.getBeanClass();
		Optional.ofNullable(beanClass.getAnnotation(Scope.class))
				.map(Scope::value)
				.ifPresent(beanDefinition::setScope);
	}

	private void registerBeanDefinition(BeanDefinition beanDefinition) {
		String componentName = getComponentName(beanDefinition);
		beanDefinitionRegistry.registerBeanDefinition(componentName, beanDefinition);
	}

	/**
	 * Annotation annotation = beanDefinition.getBeanClass().getAnnotation(Component.class);
	 * 如果获取到的class没有泛型参数的话，在这个class上面进一步获取到的注解也是父类，而不是具体的某一个注解类
	 */
	private String getComponentName(BeanDefinition beanDefinition) {
		Class<?> beanClass = beanDefinition.getBeanClass();
		Component annotation = beanClass.getAnnotation(Component.class);
		String value = annotation.value();
		return StrUtil.isNotBlank(value) ?
				value : StrUtil.lowerFirst(beanClass.getSimpleName());
	}
}
