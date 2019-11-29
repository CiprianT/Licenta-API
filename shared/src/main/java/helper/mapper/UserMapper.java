package helper.mapper;

import dto.UserDto;
import entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UserDto dto);

    List<UserDto> toDtoList(List<UserEntity> all);
    List<UserEntity> toEntityList(List<UserDto> all);

}
