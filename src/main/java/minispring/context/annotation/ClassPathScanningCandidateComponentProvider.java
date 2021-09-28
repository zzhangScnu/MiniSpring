package minispring.context.annotation;

import cn.hutool.core.util.ClassUtil;
import minispring.beans.factory.config.BeanDefinition;
import minispring.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lihua
 * @since 2021/9/28
 */
public class ClassPathScanningCandidateComponentProvider {

	protected Set<BeanDefinition> findCandidateComponents(String basePackage) {
		return ClassUtil.scanPackageByAnnotation(basePackage, Component.class).stream()
				.map(BeanDefinition::new)
				.collect(Collectors.toSet());
	}
}
