package minispring.context.support;

import minispring.beans.BeanException;
import minispring.beans.factory.ConfigurableListableBeanFactory;
import minispring.beans.factory.config.BeanFactoryPostProcessor;
import minispring.beans.factory.config.BeanPostProcessor;
import minispring.context.ConfigurableApplicationContext;
import minispring.core.io.loader.DefaultResourceLoader;

import java.util.Map;

/**
 * @author lihua
 * @since 2021/8/31
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

	@Override
	public void refresh() throws BeanException {
		refreshBeanFactory();
		// 每次refresh，其实都是创建了新的容器
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();
		invokeBeanFactoryPostProcessors(beanFactory);
		registerBeanPostProcessors(beanFactory);
		beanFactory.preInstantiateSingletons();
	}

	/**
	 * 创建BeanFactory，加载BeanDefinition
	 *
	 * @throws BeanException 异常
	 */
	protected abstract void refreshBeanFactory() throws BeanException;

	/**
	 * 获取BeanFactory
	 *
	 * @return beanFactory
	 */
	protected abstract ConfigurableListableBeanFactory getBeanFactory();

	/**
	 * beanDefinition加载之后、bean实例化之前，执行已注册的BeanFactoryPostProcessor
	 *
	 * @param beanFactory bean工厂
	 */
	private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = getBeansOfType(BeanFactoryPostProcessor.class);
		beanFactoryPostProcessorMap.values()
				.forEach(beanFactoryPostProcessor -> beanFactoryPostProcessor.postProcessBeanFactory(beanFactory));
	}

	/**
	 * 执行BeanPostProcessor的注册操作，以便在bean实例化的时候调用
	 *
	 * @param beanFactory bean工厂
	 */
	private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
		Map<String, BeanPostProcessor> beanPostProcessorMap = getBeansOfType(BeanPostProcessor.class);
		beanPostProcessorMap.values()
				.forEach(beanFactory::addBeanPostProcessor);
	}

	@Override
	public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
		return getBeanFactory().getBeansOfType(type);
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return getBeanFactory().getBeanDefinitionNames();
	}

	@Override
	public Object getBean(String name, Object... args) {
		return getBeanFactory().getBean(name, args);
	}

	@Override
	public void registerShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(this::close));
	}

	@Override
	public void close() {
		getBeanFactory().destroySingletons();
	}
}
