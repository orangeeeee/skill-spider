package jp.co.skill.spider.ss01.mail;

import jp.co.skill.spider.model.ReserveInfo;
import jp.co.skill.spider.ss01.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstructMailFactory {


	@Autowired
	private UserService userService;

	abstract String createBody(ReserveInfo reserveInfo);
	/**
	 * テンプレートファイルからテンプレートの文字列を取得する。<br>
	 * テンプレートは、メールごとに異なる想定。
	 * @return
	 */
	public String getTemplate(String templateName) {

		//userService.getTemplate(templateName);

		return "get tempalte File";
	}
}
