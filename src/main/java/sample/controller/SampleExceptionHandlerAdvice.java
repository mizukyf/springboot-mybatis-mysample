package sample.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * 例外ハンドラ・アドバイス.
 * AOPによりコントローラに定義されたハンドラ・メソッドと結び付けられ、
 * コントローラ内で処理されなかった例外をキャッチして処理する役目を負う。
 * {@link ErrorHandlingSampleController}のJavadocも参照。
 */
@ControllerAdvice
public class SampleExceptionHandlerAdvice {

	/**
	 * 個別のコントローラで処理されなかった例外をキャッチして処理する.
	 * ただし処理対象の例外には条件を付けることができる。
	 * このメソッドの場合、{@link ErrorHandlingSampleController.HandledByControllerAdviceException}のみを対象とする。
	 * @param request HTTPサーブレット・リクエスト
	 * @param ex 例外
	 * @return モデル＆ビュー
	 */
    @ExceptionHandler(value = {ErrorHandlingSampleController.HandledByControllerAdviceException.class})
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception ex) {
    	// デバッグ目的で例外の情報を標準エラー出力に出力する
		ex.printStackTrace();
		// ビュー名とモデルをいずれも戻り値で指定するためModelAndViewを使用する
    	ModelAndView mav = new ModelAndView("/error/handled-by-controller-advice");
    	// サンプルなのでモデル属性は何も指定せずそのまま呼び出し元に返す
        return mav;
    }
}
