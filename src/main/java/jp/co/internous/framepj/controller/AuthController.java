package jp.co.internous.framepj.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import jp.co.internous.framepj.model.domain.MstUser;
import jp.co.internous.framepj.model.domain.TblCart;
import jp.co.internous.framepj.model.domain.dto.CartDto;
import jp.co.internous.framepj.model.form.UserForm;
import jp.co.internous.framepj.model.mapper.MstUserMapper;
import jp.co.internous.framepj.model.mapper.TblCartMapper;
import jp.co.internous.framepj.model.session.LoginSession;


/**
 * 認証に関する処理を行うコントローラー
 * @author インターノウス
 *
 */
@RestController
@RequestMapping("/frameweb/auth")
public class AuthController {

	@Autowired
	private LoginSession loginSession;

	@Autowired
	private MstUserMapper userMapper;

	@Autowired
	private TblCartMapper cartMapper;

	private Gson gson = new Gson();

	/**
	 * ログイン処理をおこなう
	 * @param f ユーザーフォーム
	 * @return ログインしたユーザー情報(JSON形式)
	 */
	@PostMapping("/login")
	public String login(@RequestBody UserForm f) {

		MstUser user = userMapper.findByUserNameAndPassword(
				f.getUserName(),
				f.getPassword());

		if (user == null) {
			return gson.toJson(user);
		}

		int userId = user.getId();
		int tmpUserId = loginSession.getTmpUserId();

		List<CartDto> carts = cartMapper.findByUserId(userId);
		List<CartDto> tmpCarts = cartMapper.findByUserId(tmpUserId);

		TblCart updatingCart = new TblCart();
		updatingCart.setUserId(user.getId());

		List<Integer> deleteId = new ArrayList<>();

		Set<Boolean> resultSet = new HashSet<>();

		for (CartDto cart: carts) {
			for (CartDto tmpCart: tmpCarts) {

				if (cart.getProductId() == tmpCart.getProductId()) {

					updatingCart.setProductId(cart.getProductId());
					updatingCart.setProductCount(tmpCart.getProductCount());
					int updateCount = cartMapper.update(updatingCart);

					deleteId.clear();
					deleteId.add(tmpCart.getId());
					int deleteCount = cartMapper.deleteById(deleteId);

					resultSet.add(updateCount == deleteCount && updateCount > 0);
				}
			}
		}

		int tmpCartsCount = cartMapper.findCountByUserId(tmpUserId);

		int updatedTmpCartsCount = cartMapper.updateUserId(userId, tmpUserId);

		resultSet.add(tmpCartsCount == updatedTmpCartsCount && updatedTmpCartsCount > 0);


		loginSession.setUserId(user.getId());
		loginSession.setTmpUserId(0);
		loginSession.setUserName(user.getUserName());
		loginSession.setPassword(user.getPassword());
		loginSession.setLoggedIn(true);

		return gson.toJson(user);

	}

	/**
	 * ログアウト処理をおこなう
	 * @return 空文字
	 */
	@PostMapping("/logout")
	public String logout() {

		loginSession.setUserId(0);
		loginSession.setTmpUserId(0);
		loginSession.setUserName(null);
		loginSession.setPassword(null);
		loginSession.setLoggedIn(false);

		return gson.toJson(null);

	}

	/**
	 * パスワード再設定をおこなう
	 * @param f ユーザーフォーム
	 * @return 処理後のメッセージ
	 */
	@PostMapping("/resetPassword")
	public String resetPassword(@RequestBody UserForm f) {

		userMapper.updatePassword(
				f.getUserName(),
				f.getNewPassword());

		loginSession.setPassword(f.getNewPassword());

		return "パスワードが再設定されました。";
	}
}
