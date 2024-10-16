package com.example.expenseService.consumer;


import com.example.expenseService.dto.ExpenseDto;
import com.example.expenseService.service.ExpenseService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ExpenseConsumer {
    private ExpenseService expenseService;

    ExpenseConsumer(ExpenseService expenseService){
         this.expenseService=expenseService;
    }
    @KafkaListener(topics = "${spring.kafka.topic-json.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void listen(ExpenseDto expenseDto){
            try{
                expenseService.createExpense(expenseDto);
            }catch (Exception ex){
                System.out.println("Exceptions in listening the event");
            }

        }

}
