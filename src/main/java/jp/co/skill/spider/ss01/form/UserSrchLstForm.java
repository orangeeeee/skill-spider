package jp.co.skill.spider.ss01.form;

import java.util.List;

import jp.co.skill.spider.dao.domain.SUser;

public class UserSrchLstForm {

	/**
	 * 照会画面の検索キーとしてPOST送信するID
	 */
	private String sUserId;

	/** 検索キーワード（ユーザID） */
	private String kwSUserId;

	/** 検索キーワード（名前） */
	private String kwName;

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

	public String getKwSUserId() {
		return kwSUserId;
	}

	public void setKwSUserId(String kwSUserId) {
		this.kwSUserId = kwSUserId;
	}

	public String getKwName() {
		return kwName;
	}

	public void setKwName(String kwName) {
		this.kwName = kwName;
	}


}
