package jp.co.skill.spider.ss01.controller;

import java.util.LinkedList;

import javax.servlet.http.HttpServletResponse;

import jp.co.skill.spider.model.FileMeta;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * プロフィール画像アップロード用<br>
 * １ユーザ、１画像まで
 *
 * @author yuuichi
 *
 */
@Controller
public class ProfFileUploadController {

	/**
	 * プロフィールの画像をアップロードする。
	 *
	 * @param request
	 * @param response
	 * @return ファイル情報？
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMeta> upload(
			MultipartHttpServletRequest request, HttpServletResponse response) {

		// ここではtemp領域に維持するだけ。
		// 完了画面でサーバーに格納する。
		return null;
	}

}
