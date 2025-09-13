package com.melo.bets.infrastructure.persistence.mapper;

import com.melo.bets.domain.dto.payment.PaymentCreateDto;
import com.melo.bets.domain.dto.payment.PaymentDto;
import com.melo.bets.infrastructure.persistence.entity.PaymentEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PaymentMapper {

    // Entity to DTO
    @Mapping(target = "userName", source = "userEntity.name")
    PaymentDto toPayment(PaymentEntity paymentEntity);
    List<PaymentDto> toPaymentList(List<PaymentEntity> paymentEntities);

    // Entity to CreateDto
    PaymentCreateDto toPaymentCreateDto(PaymentEntity payment);

    // CreateDto to Entity
    @Mapping(target = "paymentType", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userEntity", ignore = true)
    @Mapping(target = "paymentDate", ignore = true)
    PaymentEntity toPaymentCreateEntity(PaymentCreateDto payment);

    // DTO to Entity
    @InheritInverseConfiguration
    @Mapping(target = "userEntity", ignore = true)
    PaymentEntity toPaymentEntity(PaymentDto payment);

}
