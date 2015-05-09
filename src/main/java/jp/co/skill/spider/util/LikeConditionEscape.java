package jp.co.skill.spider.util;

import org.springframework.stereotype.Component;

@Component
public class LikeConditionEscape {

	public static final char LIKE_ESC_CHAR = '~';

	/**
	 * Likeエスケープ処理
	 * @param condition
	 * @param likeCondition
	 * @return
	 */
	public StringBuilder toLikeCondition(String condition,
			StringBuilder likeCondition, boolean escapeFullWithWildcards) {

		StringBuilder storingLikeCondition = likeCondition;
		if (storingLikeCondition == null) {
			storingLikeCondition = new StringBuilder();
		}
		if (condition == null) {
			return storingLikeCondition;
		}
		for (int i = 0; i < condition.length(); i++) {
			char c = condition.charAt(i);
			if (c == LIKE_ESC_CHAR) {
				storingLikeCondition.append(LIKE_ESC_CHAR);
			} else if (c == '%' || c == '_') {
				storingLikeCondition.append(LIKE_ESC_CHAR);
			} else if (escapeFullWithWildcards && (c == '＿' || c == '％')) {
				storingLikeCondition.append(LIKE_ESC_CHAR);
			}
			storingLikeCondition.append(c);
		}
		return storingLikeCondition;
	}
}
