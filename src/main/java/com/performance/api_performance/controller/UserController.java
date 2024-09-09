package com.performance.api_performance.controller;

import com.performance.api_performance.dto.UserRequestDto;
import com.performance.api_performance.entity.UserEntity;
import com.performance.api_performance.exceptions.UserRecordNotFoundException;
import com.performance.api_performance.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<UserEntity> addRecord(@RequestBody UserRequestDto userRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addRecord(userRequest));
    }
    @GetMapping("/fetchAll")
    public ResponseEntity<List<UserEntity>> fetchAllRecord(){
        return ResponseEntity.ok(userService.getAllRecords());
    }
    @GetMapping("/fetch/{userId}")
    public ResponseEntity<UserEntity> fetchParticularRecord(@PathVariable int userId) throws UserRecordNotFoundException {
        return ResponseEntity.ok(userService.fetchParticularRecord(userId));
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<UserEntity> updateRecord(@PathVariable int userId, @RequestBody UserRequestDto userRequest)
            throws UserRecordNotFoundException {
        return ResponseEntity.ok(userService.updateRecord(userId,userRequest));
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteRecord(@PathVariable int userId) throws UserRecordNotFoundException {
        return ResponseEntity.ok(userService.deleteRecord(userId));
    }
    @GetMapping("/page/{offset}/{pageSize}")
    public ResponseEntity<Page<UserEntity>> getAllRecordsWithOffSet(@PathVariable int offset, @PathVariable int pageSize){
        return ResponseEntity.ok(userService.getAllRecordsWithOffSet(offset, pageSize));
    }
    @GetMapping("/pageWithField/{offset}/{pageSize}/{fieldName}")
    public ResponseEntity<Page<UserEntity>> getAllRecordsWithOffSet(@PathVariable int offset, @PathVariable int pageSize
            ,@PathVariable String fieldName){
        return ResponseEntity.ok(userService.getAllRecordsSortWithField(offset, pageSize,fieldName));
    }
}
