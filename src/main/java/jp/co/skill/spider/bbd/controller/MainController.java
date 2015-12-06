package jp.co.skill.spider.bbd.controller;

import jp.co.skill.spider.bbd.form.BbDemoForm;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * あとで名前を変更するかも
 * とりあえず動かす環境作る。
 * @author yuuichi
 *
 */
@Controller
public class MainController {

	private static final String ATTR_FROM_KEY = "bbDemoForm";

	private static final Logger logger = Logger.getLogger(MainController.class);


	/**
	 * テスト画面初期表示
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/bbd/testIni", method = RequestMethod.GET)
	public ModelAndView testIni() {

		logger.debug("backbone-demo/init starts");







		logger.debug("backbone-demo/init end");

		return new ModelAndView("bb/demo", ATTR_FROM_KEY, new BbDemoForm());
	}
}
