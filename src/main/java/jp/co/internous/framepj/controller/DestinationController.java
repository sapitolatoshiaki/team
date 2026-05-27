package jp.co.internous.framepj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import jp.co.internous.framepj.model.domain.MstDestination;
import jp.co.internous.framepj.model.domain.MstUser;
import jp.co.internous.framepj.model.form.DestinationForm;
import jp.co.internous.framepj.model.mapper.MstDestinationMapper;
import jp.co.internous.framepj.model.mapper.MstUserMapper;
import jp.co.internous.framepj.model.session.LoginSession;

/**
 * 宛先情報に関する処理のコントローラー
 * @author インターノウス
 *
 */
@Controller
@RequestMapping("/frameweb/destination")
public class DestinationController {

	@Autowired
	private MstUserMapper userMapper;
	
	@Autowired
	private MstDestinationMapper destinationMapper;
	
	@Autowired
	private LoginSession loginSession;
	
	private Gson gson = new Gson();
	
	/**
	 * 宛先画面を初期表示する
	 * @param m 画面表示用オブジェクト
	 * @return 宛先画面
	 */
	@RequestMapping("/")
	public String index(Model m) {

		String userName = loginSession.getUserName();
		String password = loginSession.getPassword();
		
		MstUser user = userMapper.findByUserNameAndPassword(userName,password);
		
		m.addAttribute("loginSession", loginSession);
		m.addAttribute("user",user);
	
		return "destination";
	}
	
	/**
	 * 宛先情報を削除する
	 * @param destinationId 宛先情報ID
	 * @return true:削除成功、false:削除失敗
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/delete")
	@ResponseBody
	public boolean delete(@RequestBody String destinationId) {
	
		JsonObject jsonObject = gson.fromJson(destinationId, JsonObject.class);

		int id = gson.fromJson(jsonObject.get("destinationId"), int.class);

		int result = destinationMapper.logicalDeleteById(id);

		return result > 0;
	}

	/**
	 * 宛先情報を登録する
	 * @param f 宛先情報のフォーム
	 * @return 宛先情報id
	 */
	@PostMapping("/register")
	@ResponseBody
	public String register(@RequestBody DestinationForm f) {
	
		int userId = loginSession.getUserId();

		MstDestination destination = new MstDestination(f);

		destination.setUserId(userId);

		destinationMapper.insert(destination);

		int destinationId = destination.getId();

		return gson.toJson(destinationId);
	}
}

