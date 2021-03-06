package jp.co.skill.spider.ss01.controller;

import javax.servlet.http.HttpSession;

import jp.co.skill.spider.dao.domain.SUser;
import jp.co.skill.spider.ss01.form.UserForm;
import jp.co.skill.spider.ss01.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserRefController {

	private static final Logger logger = Logger.getLogger(UserRefController.class);

	private static final String ATTR_FROM_KEY = "userForm";

	private static final String SESSION_FROM_KEY = "sssionUserForm";

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/ss01/ref", method = RequestMethod.POST)
	public ModelAndView ref(@ModelAttribute UserForm userForm,
			HttpSession session, ModelMap model) {

		logger.debug("ref start");

		ModelAndView modelAndView = new ModelAndView();

		//TODO エラー制御は後ほど
		if(StringUtils.isEmpty(userForm.getsUserId())) {
			//エラー処理
			logger.debug("id is null");
			throw new jp.co.skill.spider.exception.SystemException("");
		}

		SUser resultData = userService.getUserInfo(userForm.getsUserId());

		//TODO データが取得できなかった場合のエラー処理
		if(resultData == null) {
			//データが削除された可能性がある旨のメッセージを出力する。
		}

		// success pattern
		//正常遷移の場合、HttpSessionの値を表示さる。
		session.setAttribute(SESSION_FROM_KEY, userForm);

		BeanUtils.copyProperties(resultData, userForm);

		modelAndView.addObject(ATTR_FROM_KEY, userForm);
		modelAndView.setViewName("ss01/userRef");

		logger.debug("ref end");

		return modelAndView;
	}
}
