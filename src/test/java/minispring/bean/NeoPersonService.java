package minispring.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lihua
 * @since 2021/9/7
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NeoPersonService {

	private String name;

	private Integer gender;

	private IPersonDao personDao;

	public Integer queryGenderByName(String name) {
		return personDao.queryGenderByName(name);
	}
}
