package ru.luttsev.authservice.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.luttsev.authservice.model.entity.RefreshToken;
import ru.luttsev.authservice.model.payload.RefreshTokenPayload;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RefreshTokenMapper {

    @Mapping(target = "userId", source = "user.id")
    RefreshTokenPayload mapToPayload(RefreshToken refreshToken);
}
