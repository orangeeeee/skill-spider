package jp.co.skill.spider.ss01.mail;

import jp.co.skill.spider.model.MailData;
import jp.co.skill.spider.model.ReserveInfo;
import jp.co.skill.spider.ss01.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

public class PremiumMailFactory extends AbstructMailFactory implements MailFactory {

	/** mail template id */
	private static String MAIL_TEMPLATE_NAME = "premium.txt";

	/** メールタイトル */
	private static String TITLE = "プレミアム会員様お得情報";

	@Autowired
	private UserService userService;

	@Override
	public MailData create(ReserveInfo reserveInfo) {

		System.out.println("start create");

		MailData data = new MailData();
		data.setToAddress(TO_ADDRESS);
		data.setFromAddress(reserveInfo.getMailAddress());
		data.setTitle(TITLE);
		data.setBody(createBody(reserveInfo));

		System.out.println("end create");

		//ここが今の実装だとNullになってしまう。
		userService.getTemplate("aaa");

		return data;
	}

	@Override
	public String createBody(ReserveInfo reserveInfo) {
		String template = getTemplate(MAIL_TEMPLATE_NAME);
		//template bin
		System.out.println(template);
		System.out.println("createBody");

		return "create body string";
	}

}
