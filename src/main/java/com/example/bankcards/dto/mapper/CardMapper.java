package com.example.bankcards.dto.mapper;

import com.example.bankcards.dto.card.CardCreateRequest;
import com.example.bankcards.dto.card.CardResponse;
import com.example.bankcards.entity.Card;
import com.example.bankcards.util.CardNumberMasker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = CardNumberMasker.class)
public interface CardMapper {
    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(target = "maskedNumber", expression = "java(CardNumberMasker.mask(entity.getNumber()))")
    CardResponse toDto(Card entity);

    @Mapping(source = "ownerId", target = "owner.id")
    Card toEntity(CardCreateRequest request);
}



