package jp.co.internous.framepj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jp.co.internous.framepj.model.domain.MstDestination;
import jp.co.internous.framepj.model.mapper.MstDestinationMapper;
import jp.co.internous.framepj.model.mapper.TblCartMapper;
import jp.co.internous.framepj.model.mapper.TblPurchaseHistoryMapper;
import jp.co.internous.framepj.model.session.LoginSession;

/**
 * 決済に関する処理を行うコントローラー
 * @author インターノウス
 *
 */
@Controller
@RequestMapping("/frameweb/settlement")
public class SettlementController {
	
	@Autowired
	private MstDestinationMapper destinationMapper;
	
	@Autowired
	private TblPurchaseHistoryMapper purchaseHistoryMapper;
	
	@Autowired
	private TblCartMapper cartMapper;
	
	@Autowired
	private LoginSession loginSession;
	
	private Gson gson = new Gson();
	
	/**
	 * 宛先選択・決済画面を初期表示する。
	 * @param m 画面表示用オブジェクト
	 * @return 宛先選択・決済画面
	 */
	@RequestMapping("/")
	public String index(Model m) {
		
		int userId = loginSession.getUserId();
		
		List<MstDestination> destinations = destinationMapper.findByUserId(userId);
		m.addAttribute("loginSession", loginSession);
		m.addAttribute("destinations", destinations);
		
		return "settlement";
		
	}
	
	/**
	 * 決済処理を行う
	 * @param destinationId 宛先情報id
	 * @return true:決済処理成功、false:決済処理失敗
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/complete")
	@ResponseBody
	public boolean complete(@RequestBody String destinationId) {
		
		int userId = loginSession.getUserId();
		
		JsonObject jsonObject = gson.fromJson(destinationId, JsonObject.class);
		int destinationIdInt = jsonObject.get("destinationId").getAsInt();
		int registerResult = purchaseHistoryMapper.insert(destinationIdInt, userId);
		
		int cartCount = cartMapper.findCountByUserId(userId);
		
		if (registerResult != cartCount || registerResult == 0) {
			return false;
		}
		
		int deleteResult = cartMapper.deleteByUserId(userId);
		
		if (deleteResult != cartCount || deleteResult == 0) {
			return false;
		}
		
		return true;
		
	}
}