package com.orlovandrei.bank_rest.dto.mapper;

import com.orlovandrei.bank_rest.dto.blockorder.BlockOrderRequest;
import com.orlovandrei.bank_rest.entity.BlockOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlockOrderMapper extends Mappable<BlockOrder, BlockOrderRequest> {
    @Mapping(source = "card.id", target = "cardId")
    @Mapping(source = "requestedBy.id", target = "requestedById")
    BlockOrderRequest toDto(BlockOrder entity);

    @Mapping(source = "cardId", target = "card.id")
    @Mapping(source = "requestedById", target = "requestedBy.id")
    BlockOrder toEntity(BlockOrderRequest dto);
}
