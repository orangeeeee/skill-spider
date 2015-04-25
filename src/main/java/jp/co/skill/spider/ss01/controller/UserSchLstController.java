package jp.co.skill.spider.ss01.controller;

import jp.co.skill.spider.ss01.form.UserSrchLstForm;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * ユーザ検索画面用コントローラー
 * <p>
 * 検索画面と検索結果画面を兼ねる。
 * </p>
 * @author yuuichi
 *
 */
@Controller
public class UserSchLstController {

	private static final String ATTR_FROM_KEY = "userSrchLstForm";

	private static final Logger logger = Logger.getLogger(UserRegController.class);

	/**
	 * 検索一覧画面初期表示
	 * @return 画面
	 */
	@RequestMapping(value = "/initSch", method = RequestMethod.GET)
	public ModelAndView register() {

		logger.debug("register starts");

		UserSrchLstForm listform = new UserSrchLstForm();


		logger.debug("register end");

		return new ModelAndView("ss01/userSearchList", ATTR_FROM_KEY, new UserSrchLstForm());
	}
}
