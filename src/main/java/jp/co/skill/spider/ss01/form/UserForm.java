package jp.co.skill.spider.ss01.form;

/**
 * ユーザ登録、更新、照会画面データ格納Fromクラス
 * @author yuuichi
 *
 */
public class UserForm {

	private String sUserId;

	private String password;

	private String name;

	private String eMail;

	private String introduceMyself;

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
