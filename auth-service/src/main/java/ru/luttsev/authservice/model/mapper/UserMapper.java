package ru.luttsev.authservice.model.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.luttsev.authservice.model.entity.User;
import ru.luttsev.authservice.model.payload.UserPayload;
import ru.luttsev.authservice.model.payload.request.UpdateUserRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserPayload mapToPayload(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void patchUpdateUser(@MappingTarget User user, UpdateUserRequest request);
}
