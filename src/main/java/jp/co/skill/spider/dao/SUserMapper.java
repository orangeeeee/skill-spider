package jp.co.skill.spider.dao;

import jp.co.skill.spider.dao.domain.SUser;

public interface SUserMapper {

	/**
	 * ユーザTBLレコード挿入処理
	 * @param sUser
	 * @return the number of rows affected
	 */
	public int insert(SUser sUser);
}
