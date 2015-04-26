package jp.co.skill.spider.ss01.service;

import java.util.List;

import jp.co.skill.spider.dao.domain.SUser;
import jp.co.skill.spider.ss01.form.UserForm;

public interface UserService {

	/**
	 * 登録処理
	 */
	public void register(UserForm sUserForm);

	/**
	 * 検索処理
	 */
	public List<SUser> search();
}
