package com.example.expenseService.service;


import com.example.expenseService.dto.ExpenseDto;
import com.example.expenseService.entity.Expense;
import com.example.expenseService.repository.ExpenseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExpenseService {

    private ExpenseRepository expenseRepository;
    private ObjectMapper objectMapper=new ObjectMapper();


    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository){
         this.expenseRepository=expenseRepository;
    }

    public boolean createExpense(ExpenseDto expenseDto){
        setCurrency(expenseDto);
        try{
            expenseRepository.save(objectMapper.convertValue(expenseDto, Expense.class));
            return true;

        }catch (Exception ex){
            return false;
        }
    }

    public boolean updateExpense(ExpenseDto expenseDto){
        Optional<Expense> expense = expenseRepository.findByUserIdAndExternalId(expenseDto.getUserId(),expenseDto.getExpenseId());
        if(expense.isEmpty()){
            return false;
        }

             Expense expense1 = expense.get();
             expense1.setCurrency(Strings.isNotBlank(expense1.getCurrency())? expenseDto.getCurrency(): expense1.getCurrency());
             expense1.setMerchant(Strings.isNotBlank(expense1.getMerchant())? expenseDto.getMerchant(): expense1.getMerchant());
             expense1.setAmount(expenseDto.getAmount());
             expenseRepository.save(expense1);

        return true;
    }

    public List<ExpenseDto> getExpense(String userId){
        List<Expense> expenseList=expenseRepository.findByUserId(userId);
        return objectMapper.convertValue(expenseList, new TypeReference<List<ExpenseDto>>() {
        });
    }

    private void setCurrency(ExpenseDto expenseDto){
           if(Objects.isNull(expenseDto.getCurrency())){
               expenseDto.setCurrency("inr");
           }
    }
}
