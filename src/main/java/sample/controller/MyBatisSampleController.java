package sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import sample.entity.ActorEntity;
import sample.mapper.ActorMapper;

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

	/**
	 * actorテーブルにアクセスするDMLを仲介するマッパー.
	 * Mybatis-Springによりマッパー・インターフェースが自動で探知され、
	 * アノテーションとマッパーXMLで指定されたクエリを実行するマッパー実装が自動生成される。
	 * 自動生成されたマッパー実装はSpringのもとで管理され{@link Autowired}指定されたフィールドに自動設定される。
	 */
	@Autowired
	private ActorMapper actorMapper;
	
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
    String actor(@PathVariable int id, Model model) {
    	final ActorEntity a = actorMapper.selectById(id);
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
    String actor(Model model) {
    	final List<ActorEntity> as = actorMapper.selectAll();
   		model.addAttribute("actors", as);
        return "/mybatis/actors";
    }    
	
}
