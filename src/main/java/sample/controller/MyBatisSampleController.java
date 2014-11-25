package sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sample.entity.ActorEntity;
import sample.service.MyBatisSampleService;

/**
 * MyBatisによるO/Rマッピングを使用するコントローラ.
 * <p>MyBatisにより実装されたマッパーを通じてデータベースにアクセスし、結果を画面に表示する。
 * ここではサンプル・コードをシンプルなものにするため、
 * コントローラはマッパー（設計方針にもよるが基本的にDaoレイヤに該当）を直接参照しているが、
 * 一般的にはサービス・クラスを通じて間接的に参照するかたちになる。</p>
 * <p>このサンプル・プログラムはローカル・マシン上のポート5432でPostgreSQLが稼働しており、
 * <a href="http://www.postgresqltutorial.com/postgresql-sample-database/">PostgreSQL Sample Database</a>
 * にしたがってサンプル・データベースdvdrentalが構築されていることを前提としている。
 * 接続情報のカスタマイズが必要な場合はapplication.propertiesファイルを変更する。</p>
 */
@Controller
@RequestMapping("/mybatis")
public class MyBatisSampleController {

	@Autowired
	private MyBatisSampleService sampleService;
	
	@RequestMapping({"/", "/index"})
	public String index() {
		return "/mybatis/index";
	}
	
	/**
	 * パス変数で指定されたIDを使用してactorテーブルからレコードを取得して画面表示する.
	 * 単一値を返すことを前提としたマッパーのメソッドは、該当する値が存在しなかったり、
	 * O/Rマッピングに失敗したりすると{@code null}を返す。
	 * クライアント・コード側ではこれを前提としたコーディングが必要になる（テンプレート側も同様）。
	 * @param id ID
	 * @param model モデル
	 * @return ビュー名
	 */
    @RequestMapping("/actor/{id}")
    public String actor(@PathVariable int id, Model model) {
    	final ActorEntity a = sampleService.getActorById(id);
    	model.addAttribute("found", a != null);
    	if (a != null) {
        	model.addAttribute("actor", a);
    	}
        return "/mybatis/actor";
    }
	
    /**
     * actorテーブルから全レコードを取得して画面表示する.
     * @param model モデル
     * @return ビュー名
     */
    @RequestMapping("/actors")
    public String actors(Model model) {
    	final List<ActorEntity> as = sampleService.getActorsList();
   		model.addAttribute("actors", as);
        return "/mybatis/actors";
    }
    
    @RequestMapping("/input")
    public String input(Model model) {
    	final ActorEntity actor = new ActorEntity();
    	actor.setFirstName("Foo");
    	actor.setLastName("Bar");
    	model.addAttribute("actor", actor);
    	return "/mybatis/input";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(
    		// @ModelAttributeによりフォーム情報でコマンド・オブジェクトが初期化される
    		@ModelAttribute("actor") ActorEntity actor,
    		// 初期化処理中に発生したエラーはすぐ次のBindingResultに格納される
    		BindingResult result,
    		Model model) {
    	// フィールドに設定されている値をチェック
    	if (actor.getFirstName() == null || actor.getFirstName().isEmpty() 
    			|| actor.getLastName() == null || actor.getLastName().isEmpty()) {
    		// ここでは問題があれば例外をスローしているが入力画面に戻り、
    		// バリデーション・エラーのメッセージを表示する実装のほうが親切
    		throw new IllegalArgumentException("First name and last name must be not null (and not empty).");
    	}
    	// フォームの情報からコマンド・オブジェクトを初期化した際のエラー有無をチェック
    	if (result.hasErrors()) {
    		// ここでは問題があれば例外をスローしているが入力画面に戻り、
    		// バリデーション・エラーのメッセージを表示する実装のほうが親切
    		throw new IllegalArgumentException();
    	}
    	// サービスを通じてデータを登録
    	sampleService.registerActor(actor);
    	// 処理が無事終わったら一覧ページにリダイレクト
    	return "redirect:/mybatis/actors";
    }
    
    @RequestMapping(value = "/register-and-cancel", method = RequestMethod.POST)
    public String registerAndCancel(
    		@ModelAttribute("actor") ActorEntity actor,
    		BindingResult result,
    		Model model) {
    	try {
    		sampleService.registerActorAndCancel(actor);
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}
    	return "redirect:/mybatis/actors";
    }
	
}
