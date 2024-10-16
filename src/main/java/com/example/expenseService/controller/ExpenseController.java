package com.example.expenseService.controller;

import com.example.expenseService.dto.ExpenseDto;
import com.example.expenseService.service.ExpenseService;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {


    private final ExpenseService expenseService;

    ExpenseController(ExpenseService expenseService){
        this.expenseService=expenseService;
    }

    @GetMapping("/expense/v1/")
    public ResponseEntity<List<ExpenseDto>> getExpenses(@PathParam("user_id")@NonNull String userId){
         try{
              List<ExpenseDto> expenseDtoList = expenseService.getExpense(userId);
              return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
         }catch (Exception ex){
              return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
         }
    }
    @PostMapping(path="/expense/v1/addExpense")
    public ResponseEntity<Boolean> addExpenses(@RequestHeader(value = "X-User-Id") @NonNull String userId, @RequestBody ExpenseDto expenseDto){
        try{
            expenseDto.setUserId(userId);
            return new ResponseEntity<>(expenseService.createExpense(expenseDto), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }



}
