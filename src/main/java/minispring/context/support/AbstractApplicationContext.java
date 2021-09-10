package minispring.context.support;

import minispring.beans.BeanException;
import minispring.beans.factory.ConfigurableListableBeanFactory;
import minispring.beans.factory.config.BeanFactoryPostProcessor;
import minispring.beans.factory.config.BeanPostProcessor;
import minispring.context.ApplicationEvent;
import minispring.context.ApplicationListener;
import minispring.context.ConfigurableApplicationContext;
import minispring.context.event.ApplicationEventMulticaster;
import minispring.context.event.ContextClosedEvent;
import minispring.context.event.ContextRefreshedEvent;
import minispring.context.event.SimpleApplicationEventMulticaster;
import minispring.core.io.loader.DefaultResourceLoader;

import java.util.Map;

/**
 * @author lihua
 * @since 2021/8/31
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

	public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

	private ApplicationEventMulticaster applicationEventMulticaster;

	@Override
	public void refresh() throws BeanException {
		refreshBeanFactory();
		// 每次refresh，其实都是创建了新的容器
		ConfigurableListableBeanFactory beanFactory = getBeanFactory();
		invokeBeanFactoryPostProcessors(beanFactory);
		registerBeanPostProcessors(beanFactory);
		addApplicationContextProcessor(beanFactory);
		initApplicationEventMulticaster();
		registerListeners();
		beanFactory.preInstantiateSingletons();
		publishEvent();
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
		ContextClosedEvent contextClosedEvent = new ContextClosedEvent(this);
		applicationEventMulticaster.multicasterEvent(contextClosedEvent);
		getBeanFactory().destroySingletons();
	}

	private void addApplicationContextProcessor(ConfigurableListableBeanFactory beanFactory) {
		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
	}

	/**
	 * 初始化事件广播者
	 */
	private void initApplicationEventMulticaster() {
		applicationEventMulticaster = new SimpleApplicationEventMulticaster();
		getBeanFactory().registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
	}

	/**
	 * 注册事件监听器
	 */
	private void registerListeners() {
		getBeansOfType(ApplicationListener.class).values()
				.forEach(applicationEventMulticaster::addApplicationListener);
	}

	/**
	 * 发布容器刷新事件
	 */
	private void publishEvent() {
		ContextRefreshedEvent contextRefreshedEvent = new ContextRefreshedEvent(this);
		applicationEventMulticaster.multicasterEvent(contextRefreshedEvent);
	}

	@Override
	public void publishEvent(ApplicationEvent applicationEvent) {
		applicationEventMulticaster.multicasterEvent(applicationEvent);
	}
}
