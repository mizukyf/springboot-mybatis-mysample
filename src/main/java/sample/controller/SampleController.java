package sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sample.service.SampleService;

@Controller
public class SampleController {
	
	@Autowired
	private SampleService sampleService;
	
    /**
     * パス変数を使用したハンドラ・メソッドの例.
	 * 戻り値でビュー名を指定し、引数として受け取った{@link Model}参照にモデル属性（≒テンプレート変数）を設定している。
	 * ビュー名は"(クラスパス)/templates/..."配下のHTMLファイルのパスとして処理される。
     * モデル属性として設定するメッセージの生成には、
     * リクエスト・パスの断片から値を抽出する「パス変数」と呼ばれるテクニックを使用している。
     * @param name パス変数
     * @param model モデル
     * @return ビュー名
     */
    @RequestMapping("/hello/{name}")
    public String helloPathVariable(@PathVariable String name, Model model) {
    	model.addAttribute("message", String.format("Hello %s!", name));
        return "hello";
    }
    
    /**
     * クエリ文字列を使用したハンドラ・メソッドの例.
     * {@link #helloPathVariable(String, Model)}と異なり、
     * クエリ文字列で指定された値をメッセージの生成に使用する。
     * 加えてこのハンドラでは{@link RequestMapping}でHTTPメソッドの制約を付けている。
     * @param name クエリ文字列
     * @param model モデル
     * @return ビュー名
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String helloReqestParam(@RequestParam("name") String name, Model model) {
    	model.addAttribute("message", String.format("Hello %s!", name));
    	return "hello";
    }
    
    /**
     * サービス・クラスを使用したハンドラ・メソッドの例.
     * {@link Service}を付与して定義したサービス・クラスに
     * {@link Autowired}を付与したフィールドを通じてアクセスしている。
     * フィールドに対するオブジェクト参照の設定はSpringにより自動的に実施される。
     * サンプルのサービス・クラスのメソッドは引数として受け取った文字列を大文字化して返すだけ。
     * @param name パス変数
     * @param model モデル
     * @return ビュー名
     */
    @RequestMapping("/hello/{name}!")
    public String helloWithServiceClass(@PathVariable String name, Model model) {
    	model.addAttribute("message", String.format("Hello %s!",
    			sampleService.doSomeBusinessLogic(name)));
    	return "hello";
    }
}
