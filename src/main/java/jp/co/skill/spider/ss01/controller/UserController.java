package jp.co.skill.spider.ss01.controller;

import jp.co.skill.spider.ss01.form.UserForm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class UserController {


	/**
	 * ユーザ登録入力画面表示
	 * @return 画面
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

//		logger.debug("test starts");

		//NOTE
		/*------------------------------------------------------------------------
		 * <form:form id="oForm" modelAttribute="customer" method="post">
		 * 第二引数は、form:formタグのmodelAttributetタグと同一名を付けること!!
		 *-----------------------------------------------------------------------*/
		/*----------------------------------------------------------------
		 * public ModelAndView(View view(遷移Path),
         *          String modelName,
         *           Object modelObject)
		 *	Convenient constructor to take a single model object.
		 *	Parameters:
	 	 *	view - View object to render
		 *	modelName - name of the single entry in the model
		 *	modelObject - the single model object
		/---------------------------------------------------------------- */
//		logger.debug("test end");

		return new ModelAndView("ss01/userReg", "userForm", new UserForm());
	}
}
