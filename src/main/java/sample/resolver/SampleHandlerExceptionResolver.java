package sample.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * {@link HandlerExceptionResolver}のサンプル実装クラス.
 * ハンドラ（コントローラのメソッド）から例外がスローされた場合に処理が異常されるオブジェクト。
 * {@link @ExceptionHandler}が付与されたメソッドとちがい、
 * 処理対象の例外は特定のコントローラに限定されない。
 */
@Component
public class SampleHandlerExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3) {
		arg3.printStackTrace();
		final ModelAndView mav = new ModelAndView("/error/handled-by-resolver");
		return mav;
	}

}
