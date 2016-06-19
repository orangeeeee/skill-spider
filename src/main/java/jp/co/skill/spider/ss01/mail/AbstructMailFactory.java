package jp.co.skill.spider.ss01.mail;

import jp.co.skill.spider.model.ReserveInfo;

public abstract class AbstructMailFactory {

	abstract String createBody(ReserveInfo reserveInfo);
	/**
	 * テンプレートファイルからテンプレートの文字列を取得する。<br>
	 * テンプレートは、メールごとに異なる想定。
	 * @return
	 */
	public String getTemplate(String templateName) {

		return "get tempalte File";
	}
}
