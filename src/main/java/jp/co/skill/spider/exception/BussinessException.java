package jp.co.skill.spider.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class BussinessException extends Exception {

	private static MessageSourceAccessor messageSourceAccessor;

	public BussinessException() {
		super();
	}

	/**
	 * メッセージあり
	 * @param message_key messages_ja.propertiesのキー
	 */
	public BussinessException(String message_key) {

		super(messageSourceAccessor.getMessage(message_key));
	}

	/**
	 * メッセージ＋バインドあり
	 * @param message_key messages_ja.propertiesのキー
	 */
	public BussinessException(String message_key, Object[] bindStrings) {

		super(messageSourceAccessor.getMessage(message_key, bindStrings));
	}

	/**
	 * static DI
	 * １．setterを作成し、自クラスのMessageSourceAccessorに設定すること。
	 * ２．クラスに@Componentを付ける。
	 * ３．@Autowiredはsetterに付ける。
	 * 説明以下に載せました。
	 * http://qiita.com/orangeeeee/items/5e74ea31fe7d49fd96f8
	 */
	@Autowired
	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		BussinessException.messageSourceAccessor = messageSourceAccessor;
	}
}
