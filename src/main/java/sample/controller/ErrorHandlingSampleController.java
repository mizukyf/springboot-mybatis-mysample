package sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * エラー処理に関するハンドラ・メソッドのサンプロを実装したコントローラ.
 * クラス・レベルで{@link RequestMapping}を使用することでベースとなるパスを指定している。
 * ここで指定されたパスは、個別のハンドラ・メソッドで指定しているパスの接頭辞として連結される。
 */
@Controller
@RequestMapping("/error")
public class ErrorHandlingSampleController {
	
	@RequestMapping({"/", "/index"})
	public String index() {
		return "/error/index";
	}
	
	/**
	 * コントローラ・クラスそのものに実装された例外ハンドラを使用する例.
	 * @return (このメソッドは決して値を返さない)
	 */
	@RequestMapping("/handled-by-controller-themself")
	public String handledByControllerThemself() {
		throw new HandledByControllerThemselfException();
	}
	
	/**
	 * 別クラスで実装された例外ハンドラ（AOPにより関連付けられる）を使用する例.
	 * 例外ハンドラは{@link SampleExceptionHandlerAdvice}で実装されている。
	 * @return (このメソッドは決して値を返さない)
	 */
	@RequestMapping("/handled-by-controller-advice")
	public String handledByControllerAdvice() {
		throw new HandledByControllerAdviceException();
	}
	
	/**
	 * 例外リゾルバの実装を使用する例.
	 * ハンドラ・メソッド内で発生した例外は、
	 * コントローラ独自の例外ハンドラやAOPで関連付けられた例外ハンドラにキャッチされなかった場合、
	 * ハンドラ例外リゾルバ（{@link HandlerExceptionResolver}の実装）に処理を委ねられる。
	 * @return (このメソッドは決して値を返さない)
	 */
	@RequestMapping("/handled-by-resolver")
	public String handledByResolver() {
		throw new HandledByResolverException();
	}
	
	@ExceptionHandler(HandledByControllerThemselfException.class)
	public String handleError(Exception ex) {
		ex.printStackTrace();
		return "/error/handled-by-controller-themself";
	}
	
	public static class HandledByControllerThemselfException extends RuntimeException {
		private static final long serialVersionUID = -7831839265054767287L;
	}
	
	public static class HandledByControllerAdviceException extends RuntimeException {
		private static final long serialVersionUID = -7831839265054767287L;
	}
	
	public static class HandledByResolverException extends RuntimeException {
		private static final long serialVersionUID = -7831839265054767287L;
	}
}
