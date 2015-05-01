package jp.co.skill.spider.ss01.service;

import java.util.List;

import jp.co.skill.spider.dao.SUserMapper;
import jp.co.skill.spider.dao.domain.SUser;
import jp.co.skill.spider.exception.BussinessException;
import jp.co.skill.spider.exception.SystemException;
import jp.co.skill.spider.ss01.form.UserForm;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private SUserMapper sUserMapper;

	@Autowired
	private MessageSourceAccessor messageSourceAccessor;

	@Override
	public void register(UserForm sUserForm) throws BussinessException {

		SUser s_user = new SUser();
		//sUserFormからs_userへプロパティーのコピー
		BeanUtils.copyProperties(sUserForm, s_user);
		/**
		 * 通常ログインユーザをservletContxtから取得し設定する。
		 */
		s_user.setRegUserId("admin");
		s_user.setUpdUserId("admin");

		try {

			int insCnt = sUserMapper.insert(s_user);

			if(insCnt <= 0) {
				throw new SystemException("sys.error.transaction", new String[] {"登録"});
			}

		}catch(final DuplicateKeyException ex) {

			/**
			 * DuplicateKeyException
			 * 一意制約違反が発生した場合に発生する例外。
			 * 今回のようにsequenceを使用していない場合に使う。
			 */
			throw new BussinessException("bs.error.dulicateKey", new String[] {"ユーザID"});
		}
	}

	@Override
	public List<SUser> search() {

		// 一旦全権検索のみ後で条件つける。
		/*
		 * SQLインジェクション対策はこれから入れる。
		 * 以下、参考サイト
		 * https://terasolunaorg.github.io/guideline/public_review/ArchitectureInDetail/DataAccessCommon.html
		 * http://www.tokumaru.org/d/20080601.html
		 *
		 */
		List<SUser> resultList = sUserMapper.selectList();


		return resultList;
	}

	@Override
	public SUser getUserInfo(String sUserId) {

		SUser resultData = sUserMapper.selectId(sUserId);

		return resultData;
	}

	@Override
	public void update(UserForm sUserForm) throws BussinessException {

		SUser s_user = new SUser();
		//sUserFormからs_userへプロパティーのコピー
		BeanUtils.copyProperties(sUserForm, s_user);
		s_user.setRegUserId("admin");
		s_user.setUpdUserId("admin");

		try {

			//取得するのは更新日だけでもいい。
			SUser locRecod = sUserMapper.selectIdForUpd(s_user.getsUserId());

			if(locRecod == null ||
					!locRecod.getUpdTimestamp().equals(s_user.getUpdTimestamp())) {
				//楽観ロックエラー
				throw new BussinessException("bs.error.rock");
			}

			int updCnt = sUserMapper.update(s_user);

			if(updCnt <= 0) {
				throw new SystemException("sys.error.transaction", new String[] {"更新"});
			}

		}catch(final PessimisticLockingFailureException ex) {

			/*
			 * PessimisticLockingFailureExceptionは<br/>
			 * 悲観ロックに成功しなかった場合に発生する例外。<br/>
			 * 他の処理で同一データがロックされており、<br/>
			 * ロック解放待ちのタイムアウト時間を<br/>
			 * 超えてもロックが解放されない場合に発生する。<br/>
			 */
			throw new BussinessException("bs.error.rock");
		}

	}
}
