package minispring.context;

import minispring.beans.factory.ListableBeanFactory;
import minispring.core.io.loader.ResourceLoader;

/**
 * @author lihua
 * @since 2021/8/31
 */
public interface ApplicationContext extends ListableBeanFactory, ResourceLoader, ApplicationEventPublisher {
}
