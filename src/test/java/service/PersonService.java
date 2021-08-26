package service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lihua
 * @since 2021/8/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonService {

    private String name;

    private Integer gender;
}
