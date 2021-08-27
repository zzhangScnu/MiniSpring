package minispring.beans;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lihua
 * @since 2021/8/26
 */
@Data
public class PropertyValues {

    private final List<PropertyValue> propertyValueList;

    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValueList.add(propertyValue);
    }

    public PropertyValues() {
        propertyValueList = new ArrayList<>();
    }
}
