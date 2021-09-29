package minispring.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lihua
 * @since 2021/8/26
 */
@Data
@AllArgsConstructor
public class PropertyValue {

    private final String name;

    /**
     * 普通的值，或是BeanReference
     */
    private Object value;
}
