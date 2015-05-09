package jp.co.skill.spider.exception.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.skill.spider.config.ConfigLocations;
import jp.co.skill.spider.exception.SystemException;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * 例外ハンドリングクラス
 * '@ControllerAdvice'は、
 * 全てのControllerで実行したい処理を実装する。
 * @author yuuichi
 */
@ControllerAdvice
public class ExceptionController extends BasicErrorController {

	public ExceptionController() {
		super(new DefaultErrorAttributes());
	}

	private static final Logger logger = Logger.getLogger(ExceptionController.class);

	/**
	 * SystemExceptionが発生した際のハンドリングを行う。
	 * @param ex
	 * @param response
	 * @return ModelAndView
	 */
	@ExceptionHandler(SystemException.class)
	public ModelAndView systemException(SystemException ex,
			HttpServletResponse response) {

		logger.debug("Handlng SystemException - Catching: "
				+ ex.getClass().getSimpleName());

		return new ModelAndView(new RedirectView("/" + ConfigLocations.XD_ADMIN_UI_BASE_PATH + "/error/sysError.jsp"));
	}


	@Override
	@RequestMapping(value = "${error.path:/error}", produces = "text/html")
	public ModelAndView errorHtml(HttpServletRequest request) {

		Integer status = (Integer) new ServletRequestAttributes(request).getAttribute(
				"javax.servlet.error.status_code", RequestAttributes.SCOPE_REQUEST);

		/*----------------------------------------------------------
		 * status codeごとに遷移先を変える場合のサンプル
		 * 以下のようにSpringの定数を使用してもよい。
		 * if(status == HttpStatus.REQUEST_TIMEOUT.value())
		 -----------------------------------------------------------*/

		switch (status) {
			case 400:
			return new ModelAndView(new RedirectView("/" + ConfigLocations.XD_ADMIN_UI_BASE_PATH + "/error/sysError.jsp"));
			case 403:
				return new ModelAndView(new RedirectView("/" + ConfigLocations.XD_ADMIN_UI_BASE_PATH + "/error/sysError.jsp"));
			case 404:
				return new ModelAndView(new RedirectView("/" + ConfigLocations.XD_ADMIN_UI_BASE_PATH + "/error/sysError.jsp"));
			case 500:
				return new ModelAndView(new RedirectView("/" + ConfigLocations.XD_ADMIN_UI_BASE_PATH + "/error/sysError.jsp"));
		}

		return super.errorHtml(request);
	}
}
