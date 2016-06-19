package jp.co.skill.spider.ss01.mail;

import jp.co.skill.spider.model.MailData;
import jp.co.skill.spider.model.ReserveInfo;

public interface MailFactory {

	final String TO_ADDRESS = "service@co.jp";

	MailData create(ReserveInfo mailData);

}
