package jp.co.skill.spider.ss01.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import jp.co.skill.spider.dao.domain.SUser;
import jp.co.skill.spider.ss01.form.UserForm;
import jp.co.skill.spider.ss01.service.UserService;
import jp.co.skill.spider.ss01.validation.UserValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserUpdController {

	private static final Logger logger = Logger.getLogger(UserUpdController.class);

	private static final String ATTR_FROM_KEY = "userForm";

	private static final String SESSION_FROM_KEY = "sssionUserForm";

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/ss01/update", method = RequestMethod.POST)
	public ModelAndView update(@ModelAttribute UserForm userForm,
			HttpSession session, ModelMap model) {

		logger.debug("update start");

		ModelAndView modelAndView = new ModelAndView();

		if(StringUtils.isEmpty(userForm.getsUserId())) {
			//エラー処理

			logger.debug("id is null");
		}

		SUser resultData = userService.getUserInfo(userForm.getsUserId());


		//TODO データが取得できなかった場合のエラー処理
		if(resultData == null) {

		}

		BeanUtils.copyProperties(resultData, userForm);

		modelAndView.addObject(ATTR_FROM_KEY, userForm);
		modelAndView.setViewName("ss01/userUpd");

		logger.debug("update end");

		return modelAndView;
	}



	/**
	 * 確認画面表示<br>
	 * <p>
	 * 引数のModelAttribute<br>
	 *<br/>
	 * '@ModelAttribute'で指定された"customer"がModelから取り出されます。<br>
	 * もしModelに"customer"がない場合、新たにCustomerをnewして、Modelに設定します。
	 * <br/>
	 * ★☆★★ 注意点 ★★☆★<br/>
	 * １．'@Valid'は先頭<br/>
	 * ２．BindingResultは、Validation対象のオブジェクトの直後（次）の引数として定義すること。<br/>
	 * そうでない場合、404エラーとなる。
	 * </p>
	 * @param userForm
	 * @param result
	 * @param session
	 * @param model
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ss01/updConf", method = RequestMethod.POST)
	public ModelAndView updateConf(@Valid @ModelAttribute UserForm userForm,
			BindingResult result, HttpSession session, ModelMap model) {

		logger.debug("updateConf start");

		ModelAndView modelAndView = new ModelAndView();

		// Validation Errorがある場合
		if (result.hasErrors()) {

			String message = "Please fil requered field.";
			//エラーの場合、Formの値を表示。
			//TODO 直接設定するの良くないんでしたっけ？
			modelAndView.addObject(ATTR_FROM_KEY, userForm);
			modelAndView.addObject("message", message);
			modelAndView.setViewName("ss01/userUpd");
			return modelAndView;
		}

		// success pattern

		//UserIdを設定
		UserForm sUserForm = (UserForm)session.getAttribute(SESSION_FROM_KEY);
		userForm.setsUserId(sUserForm.getsUserId());
		//正常遷移の場合、HttpSessionの値を表示さる。
		session.setAttribute(SESSION_FROM_KEY, userForm);

		modelAndView.setViewName("ss01/userUpdConf");

		logger.debug("updateConf end");

		return modelAndView;
	}

	/**
	 * 更新画面に戻る<br>
	 * <p>
	 * sessionの値をFormに詰め替える。
	 * </p>
	 * @param userForm
	 * @param session
	 * @param model
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ss01/backUpd", method = RequestMethod.POST)
	public ModelAndView backUpdate(@ModelAttribute UserForm userForm,
			HttpSession session, ModelMap model) {

		logger.debug("backUpdate start");

		ModelAndView modelAndView = new ModelAndView();

		UserForm sUserForm = (UserForm)session.getAttribute(SESSION_FROM_KEY);

		modelAndView.addObject(ATTR_FROM_KEY, sUserForm);
		modelAndView.setViewName("ss01/userUpd");

		logger.debug("backUpdate end");

		return modelAndView;
	}

	/**
	 * 登録処理後完了画面表示<br>
	 * <p>
	 * sessionの値をDBにinsertする。
	 * 成功した場合、完了画面に遷移する。
	 * </p>
	 * @param userForm
	 * @param session
	 * @param model
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ss01/updComp", method = RequestMethod.POST)
	public ModelAndView updateComp(@ModelAttribute UserForm userForm,
			HttpSession session, ModelMap model) {

		logger.debug("updateComp start");

		ModelAndView modelAndView = new ModelAndView();

		UserForm sUserForm = (UserForm)session.getAttribute(SESSION_FROM_KEY);

		userService.update(sUserForm);

		//登録確認画面用のSessionを削除。
		session.setAttribute(SESSION_FROM_KEY, null);

		modelAndView.setViewName("ss01/userUpdComp");

		logger.debug("updateComp end");

		return modelAndView;
	}

}
