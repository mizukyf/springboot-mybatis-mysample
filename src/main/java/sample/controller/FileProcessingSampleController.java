package sample.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileProcessingSampleController {
	
	@RequestMapping({"/", "/index"})
	public String index() {
		return "/file/index";
	}
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
	public String upload(Model model) throws IOException {
		model.addAttribute("uploaded", false);
		return "/file/upload";
	}
	
	/**
	 * アップロードされたファイルの情報を読み取って画面に表示する例.
	 * アップロードされたファイルの情報にアクセスするには{@link RequestParam}と
	 * {@link MultipartFile}の組み合わせでハンドラの引数を宣言する。
	 * 
	 * @param file アップロードされたファイルにアクセスするためのオブジェクト
	 * @param model モデル
	 * @return ビュー名
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file,
			Model model) throws IOException {
		// 実際にファイルがアップロードされたかチェック
		if (file.isEmpty()) {
			// 画面表示を制御するためのフラグを設定
			model.addAttribute("uploaded", false);
		} else {
			// 画面表示を制御するためのフラグを設定
			model.addAttribute("uploaded", true);
			// MultipartFileを通じてファイル情報にアクセス
			model.addAttribute("fileName", file.getOriginalFilename());
			model.addAttribute("firstLine", readFirstLine(file));
		}
		return "/file/upload";
	}
	
	private String readFirstLine(MultipartFile file) throws IOException {
		try {
			final BufferedReader br = new BufferedReader(
				new InputStreamReader(file.getInputStream(), Charset.forName("utf-8")));
			return br.readLine();
		} catch (IOException ex) {
			ex.printStackTrace();
			return "???";
		}
	}
	
}
