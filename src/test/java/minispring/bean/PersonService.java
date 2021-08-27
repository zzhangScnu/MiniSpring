package minispring.bean;

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

    private PersonDao personDao;

    public Integer queryGenderByName(String name) {
        return personDao.queryGenderByName(name);
    }

    public PersonService(String name, Integer gender) {
        this.name = name;
        this.gender = gender;
    }
}
