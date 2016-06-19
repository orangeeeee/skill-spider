package jp.co.skill.spider.ss01.mail;

import jp.co.skill.spider.model.MailData;
import jp.co.skill.spider.model.ReserveInfo;

public class CorporateMember extends AbstructMailFactory implements MailFactory {

	/** mail template id */
	private static String MAIL_TEMPLATE_NAME = "corporate.txt";

	/** メールタイトル */
	private static String TITLE = "法人会員様お得情報";

	@Override
	public MailData create(ReserveInfo reserveInfo) {

		MailData data = new MailData();
		data.setToAddress(TO_ADDRESS);
		data.setFromAddress(reserveInfo.getMailAddress());
		data.setTitle(TITLE);
		data.setBody(createBody(reserveInfo));

		return data;
	}

	@Override
	String createBody(ReserveInfo reserveInfo) {
		String template = getTemplate(MAIL_TEMPLATE_NAME);
		//template bin
		System.out.println(template);
		return "create body string";
	}

}
