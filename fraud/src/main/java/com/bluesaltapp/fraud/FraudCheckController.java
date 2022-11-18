package com.bluesaltapp.fraud;


import com.bluesaltapp.common.IResponsePayload;
import com.bluesaltapp.common.ResponsePayload;
import com.bluesaltapp.fraud.service.FraudCheckService;
import com.bluesaltapp.fraud.core.response.FraudResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")
public record FraudCheckController(FraudCheckService fraudCheckService) {

    @GetMapping(path = "{customerId}")
    IResponsePayload<FraudResponse> isFraudster(@PathVariable("customerId") Integer customerId) {
        boolean isFraud = fraudCheckService.isFraudulentCustomer(customerId);
        IResponsePayload<FraudResponse> payload = new ResponsePayload();
        FraudResponse dataEntry = new FraudResponse(isFraud);
        payload.addData(dataEntry);
        return payload;
    }

}
