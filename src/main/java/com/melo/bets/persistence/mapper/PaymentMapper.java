package com.melo.bets.persistence.mapper;

import com.melo.bets.domain.Payment;
import com.melo.bets.persistence.entity.PaymentEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface PaymentMapper {

    Payment toPayment(PaymentEntity paymentEntity);

    @InheritInverseConfiguration
    PaymentEntity toPaymentEntity(Payment payment);

}
