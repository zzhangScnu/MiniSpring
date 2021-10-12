package minispring.context.support;

import minispring.beans.BeanException;
import minispring.beans.factory.ConfigurableListableBeanFactory;
import minispring.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
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
		addBeanPostProcessor(beanFactory);
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
	public Object getBean(Class<?> type) {
		return getBeanFactory().getBean(type);
	}

	@Override
	public Object getBeanPlainly(String name, Object... args) {
		return getBeanFactory().getBeanPlainly(name, args);
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

	private void addBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
		addAutowiredAnnotationBeanPostProcessor(beanFactory);
	}

	/**
	 * 支持一下注解注入依赖
	 * <p>
	 * 在上一个版本中，是需要在XML配置文件里面声明一下AutowiredAnnotationBeanPostProcessor这个bean的，使用起来比较繁琐
	 * 所以想要在容器启动的时候就自动添加到容器里，实现对用户无感知的效果
	 * <p>
	 * 看了Spring的实现方式，是判断beanDefinitionRegistry，里面如果没有AutowiredAnnotationBeanPostProcessor对应的beanDefinition，
	 * 就会自己生成该beanDefinition并注册，逻辑比较复杂，所以这里简单实现一下
	 * <p>
	 * 在刷新容器的时候，就自动把AutowiredAnnotationBeanPostProcessor实例化并添加进来
	 * 但是如果不经过XML解析和自动化装配的话，直接new出来是不会被注入beanFactory实例的
	 * 所以又给AutowiredAnnotationBeanPostProcessor增加了有参构造方法，new的时候直接把工厂实例传递进去
	 */
	private void addAutowiredAnnotationBeanPostProcessor(ConfigurableListableBeanFactory beanFactory) {
		String targetName = AutowiredAnnotationBeanPostProcessor.class.getSimpleName();
		boolean present = getBeansOfType(BeanPostProcessor.class).values().stream()
				.anyMatch(processor -> processor.getClass().getSimpleName().contains(targetName));
		// 如果用户已经手动配置在XML文件的话，就不额外处理了
		if (!present) {
			beanFactory.addBeanPostProcessor(new AutowiredAnnotationBeanPostProcessor(beanFactory));
		}
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
