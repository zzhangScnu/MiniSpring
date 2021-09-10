package minispring.context;

/**
 * 事件的发布者
 *
 * @author lihua
 * @since 2021/9/8
 */
public interface ApplicationEventPublisher {

	/**
	 * 发布事件
	 * 由于这里不需要关注具体是什么类型的事件，只需要传父类作为参数即可
	 *
	 * @param applicationEvent 事件
	 */
	void publishEvent(ApplicationEvent applicationEvent);
}
