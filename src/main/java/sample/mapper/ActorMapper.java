package sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import sample.entity.ActorEntity;

/**
 * actorテーブルに対するDMLを仲介するマッパー.
 * Javaソースコードとしてはインターフェースを宣言するのみで、DMLの実体は同名のXMLファイルで記述する。
 * インターフェースとXMLの情報を元にMyBatisフレームワークにより自動的にマッパーの実装が用意される。
 */
public interface ActorMapper {
	ActorEntity selectById(@Param("id") int id);
	List<ActorEntity> selectAll();
	int selectCount();
	int insert(ActorEntity actor);
}
