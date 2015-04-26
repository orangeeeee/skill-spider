package jp.co.skill.spider.dao;

import java.util.List;

import jp.co.skill.spider.dao.domain.SUser;

public interface SUserMapper {

	/**
	 * ユーザTBLレコード挿入処理
	 * @param sUser
	 * @return the number of rows affected
	 */
	public int insert(SUser sUser);

	/**
	 * 検索画面の条件にあった検索結果を取得
	 */
	public List<SUser> selectList();
}
