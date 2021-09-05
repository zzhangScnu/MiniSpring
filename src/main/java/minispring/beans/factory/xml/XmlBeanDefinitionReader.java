package minispring.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import minispring.beans.BeanException;
import minispring.beans.PropertyValue;
import minispring.beans.PropertyValues;
import minispring.beans.factory.config.BeanDefinition;
import minispring.beans.factory.config.BeanReference;
import minispring.beans.factory.support.AbstractBeanDefinitionReader;
import minispring.beans.factory.support.BeanDefinitionRegistry;
import minispring.core.io.loader.ResourceLoader;
import minispring.core.io.resource.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * @author lihua
 * @since 2021/8/27
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    private static final String ID = "id";

    private static final String CLASS = "class";

    private static final String NAME = "name";

    private static final String VALUE = "value";

    private static final String REF = "ref";

    private static final String INIT_METHOD = "init-method";

    private static final String DESTROY_METHOD = "destroy-method";

    private static final String SCOPE = "scope";

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry, ResourceLoader resourceLoader) {
        super(beanDefinitionRegistry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(String location) {
        // 通过getter去引用父类的private属性对象
        Resource resource = getResourceLoader().getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) {
        Arrays.stream(locations)
                .forEach(this::loadBeanDefinitions);
    }

    private void loadBeanDefinitions(Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinitions(inputStream);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeanException("cannot load bean definition!", e);
        }
    }

    private void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        Document document = XmlUtil.readXML(inputStream);
        Element element = document.getDocumentElement();
        NodeList beanNodes = element.getChildNodes();
        int bound = beanNodes.getLength();
        // 为了把异常在方法签名处抛出去，这里用for写法简洁些
        for (int index = 0; index < bound; index++) {
            doLoadSingleBeanDefinition(beanNodes, index);
        }
    }

    private void doLoadSingleBeanDefinition(NodeList childNodes, int index) throws ClassNotFoundException {
        Node node = childNodes.item(index);
        if (!(node instanceof Element)) {
            return;
        }
        Element bean = (Element) node;
        Class<?> beanClass = Class.forName(bean.getAttribute(CLASS));
        String beanName = getBeanName(bean, beanClass);
        PropertyValues propertyValues = assemblePropertyValues(bean);
        BeanDefinition beanDefinition = new BeanDefinition(beanClass, propertyValues);
        setMethodNames(bean, beanDefinition);
        beanDefinition.setScope(bean.getAttribute(SCOPE));
        checkDuplicate(beanName);
        getBeanDefinitionRegistry().registerBeanDefinition(beanName, beanDefinition);
    }

    private void setMethodNames(Element bean, BeanDefinition beanDefinition) {
        String initMethodName = bean.getAttribute(INIT_METHOD);
        beanDefinition.setInitMethodName(initMethodName);
        String destroyMethodName = bean.getAttribute(DESTROY_METHOD);
        beanDefinition.setDestroyMethodName(destroyMethodName);
    }

    private void checkDuplicate(String beanName) {
        if (getBeanDefinitionRegistry().containsBeanDefinition(beanName)) {
            throw new BeanException("Duplicate beanName[" + beanName + "] is not allowed");
        }
    }

    private String getBeanName(Element bean, Class<?> beanClass) {
        return Optional.ofNullable(bean.getAttribute(ID))
                .orElse(Optional.ofNullable(bean.getAttribute(NAME))
                        .orElse(StrUtil.lowerFirst(beanClass.getSimpleName())));
    }

    private PropertyValues assemblePropertyValues(Node bean) {
        NodeList propertyNodes = bean.getChildNodes();
        PropertyValues propertyValues = new PropertyValues();
        IntStream.range(0, propertyNodes.getLength())
                .mapToObj(propertyNodes::item)
                .map(this::assemblePropertyValue)
                .filter(Objects::nonNull)
                .forEach(propertyValues::addPropertyValue);
        return propertyValues;
    }

    private PropertyValue assemblePropertyValue(Node node) {
        if (!(node instanceof Element)) {
            return null;
        }
        Element property = (Element) node;
        String name = property.getAttribute(NAME);
        Object value = Optional.ofNullable(property.getAttribute(REF))
                .filter(StrUtil::isNotBlank)
                .map(BeanReference::new)
                .map(beanReference -> (Object) beanReference)
                .orElse(property.getAttribute(VALUE));
        return new PropertyValue(name, value);
    }
}
