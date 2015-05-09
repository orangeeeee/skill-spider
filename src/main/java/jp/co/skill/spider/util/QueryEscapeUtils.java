package jp.co.skill.spider.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryEscapeUtils {

	private static LikeConditionEscape likeConditionEscape;

	/**
	 * Likeのエスケープ処理呼び出し<br>
	 * ※１<br>
	 * Oracle、DB2の場合、escapeFullWithWildcardsをtrueにする。<br>
	 * 記以外はfalse<br>
	 * ※２<br>
	 *  ESCAPE '~'をSQLに付けること。<br>
	 *
	 * @param condition
	 * @param escapeFullWithWildcards trueの場合、全角の'％','＿'もエスケープする。
	 * @return
	 */
	public static String toLikeCondition(String condition, boolean escapeFullWithWildcards) {

		if (condition == null) {
			return null;
		}
		return likeConditionEscape.toLikeCondition(condition, new StringBuilder(),escapeFullWithWildcards).toString();
	}

	@Autowired
	public void setLikeConditionEscape(LikeConditionEscape likeConditionEscape) {
		QueryEscapeUtils.likeConditionEscape = likeConditionEscape;
	}

	/**
	 * TODO ${}を使用した埋め込み式を使用する場合
	 * SQLインジェクション対策が必要になるので注意。
	 *
	 */
}
