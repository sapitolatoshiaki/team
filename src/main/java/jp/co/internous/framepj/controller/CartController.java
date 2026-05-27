package jp.co.internous.framepj.controller;

import java.lang.reflect.Type;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import jp.co.internous.framepj.model.domain.TblCart;
import jp.co.internous.framepj.model.domain.dto.CartDto;
import jp.co.internous.framepj.model.form.CartForm;
import jp.co.internous.framepj.model.mapper.TblCartMapper;
import jp.co.internous.framepj.model.session.LoginSession;


/**
 * カート情報に関する処理のコントローラー
 * @author インターノウス
 *
 */
@Controller
@RequestMapping("/frameweb/cart")
public class CartController {

	private Gson gson = new Gson();

	@Autowired
	private LoginSession loginSession;

	@Autowired
	private TblCartMapper cartMapper;

	/**
	 * カート画面を初期表示する。
	 * @param m 画面表示用オブジェクト
	 * @return カート画面
	 */
	@RequestMapping("/")
	public String index(Model m) {

		List<CartDto> carts;
		int userId;

		if (loginSession.isLoggedIn()) {
			userId = loginSession.getUserId();
		} else {
			userId = loginSession.getTmpUserId();
		}

		carts = cartMapper.findByUserId(userId);

		for (CartDto cartDto: carts) {
			cartDto.setSubtotal(cartDto.getPrice() * cartDto.getProductCount());
		}

		m.addAttribute("loginSession", loginSession);
		m.addAttribute("carts", carts);
		return "cart";
	}

	/**
	 * カートに追加処理を行う
	 * @param f カート情報のForm
	 * @param m 画面表示用オブジェクト
	 * @return カート画面
	 */
	@RequestMapping("/add")
	public String addCart(CartForm f, Model m) {

		TblCart cart = new TblCart(f);
		int userId;

		if (loginSession.isLoggedIn()) {
			userId = loginSession.getUserId();
		} else {
			userId = loginSession.getTmpUserId();
		}

		cart.setUserId(userId);

		int dbCount = cartMapper.findCountByUserIdAndProductId(cart.getUserId(), cart.getProductId());
		if (dbCount != 0) {
			cartMapper.update(cart);
		} else {
			cartMapper.insert(cart);
		}

		return "redirect:/frameweb/cart/";
	}

	/**
	 * カート情報を削除する
	 * @param checkedIdList 選択したカート情報のIDリスト
	 * @return true:削除成功、false:削除失敗
	 */
	@PostMapping("/delete")
	@ResponseBody
	public boolean deleteCart(@RequestBody String checkedIdList) {

		JsonObject jsonObject = gson.fromJson(checkedIdList, JsonObject.class);

		Type type = TypeToken.getParameterized(List.class, Integer.class).getType();

		List<Integer> checkedIds =
				gson.fromJson(jsonObject.get("checkedIdList"), type);

		int result = cartMapper.deleteById(checkedIds);

		return result > 0;
	}
}
