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
    public ResponseEntity<List<ExpenseDto>> getExpenses(@RequestHeader(value = "X-User-Id") @NonNull String userId){
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
    @DeleteMapping("/expense/v1/{expenseId}")
    public ResponseEntity<String> deleteExpense(@RequestHeader(value = "X-User-Id" )@NonNull String userId, @PathVariable String expenseId ){
         try{
               String message = "Expense with ID " + expenseId + " was successfully deleted.";
               expenseService.deleteExpense(expenseId);
               return new ResponseEntity<>(message, HttpStatus.OK);
         }catch (Exception ex){
              return new ResponseEntity<>("something wrong with this",HttpStatus.BAD_REQUEST);
         }
    }


    @GetMapping("/expense/v1/{expenseId}")
    public ResponseEntity<ExpenseDto> getExpenses(@RequestHeader(value = "X-User-Id" )@NonNull String userId, @PathVariable String expenseId ){
        try{
            ExpenseDto expenseDto = expenseService.getParticularExpense(expenseId);
            return new ResponseEntity<>(expenseDto,HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/expense/v1/monthly")
    public ResponseEntity<List<ExpenseDto>> getMonthlyExpenses(){
        try{
            List<ExpenseDto> expenseDtoList = expenseService.getMonthlyExpenses();
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }




}
