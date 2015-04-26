package jp.co.skill.spider.ss01.controller;

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

	private static final Logger logger = Logger.getLogger(UserRegController.class);

	private static final String ATTR_FROM_KEY = "userForm";

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/ss01/ref", method = RequestMethod.POST)
	public ModelAndView ref(@ModelAttribute UserForm userForm, ModelMap model) {

		logger.debug("ref start");

		ModelAndView modelAndView = new ModelAndView();

		if(StringUtils.isEmpty(userForm.getsUserId())) {
			//エラー処理
		}

		SUser resultData = userService.initRef(userForm.getsUserId());

		BeanUtils.copyProperties(resultData, userForm);

		modelAndView.addObject(ATTR_FROM_KEY, userForm);
		modelAndView.setViewName("ss01/userRef");

		logger.debug("ref end");

		return modelAndView;
	}

}
