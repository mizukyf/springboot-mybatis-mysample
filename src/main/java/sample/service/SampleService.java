package sample.service;

import org.springframework.stereotype.Service;

/**
 * サービス・クラスのサンプル.
 * あくまでもサンプルなので特段の処理もせず結果を返すコードに留めている。
 */
@Service
public class SampleService {
	public String doSomeBusinessLogic(String param) {
		return param.toUpperCase();
	}
}
