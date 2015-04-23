package jp.co.skill.spider.dao.domain;

public class SUser {

	private String sUserId;

	private String password;

	private String name;

	private String eMail;

	private String introduceMyself;

	/**
	 * コンストラクタ
	 * すべての値を格納する際に使用する。
	 *
	 * @param sUserId ユーザID
	 * @param password パスワード
	 * @param name 名前
	 * @param eMail e-mail
	 * @param introduceMyself 自己紹介
	 */
	public SUser(String sUserId, String password, String name, String eMail,
			String introduceMyself) {

		this.sUserId = sUserId;
		this.sUserId = password;
		this.sUserId = name;
		this.sUserId = eMail;
		this.sUserId = introduceMyself;
	}

	public String getSUserId() {
		return sUserId;
	}

	public void setSUserId(String sUserId) {
		this.sUserId = sUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEMail() {
		return eMail;
	}

	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	public String getIntroduceMyself() {
		return introduceMyself;
	}

	public void setIntroduceMyself(String introduceMyself) {
		this.introduceMyself = introduceMyself;
	}
}
