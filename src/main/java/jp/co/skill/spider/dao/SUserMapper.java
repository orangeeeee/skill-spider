package jp.co.skill.spider.dao;

import java.util.List;

import jp.co.skill.spider.dao.domain.SUser;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

	/**
	 * sUserIdで検索
	 * @return SUser
	 */
	@Select("SELECT * FROM S_USER WHERE s_user_id = #{sUserId}")
	public SUser selectId(@Param("sUserId") String sUserId);
}
