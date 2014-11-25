package sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sample.entity.ActorEntity;
import sample.mapper.ActorMapper;

/**
 * サービス・クラスのサンプル.
 */
@Service
public class MyBatisSampleService {
	
	/**
	 * actorテーブルにアクセスするDMLを仲介するマッパー.
	 * Mybatis-Springによりマッパー・インターフェースが自動で探知され、
	 * アノテーションとマッパーXMLで指定されたクエリを実行するマッパー実装が自動生成される。
	 * 自動生成されたマッパー実装はSpringのもとで管理され{@link Autowired}指定されたフィールドに自動設定される。
	 */
	@Autowired
	private ActorMapper actorMapper;
	
	public List<ActorEntity> getActorsList() {
		return actorMapper.selectAll();
	}
	
	public ActorEntity getActorById(int id) {
		return actorMapper.selectById(id);
	}
	
	public int getNumberOfActors() {
		return actorMapper.selectCount();
	}
	
	/**
	 * {@link ActorEntity}をデータストアに登録する.
	 * このメソッド内では{@link Transactional}によりトランザクションが自動的に有効化される。
	 * このメソッドが例外をスローすると、トランザクションの間に行った変更はすべてロールバックされる。
	 * @param actor 登録するデータ
	 */
	@Transactional
	public void registerActor(ActorEntity actor) {
		if (actorMapper.insert(actor) != 1) {
			throw new RuntimeException("Insert operation is failed.");
		}
	}
	
	/**
	 * {@link ActorEntity}をデータストアに登録する.
	 * このメソッドは必ず例外をスローする。よってトランザクションは必ずロールバックされる。
	 * @param actor 登録するデータ
	 */
	@Transactional
	public void registerActorAndCancel(ActorEntity actor) {
		actorMapper.insert(actor);
		throw new RuntimeException("Insert operation is cancelled.");
	}
}
