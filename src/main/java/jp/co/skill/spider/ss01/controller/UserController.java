package jp.co.skill.spider.ss01.controller;

import javax.validation.Valid;

import jp.co.skill.spider.ss01.form.UserForm;
import jp.co.skill.spider.ss01.validation.UserValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class UserController {


	private static final Logger logger = Logger.getLogger(UserController.class);


	@Autowired
	private UserValidator userValidator;

	/**
	 * ユーザ登録入力画面表示
	 * @return 画面
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		logger.debug("register starts");

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
		logger.debug("register end");

		return new ModelAndView("ss01/userReg", "userForm", new UserForm());
	}


	/**
	 * 入力画面表示<br>
	 * <p>
	 * 引数のModelAttribute<br>0 O
	 *
	 * @ModelAttributeで指定された"customer"がModelから取り出されます。<br>
	 * もしModelに"customer"がない場合、新たにCustomerをnewして、Modelに設定します。
	 * <br/>
	 * ★★注意点 ：'@Valid'は先頭
	 * </p>
	 * @return 画面
	 */
	@RequestMapping(value="/registerConf", method=RequestMethod.POST )
	public ModelAndView registerConf(@Valid @ModelAttribute UserForm userForm,
				BindingResult result,
			   ModelMap model) {

		logger.debug("registerConf start");

		ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userForm", userForm);

        //Validation Errorがある場合
		if (result.hasErrors()) {
			String message = "Please fil requered field.";
			modelAndView.addObject("message", message);
			modelAndView.setViewName("ss01/userReg");
			return modelAndView;
		}else {
			//success pattern
			modelAndView.setViewName("ss01/userRegConf");
		}

		logger.debug("registerConf end");

		return modelAndView;
	}
}
