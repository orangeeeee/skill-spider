package jp.co.skill.spider.ss01.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import jp.co.skill.spider.dao.domain.SUser;
import jp.co.skill.spider.ss01.form.UserForm;
import jp.co.skill.spider.ss01.form.UserSrchLstForm;
import jp.co.skill.spider.ss01.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
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

	private static final Logger logger = Logger.getLogger(UserSchLstController.class);

	@Autowired
	private UserService userService;

	/**
	 * 検索一覧画面初期表示
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ss01/initSch", method = RequestMethod.GET)
	public ModelAndView initSch() {

		logger.debug("initSch starts");
		logger.debug("initSch end");

		return new ModelAndView("ss01/userSearchList", ATTR_FROM_KEY, new UserSrchLstForm());
	}

	/**
	 * 検索ボタン押下時処理
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ss01/search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute UserForm userForm,
			HttpSession session, ModelMap model) {

		logger.debug("search starts");

		UserSrchLstForm userSrchLstForm = new UserSrchLstForm();

		//検索
		List<SUser> resuUserltList = userService.search();

		//検索結果を格納
		userSrchLstForm.setUserList(resuUserltList);

		logger.debug("search end");

		return new ModelAndView("ss01/userSearchList", ATTR_FROM_KEY, userSrchLstForm);
	}

	/**
	 * 他の画面からの遷移時処理
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ss01/userSchList", method = RequestMethod.POST)
	public ModelAndView initOnSearch(@ModelAttribute UserForm userForm,
			HttpSession session, ModelMap model) {

		logger.debug("searchList starts");

		UserSrchLstForm userSrchLstForm = new UserSrchLstForm();

		//検索
		List<SUser> resuUserltList = userService.search();

		//検索結果を格納
		userSrchLstForm.setUserList(resuUserltList);

		logger.debug("searchList end");

		return new ModelAndView("ss01/userSearchList", ATTR_FROM_KEY, userSrchLstForm);
	}
}
