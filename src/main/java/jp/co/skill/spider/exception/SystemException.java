package jp.co.skill.spider.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

/**
 * システム例外クラス<br/>
 * 常に起こるわけではないあり得ないケース。<br/>
 * RuntimeExceptionを継承している為、<br/>
 * 注意)各メソッドにてthrows句を記載する必要はない。<br/>
 * @author yuuichi
 *
 */
@Component
public class SystemException extends RuntimeException {

	private static MessageSourceAccessor messageSourceAccessor;

	public SystemException() {
		super();
	}

	/**
	 * メッセージあり
	 * @param message_key messages_ja.propertiesのキー
	 */
	public SystemException(String message_key) {

		super(messageSourceAccessor.getMessage(message_key));
	}

	/**
	 * メッセージ＋バインドあり
	 * @param message_key messages_ja.propertiesのキー
	 */
	public SystemException(String message_key, Object[] bindStrings) {

		super(messageSourceAccessor.getMessage(message_key, bindStrings));
	}

	@Autowired
	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		SystemException.messageSourceAccessor = messageSourceAccessor;
	}
}
