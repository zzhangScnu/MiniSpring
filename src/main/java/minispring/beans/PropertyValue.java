package minispring.beans;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author lihua
 * @since 2021/8/26
 */
@Data
@RequiredArgsConstructor
public class PropertyValue {

    private final String name;

    private final Object value;
}
