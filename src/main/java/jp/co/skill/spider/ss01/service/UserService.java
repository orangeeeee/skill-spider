package jp.co.skill.spider.ss01.service;

import java.util.List;

import jp.co.skill.spider.dao.domain.SUser;
import jp.co.skill.spider.exception.BussinessException;
import jp.co.skill.spider.ss01.form.UserForm;

public interface UserService {

	/**
	 * 登録処理
	 */
	public void register(UserForm sUserForm) throws BussinessException;

	/**
	 * 更新処理
	 */
	public void update(UserForm sUserForm) throws BussinessException;

	/**
	 * 検索処理
	 */
	public List<SUser> search();

	/**
	 * 照会画面表示処理
	 */
	public SUser getUserInfo(String sUserId);

}
