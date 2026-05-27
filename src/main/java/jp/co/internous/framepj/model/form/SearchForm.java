package jp.co.internous.framepj.model.form;

import java.io.Serializable;

/**
 * 検索フォーム
 * @author インターノウス
 *
 */
public class SearchForm  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String keywords;
	private int category;
	
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getKeywords() {
		return this.keywords;
	}
	
	public void setCategory(int category) {
		this.category = category;
	}
	public int getCategory() {
		return this.category;
	}
}
