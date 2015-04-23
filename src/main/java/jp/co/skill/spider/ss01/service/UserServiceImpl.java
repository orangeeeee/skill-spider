package jp.co.skill.spider.ss01.service;

import jp.co.skill.spider.dao.SUserMapper;
import jp.co.skill.spider.dao.domain.SUser;
import jp.co.skill.spider.ss01.form.UserForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SUserMapper sUserMapper;

	@Override
	public void register(UserForm sUserForm) {

		int insCnt = sUserMapper.insert(new SUser("testUser-1", "p@ssw0rd", "Orage", "", ""));

		if(insCnt > 0) {
			System.out.println("success insert");
		}else {
			System.out.println("failuer insert");
		}
	}

}
