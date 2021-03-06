package jp.co.skill.spider.ss01.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import jp.co.skill.spider.exception.BussinessException;
import jp.co.skill.spider.model.Car;
import jp.co.skill.spider.ss01.common.imple.VehicleDriver;
import jp.co.skill.spider.ss01.form.UserForm;
import jp.co.skill.spider.ss01.service.UserService;
import jp.co.skill.spider.ss01.validation.UserValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 * 登録、登録確認、登録完了画面コントローラー
 * @author yuuichi
 */
@Controller
public class UserRegController {

	private static final Logger logger = Logger.getLogger(UserRegController.class);

	private static final String ATTR_FROM_KEY = "userForm";

	private static final String SESSION_FROM_KEY = "sssionUserForm";

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private UserService userService;


	/**
	 * validation処理<br/>
	 * バインド処理<br/>
	 * <p>
	 * 補足（Spring Frameworkの思想）
	 * バリデーションがビジネスロジックを考慮すべきか否かは大きな問題だ。<br/>
	 * これには賛成、反対の両方の答えがあり、Springでは<br/>
	 * そのどちらも除外しないバリデーション(とデータバインディング)のための設計を提示している。<br/>
	 * バリデーションは仕様的にウェブ層と結びつけられないようにすべきであり、簡単にローカライズできるべきであり、<br/>
	 * どんなバリデータでもプラグイン可能とすべきだ。上記を考慮して、<br/>
	 * Springでは基本的で尚且つアプリケーションの全ての層で利用可能なValidatorインタフェースを実現した。
	 * データバインディングはユーザの入力をアプリケーション(もしくはユーザの入力を処理するために使うオブジェクト)の<br/>
	 * ドメインモデルに動的にバインドできるようにする便利なものだ。Springではまさにそれを行う、<br/>
	 * いわゆるDataBinderが用意されている。ValidatorとDataBinderはバリデーションパッケージをなし、<br/>
	 * これは主にMVCフレームワークで用いられるが、特にこれだけに限定されたものではない。
	 * </p>
	 * @param binder
	 */
	@InitBinder("userForm")
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	/**
	 * ユーザ登録入力画面表示
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ss01/register", method = RequestMethod.POST)
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

		return new ModelAndView("ss01/userReg", ATTR_FROM_KEY, new UserForm());
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
	@RequestMapping(value = "/ss01/registerConf", method = RequestMethod.POST)
	public ModelAndView registerConf(@Valid @ModelAttribute UserForm userForm,
			BindingResult result, HttpSession session, ModelMap model) {

		logger.debug("registerConf start");

		ModelAndView modelAndView = new ModelAndView();

		// Validation Errorがある場合
		if (result.hasErrors()) {

			String message = "Please fil requered field.";
			//エラーの場合、Formの値を表示。
			//TODO 直接設定するの良くないんでしたっけ？
			modelAndView.addObject(ATTR_FROM_KEY, userForm);
			modelAndView.addObject("message", message);
			modelAndView.setViewName("ss01/userReg");
			return modelAndView;
		}

		// success pattern
		//正常遷移の場合、HttpSessionの値を表示さる。
		session.setAttribute(SESSION_FROM_KEY, userForm);

		modelAndView.setViewName("ss01/userRegConf");

		logger.debug("registerConf end");

		return modelAndView;
	}

	/**
	 * 入力画面に戻る<br>
	 * <p>
	 * sessionの値をFormに詰め替える。
	 * </p>
	 * @param userForm
	 * @param session
	 * @param model
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ss01/backRegister", method = RequestMethod.POST)
	public ModelAndView backRegister(@ModelAttribute UserForm userForm,
			HttpSession session, ModelMap model) {

		logger.debug("backRegister start");

		ModelAndView modelAndView = new ModelAndView();

		UserForm sUserForm = (UserForm)session.getAttribute(SESSION_FROM_KEY);

		modelAndView.addObject(ATTR_FROM_KEY, sUserForm);
		modelAndView.setViewName("ss01/userReg");

		logger.debug("backRegister end");

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
	@RequestMapping(value = "/ss01/registerComp", method = RequestMethod.POST)
	public ModelAndView registerComp(@ModelAttribute UserForm userForm,
			HttpSession session, ModelMap model) throws Exception {

		logger.debug("registerComp start");

		ModelAndView modelAndView = new ModelAndView();

		UserForm sUserForm = (UserForm)session.getAttribute(SESSION_FROM_KEY);


		handleVehicle(Car::new);

		try {

			userService.register(sUserForm);

		}catch (BussinessException ex) {

			modelAndView.addObject("message", ex.getMessage());
			//TODO 前の画面に戻る。
			modelAndView.setViewName("ss01/userRegConf");
		}

		//登録確認画面用のSessionを削除。
		session.setAttribute(SESSION_FROM_KEY, null);

		modelAndView.setViewName("ss01/userRegComp");

		logger.debug("registerComp end");

		return modelAndView;
	}

	static void handleVehicle(VehicleDriver vDriver) {
		System.out.println("Handling a new vehicle...");
		vDriver.driveVehicle();
		vDriver.cleanVehicle();
	}
}
