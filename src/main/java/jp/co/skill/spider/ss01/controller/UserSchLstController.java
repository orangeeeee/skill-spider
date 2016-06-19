package jp.co.skill.spider.ss01.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.skill.spider.dao.domain.SUser;
import jp.co.skill.spider.exception.SystemException;
import jp.co.skill.spider.model.ReserveInfo;
import jp.co.skill.spider.ss01.form.UserForm;
import jp.co.skill.spider.ss01.form.UserSrchLstForm;
import jp.co.skill.spider.ss01.mail.MailSender;
import jp.co.skill.spider.ss01.mail.PremiumMailFactory;
import jp.co.skill.spider.ss01.service.UserService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.MimeTypeUtils;
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

	private static final String ATTR_S_FROM_KEY = "sessionUserSrchLstForm";

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
	 * 検索を非同期処理にする予定（TODO ソートだけにするか迷っているところ。
	 * http://d.hatena.ne.jp/ryoasai/20110203/1296749495
	 */
	/**
	 * 検索ボタン押下時処理
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ss01/search", method = RequestMethod.POST)
	public ModelAndView search(@ModelAttribute UserSrchLstForm userSrchLstForm,
			HttpSession session, ModelMap model) {

		logger.debug("search starts");

		// 検索
		List<SUser> resuUserltList = userService.search(
				userSrchLstForm.getKwSUserId(), userSrchLstForm.getKwName());

		//検索結果を格納
		userSrchLstForm.setUserList(resuUserltList);

		session.setAttribute(ATTR_S_FROM_KEY, userSrchLstForm);

		if(resuUserltList == null) {

			throw new SystemException();
		}
		logger.debug("search end");

		return new ModelAndView("ss01/userSearchList", ATTR_FROM_KEY, userSrchLstForm);
	}

	/**
	 * 他の画面からの遷移時処理
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ss01/userSchList", method = RequestMethod.POST)
	public ModelAndView userSchList(@ModelAttribute UserForm userForm,
			HttpSession session, ModelMap model) {

		logger.debug("searchList starts");

		UserSrchLstForm userSrchLstForm =  (UserSrchLstForm) session.getAttribute(ATTR_S_FROM_KEY);

		/** DIの前にFactory Methodパンターンを実行しても問題ないか確認（DIでnullにならないか）。*/
		ReserveInfo mailData = new ReserveInfo();
		mailData.setName("user name");
		mailData.setNumber("user number");

		MailSender.handleSendMail(PremiumMailFactory::new, mailData);


		//検索
		List<SUser> resuUserltList = userService.search(
				userSrchLstForm.getKwSUserId(), userSrchLstForm.getKwName());

		//検索結果を格納
		userSrchLstForm.setUserList(resuUserltList);

		logger.debug("searchList end");

		return new ModelAndView("ss01/userSearchList", ATTR_FROM_KEY, userSrchLstForm);
	}

	/**
	 * CSVダウンロード
	 *
	 * ☆★☆ ダウンロード時の注意点 ☆★☆<br>
	 * Excelで開きたい場合はUTF-8は文字化けします。
	 * MS932等を仕様する必要があります。<br>
	 * @return ModelAndView
	 */
	@RequestMapping(value = "/ss01/csvDownload",
		method = RequestMethod.POST, produces = MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE + ";charset=utf-8")
	public void csvDownload(HttpSession session, HttpServletResponse response, ModelMap model) {

		logger.debug("csvDownload starts");

		UserSrchLstForm userSrchLstForm =  (UserSrchLstForm) session.getAttribute(ATTR_S_FROM_KEY);
		List<SUser> userList = userSrchLstForm.getUserList();

		try {

			final String line_separator = "\n";//System.getProperty("line.separator");
			final String fileName = "userList_" + "date" + ".csv";

			/* Excelで開くとき。（出力できない文字に注意）
			response.setContentType("application/octet-stream,charset=MS932");
			*/
			response.setContentType("application/octet-stream,charset=UTF-8");
			response.setHeader("Content-disposition",
					"attachment; filename=\"" + fileName + "\"");

			PrintWriter pw = response.getWriter();

			pw.write("ユーザID,ユーザ名" + line_separator);

			if(userList != null) {

				//画面のリスト分CSVのレコード作成する。
				for(SUser sUser : userList) {

					StringBuilder sb = new StringBuilder();

					sb.append(sUser.getUpdUserId());
					sb.append(",");
					sb.append(sUser.getName());
					sb.append(line_separator);
					pw.write(sb.toString());
				}
			}

		} catch (IOException e) {

			throw new SystemException("書き込み失敗");
		}

		logger.debug("csvDownload end");

	}
}
