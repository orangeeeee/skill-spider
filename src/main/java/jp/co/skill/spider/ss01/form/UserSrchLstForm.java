package jp.co.skill.spider.ss01.form;

import java.util.List;

import jp.co.skill.spider.dao.domain.SUser;

public class UserSrchLstForm {

	/**
	 * 照会画面の検索キーとしてPOST送信するID
	 */
	private String sUserId;

	private List<SUser> userList;

	public List<SUser> getUserList() {
		return userList;
	}

	public void setUserList(List<SUser> userList) {
		this.userList = userList;
	}
	public String getsUserId() {
		return sUserId;
	}

	public void setsUserId(String sUserId) {
		this.sUserId = sUserId;
	}

}
