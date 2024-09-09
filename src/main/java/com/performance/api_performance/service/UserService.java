package com.performance.api_performance.service;

import com.performance.api_performance.dto.UserRequestDto;
import com.performance.api_performance.entity.UserEntity;
import com.performance.api_performance.exceptions.UserRecordNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    UserEntity addRecord(UserRequestDto userRequest);
    UserEntity updateRecord(int userId,UserRequestDto userRequest) throws UserRecordNotFoundException;
    List<UserEntity> getAllRecords();

    Page<UserEntity> getAllRecordsWithOffSet(int offset,int pageSize);
    Page<UserEntity> getAllRecordsSortWithField(int offset,int pageSize,String field);

    UserEntity fetchParticularRecord(int userId) throws UserRecordNotFoundException;
    String deleteRecord(int userId) throws UserRecordNotFoundException;
}
