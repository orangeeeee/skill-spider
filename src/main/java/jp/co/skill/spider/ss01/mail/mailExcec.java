package jp.co.skill.spider.ss01.mail;

import jp.co.skill.spider.model.ReserveInfo;

public class mailExcec {

	public static void main(String[] args) {

		ReserveInfo mailData = new ReserveInfo();
		mailData.setName("user name");
		mailData.setNumber("user number");

		MailSender.handleSendMail(PremiumMailFactory::new, mailData);
	}

}
