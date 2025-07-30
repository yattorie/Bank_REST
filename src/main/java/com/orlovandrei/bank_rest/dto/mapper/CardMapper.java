package com.orlovandrei.bank_rest.dto.mapper;

import com.orlovandrei.bank_rest.dto.card.CardCreateRequest;
import com.orlovandrei.bank_rest.dto.card.CardResponse;
import com.orlovandrei.bank_rest.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = com.orlovandrei.bank_rest.util.CardNumberMasker.class)
public interface CardMapper {
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(target = "maskedNumber", expression = "java(CardNumberMasker.mask(entity.getNumber()))")
    CardResponse toDto(Card entity);

    @Mapping(source = "ownerId", target = "owner.id")
    Card toEntity(CardCreateRequest request);
}



