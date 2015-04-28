package jp.co.skill.spider.ss01.service;

import java.util.List;

import jp.co.skill.spider.dao.SUserMapper;
import jp.co.skill.spider.dao.domain.SUser;
import jp.co.skill.spider.ss01.form.UserForm;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SUserMapper sUserMapper;

	@Override
	public void register(UserForm sUserForm) {

		SUser s_user = new SUser();
		//sUserFormからs_userへプロパティーのコピー
		BeanUtils.copyProperties(sUserForm, s_user);
		int insCnt = sUserMapper.insert(s_user);

		if(insCnt > 0) {
			System.out.println("success insert");
		}else {
			System.out.println("failuer insert");
		}
	}

	@Override
	public List<SUser> search() {

		// 一旦全権検索のみ後で条件つける。
		List<SUser> resultList = sUserMapper.selectList();

		return resultList;
	}

	@Override
	public SUser getUserInfo(String sUserId) {

		SUser resultData = sUserMapper.selectId(sUserId);

		return resultData;
	}

	@Override
	public void update(UserForm sUserForm) {

		SUser s_user = new SUser();
		//sUserFormからs_userへプロパティーのコピー
		BeanUtils.copyProperties(sUserForm, s_user);

		int insCnt = sUserMapper.update(s_user);

		if(insCnt > 0) {
			System.out.println("success insert");
		}else {
			System.out.println("failuer insert");
		}

	}
}
