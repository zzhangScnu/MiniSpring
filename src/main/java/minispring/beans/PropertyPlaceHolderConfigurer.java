package minispring.beans;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import minispring.beans.factory.ConfigurableListableBeanFactory;
import minispring.beans.factory.config.BeanDefinition;
import minispring.beans.factory.config.BeanFactoryPostProcessor;
import minispring.beans.factory.support.DefaultListableBeanFactory;
import minispring.core.io.loader.DefaultResourceLoader;
import minispring.core.io.resource.Resource;
import minispring.util.StringValueResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 在beanDefinition注册完成后，给里面的占位符赋值
 *
 * @author lihua
 * @since 2021/9/29
 */
@Data
public class PropertyPlaceHolderConfigurer implements BeanFactoryPostProcessor {

	/**
	 * 同时使用两种零宽断言
	 * 零宽度正回顾后发断言：断言自身出现的位置的【前面】能匹配表达式exp，(?<=exp)
	 * 零宽度正预测先行断言：断言自身出现的位置的【后面】能匹配表达式exp，(?=exp)
	 */
	private static final Pattern PATTERN = Pattern.compile("(?<=\\$\\{).+(?=})");

	private String location;

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException {
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
		Properties properties = parseProperties();
		Arrays.stream(beanFactory.getBeanDefinitionNames())
				.forEach(beanDefinitionName -> processPlaceHolder(beanDefinitionName, properties, defaultListableBeanFactory));
		// 将解析器添加到容器里面去，以便后续解析注解时使用
		beanFactory.addEmbeddedValueResolver(new PlaceholderResolvingStringValueResolver(properties));
	}

	/**
	 * 处理配置文件中的占位符
	 */
	private void processPlaceHolder(String beanDefinitionName, Properties properties, DefaultListableBeanFactory defaultListableBeanFactory) {
		BeanDefinition beanDefinition = defaultListableBeanFactory.getBeanDefinition(beanDefinitionName);
		beanDefinition.getPropertyValues().getPropertyValueList()
				.forEach(propertyValue -> doProcessPlaceHolder(propertyValue, properties));
	}

	private void doProcessPlaceHolder(PropertyValue propertyValue, Properties config) {
		Object value = propertyValue.getValue();
		if (!(value instanceof String)) {
			return;
		}
		String strValue = (String) value;
		String configValue = resolveStringValue(strValue, config);
		propertyValue.setValue(configValue);
	}

	private String resolveStringValue(String strValue, Properties config) {
		Matcher matcher = PATTERN.matcher(strValue);
		if (!matcher.find()) {
			return null;
		}
		String key = matcher.group(0);
		return config.getProperty(key);
	}

	/**
	 * 将xxx.properties解析为Properties对象
	 */
	private Properties parseProperties() {
		if (StrUtil.isBlank(location)) {
			throw new BeanException("location param is empty!");
		}
		Properties properties = new Properties();
		DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
		Resource resource = defaultResourceLoader.getResource(location);
		try {
			InputStream resourceAsStream = resource.getInputStream();
			properties.load(resourceAsStream);
		} catch (IOException e) {
			throw new BeanException("load properties failed!", e);
		}
		return properties;
	}

	@Data
	@RequiredArgsConstructor
	private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

		private final Properties properties;

		@Override
		public String resolveStringValue(String strVal) {
			// 通过外部类的引用，来调用外部类的方法
			return PropertyPlaceHolderConfigurer.this.resolveStringValue(strVal, properties);
		}
	}
}
