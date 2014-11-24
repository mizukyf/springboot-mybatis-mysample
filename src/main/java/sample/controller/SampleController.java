package sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SampleController {
	
	/**
	 * 最も単純なハンドラ・メソッドの例.
	 * 戻り値でビュー名を指定し、引数として受け取った{@link Model}参照にモデル属性（≒テンプレート変数）を設定している。
	 * ビュー名は"(クラスパス)/templates/..."配下のHTMLファイルのパスとして処理される。
	 * テンプレート側で存在しないモデル属性を参照しようとするとエラーが発生するので注意。
	 * @param model モデル
	 * @return ビュー名
	 */
    @RequestMapping("/hello")
    String hello(Model model) {
    	model.addAttribute("message", "hello world!");
    	return "hello";
    }

    /**
     * パス変数を使用したハンドラ・メソッドの例.
     * {@link #hello(Model)}と同じく戻り値としてビュー名を返すタイプのハンドラ。
     * ただしモデル属性として設定するメッセージの生成には、
     * リクエスト・パスの断片から値を抽出する「パス変数」と呼ばれるテクニックを使用している。
     * @param name パス変数
     * @param model モデル
     * @return ビュー名
     */
    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name, Model model) {
    	model.addAttribute("message", String.format("Hello %s!", name));
        return "hello";
    }

}
