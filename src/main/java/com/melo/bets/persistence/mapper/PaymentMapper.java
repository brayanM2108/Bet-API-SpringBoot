package com.melo.bets.persistence.mapper;

import com.melo.bets.domain.Payment;
import com.melo.bets.persistence.entity.PaymentEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PaymentMapper {

    Payment toPayment(PaymentEntity paymentEntity);
    List<Payment> toPaymentList(List<PaymentEntity> paymentEntities);

    @InheritInverseConfiguration
    @Mapping(target = "userEntity", ignore = true)
    PaymentEntity toPaymentEntity(Payment payment);

}
