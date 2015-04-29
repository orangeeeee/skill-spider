package jp.co.skill.spider.exception;

/**
 * システム例外クラス<br/>
 * 常に起こるわけではないあり得ないケース。<br/>
 * RuntimeExceptionを継承している為、<br/>
 * 注意)各メソッドにてthrows句を記載する必要はない。<br/>
 * @author yuuichi
 *
 */
public class SystemException extends RuntimeException {

	public SystemException() {
		super();
	}

	public SystemException(String message) {
		super(message);
	}
}
