package jp.co.skill.spider.ss01.validation;

import jp.co.skill.spider.ss01.form.UserForm;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validationクラスを分けることで汎用的になる。
 * @author yuuichikurata
 */
@Component
public class UserValidator implements Validator {

	/**
	 * 対象のオブジェクトがこのクラスが対象とするクラスかをチェックする。
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		//
		return UserForm.class.isAssignableFrom(clazz);
	}

	/**
	 * 入力チェック
	 */
	@Override
	public void validate(Object target, Errors errors) {

		UserForm userForm = (UserForm) target;

		//名前が未入力の場合
		if(StringUtils.isEmpty(userForm.getName()) ) {
			/*
			 * rejectValue({プロパティ名}, {エラーコード}, {メッセージ引数配列},{デフォルトメッセージ}])
			 */
			errors.rejectValue("name", "error.com.required2","arere");
		}

	}
}
