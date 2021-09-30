package minispring.beans;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import minispring.beans.factory.ConfigurableListableBeanFactory;
import minispring.beans.factory.config.BeanDefinition;
import minispring.beans.factory.config.BeanFactoryPostProcessor;
import minispring.beans.factory.config.BeanReference;
import minispring.beans.factory.support.DefaultListableBeanFactory;
import minispring.core.io.loader.DefaultResourceLoader;
import minispring.core.io.resource.Resource;
import minispring.stereotype.Autowired;
import minispring.stereotype.Value;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 在beanDefinition注册完成后，给里面的占位符赋值
 * 这一版实现，把XML配置文件、@Value注解、@Autowired注解都放在一起解析，逻辑混杂在一起
 * 如果以后要引入@Qualifier等其他注解，还得在此基础上继续往上垒代码，职责不够分明
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
	}

	private void processPlaceHolder(String beanDefinitionName, Properties properties, DefaultListableBeanFactory defaultListableBeanFactory) {
		BeanDefinition beanDefinition = defaultListableBeanFactory.getBeanDefinition(beanDefinitionName);
		beanDefinition.getPropertyValues().getPropertyValueList()
				.forEach(propertyValue -> doProcessPlaceHolderOfFile(propertyValue, properties));
		doProcessPlaceHolderOfAnnotation(beanDefinition, properties);
	}

	private void doProcessPlaceHolderOfAnnotation(BeanDefinition beanDefinition, Properties properties) {
		Class<?> beanClass = beanDefinition.getBeanClass();
		List<PropertyValue> propertyValueList = beanDefinition.getPropertyValues().getPropertyValueList();
		doProcessPlaceHolderOfValue(beanClass, properties, propertyValueList);
		doProcessPlaceHolderOfAutowired(beanClass, propertyValueList);
	}

	private void doProcessPlaceHolderOfValue(Class<?> beanClass, Properties properties, List<PropertyValue> propertyValueList) {
		Arrays.stream(beanClass.getDeclaredFields())
				.peek(field -> field.setAccessible(true))
				.filter(field -> field.isAnnotationPresent(Value.class))
				.map(field -> assemblePropertyValueForValue(field, properties))
				.forEach(propertyValueList::add);
	}

	private void doProcessPlaceHolderOfAutowired(Class<?> beanClass, List<PropertyValue> propertyValueList) {
		Arrays.stream(beanClass.getDeclaredFields())
				.peek(field -> field.setAccessible(true))
				.filter(field -> field.isAnnotationPresent(Autowired.class))
				.map(this::assemblePropertyValueForAutowired)
				.forEach(propertyValueList::add);
	}

	private PropertyValue assemblePropertyValueForValue(Field field, Properties properties) {
		Value annotation = field.getAnnotation(Value.class);
		String keyWithPlaceHolder = annotation.value();
		String key = field.getName();
		String value = resolveStringValue(keyWithPlaceHolder, properties);
		return new PropertyValue(key, value);
	}

	private PropertyValue assemblePropertyValueForAutowired(Field field) {
		String name = field.getName();
		BeanReference beanReference = new BeanReference(name);
		return new PropertyValue(name, beanReference);
	}

	private void doProcessPlaceHolderOfFile(PropertyValue propertyValue, Properties config) {
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
}
