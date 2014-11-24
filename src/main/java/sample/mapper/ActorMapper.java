package sample.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import sample.entity.ActorEntity;

public interface ActorMapper {
	@Select("select * from actor_id = 1")
	ActorEntity selectById1();
	ActorEntity selectById(@Param("id") int id);
	List<ActorEntity> selectAll();
}
