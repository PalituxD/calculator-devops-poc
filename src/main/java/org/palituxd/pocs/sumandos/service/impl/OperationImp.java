package org.palituxd.pocs.sumandos.service.impl;

import org.palituxd.pocs.sumandos.dao.entity.OperationEntity;
import org.palituxd.pocs.sumandos.dao.repository.OperationRepository;
import org.palituxd.pocs.sumandos.service.Operation;
import org.palituxd.pocs.sumandos.service.OperationType;
import org.palituxd.pocs.sumandos.service.exception.OperationExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OperationImp implements Operation {

    @Autowired
    private OperationExceptionService operationExceptionService;

    @Autowired
    private OperationRepository operationRepository;

    public BigDecimal sum(String op1, String op2) {

        validateNumbers(OperationType.ADDITION, op1, op2);

        BigDecimal result = BigDecimal.valueOf(Double.valueOf(op1).intValue() + Double.valueOf(op2).intValue());
        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setOp(OperationType.ADDITION.name());
        operationEntity.setNumber1(new BigDecimal(op1));
        operationEntity.setNumber2(new BigDecimal(op2));
        operationEntity.setResult(result);
        operationRepository.save(operationEntity);

        return result;
    }

    private void validateNumbers(OperationType operationSource, String... numbers) {
        for (int index = 0, limit = numbers.length; index < limit; index++) {
            try {
                Double.valueOf(numbers[index]);
            } catch (NumberFormatException nfe) {
                throw operationExceptionService.throwBadNumberFormat(operationSource, numbers[index]);
            }
        }
    }
}
