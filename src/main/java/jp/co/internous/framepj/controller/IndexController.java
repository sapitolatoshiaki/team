package jp.co.internous.framepj.controller;

import java.security.SecureRandom;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.internous.framepj.model.domain.MstCategory;
import jp.co.internous.framepj.model.domain.MstProduct;
import jp.co.internous.framepj.model.form.SearchForm;
import jp.co.internous.framepj.model.mapper.MstCategoryMapper;
import jp.co.internous.framepj.model.mapper.MstProductMapper;
import jp.co.internous.framepj.model.session.LoginSession;

/**
 * 商品検索に関する処理を行うコントローラー
 * @author インターノウス
 *
 */
@Controller
@RequestMapping("/frameweb")
public class IndexController {
	@Autowired
	private MstCategoryMapper categoryMapper;

	@Autowired
	private MstProductMapper productMapper;

	@Autowired
	private LoginSession loginSession;

	/**
	 * トップページを初期表示する。
	 * @param m 画面表示用オブジェクト
	 * @return トップページ
	 */
	@RequestMapping("/")
	public String index(Model m) {

		List<MstCategory> categories = categoryMapper.find();
		m.addAttribute("categories", categories);

		List<MstProduct> products = productMapper.find();
		m.addAttribute("products", products);

		if (loginSession.isLoggedIn() == false 
				&& loginSession.getTmpUserId() == 0) {
			SecureRandom r = new SecureRandom();
			int tmpUserId = -100000000 - r.nextInt(900000000);
			loginSession.setTmpUserId(tmpUserId);
		}else {
			m.addAttribute("loginSession", loginSession);
		}
		return "index";
	}

	/**
	 * 検索処理を行う
	 * @param f 検索用フォーム
	 * @param m 画面表示用オブジェクト
	 * @return トップページ
	 */
	@RequestMapping("/searchItem")
	public String searchItem(SearchForm f, Model m) {

		String words = f.getKeywords();
		String[] keywords = null;
		String returnWord = null;

		if (!words.isEmpty()) {
			f.setKeywords(words.replaceAll("　", " ").trim().replaceAll(" +", " "));
			returnWord =  f.getKeywords();
			keywords = f.getKeywords().split(" ");
		}
		m.addAttribute("keywords",returnWord);

		List<MstProduct> products = null;
		if (f.getCategory() > 0) {
			products = productMapper.findByCategoryAndProductName(f.getCategory(), keywords);
		} else if (keywords != null) {
			products = productMapper.findByProductName(keywords);
		} else {
			products = productMapper.find();
		}
		m.addAttribute("products",products);

		List<MstCategory> categories = categoryMapper.find();
		m.addAttribute("categories", categories);
		m.addAttribute("selected",f.getCategory());

		if (loginSession.isLoggedIn()) {
			m.addAttribute("loginSession", loginSession);
		}
		return "index";
	}
}
