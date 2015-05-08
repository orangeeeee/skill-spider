package jp.co.skill.spider.ss01.form;

import java.sql.Timestamp;

/**
 * ユーザ登録、更新、照会画面データ格納Fromクラス
 * @author yuuichi
 *
 */
public class UserForm {

	private String sUserId;

	private String password;

	private String passwordConf;

	private String name;

	private String eMail;

	private String introduceMyself;

	private Timestamp updTimestamp;

	public String getsUserId() {
		return sUserId;
	}

	public void setsUserId(String sUserId) {
		this.sUserId = sUserId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConf() {
		return passwordConf;
	}

	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getIntroduceMyself() {
		return introduceMyself;
	}

	public void setIntroduceMyself(String introduceMyself) {
		this.introduceMyself = introduceMyself;
	}

	public Timestamp getUpdTimestamp() {
		return updTimestamp;
	}

	public void setUpdTimestamp(Timestamp updTimestamp) {
		this.updTimestamp = updTimestamp;
	}

}
