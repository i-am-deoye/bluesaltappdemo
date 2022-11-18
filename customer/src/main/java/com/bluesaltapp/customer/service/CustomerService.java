package com.bluesaltapp.customer.service;


import com.bluesaltapp.common.exception.CustomException;
import com.bluesaltapp.common.service.BaseValidationService;
import com.bluesaltapp.customer.repository.CustomerRepository;
import com.bluesaltapp.customer.model.Customer;
import com.bluesaltapp.customer.model.CustomerDTO;
import com.bluesaltapp.customer.model.CustomerVM;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService extends BaseValidationService {
    private final CustomerRepository customerRepository;

    public CustomerDTO register(CustomerVM request) throws CustomException {
        validateSaveEntity(request, "firstName", "lastName", "email", "id");

        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.saveAndFlush(customer);

        return CustomerDTO.builder()
                .email(customer.getEmail())
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .build();
    }

}
