package com.performance.api_performance.serviceimpl;
import com.performance.api_performance.dto.UserRequestDto;
import com.performance.api_performance.entity.UserEntity;
import com.performance.api_performance.exceptions.UserRecordNotFoundException;
import com.performance.api_performance.repository.UserRepository;
import com.performance.api_performance.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    @CacheEvict(key = "'allUserRecords'",value = "UserEntities",allEntries = true)
    @CachePut(key = "#result.userId",value = "UserEntity") // inserting the new record with key as userId and value as entity and erasing the collections to make sure collection is updated.
    public UserEntity addRecord(UserRequestDto userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRequest.getUserName());
        userEntity.setMobileNumber(userRequest.getMobileNumber());
        return userRepository.save(userEntity);
    }

    @Override
    @Caching(evict = {@CacheEvict(key ="#userId",value = "UserEntity",allEntries = true),
            @CacheEvict(key = "'allUserRecords'",value = "UserEntities",allEntries = true)}) // deleting the record as the record is gonna to be updated from the collections and particular record to be updated.
    @CachePut(key = "#userId",value = "UserEntity")  // as the old record is erased inserting a new record.
    public UserEntity updateRecord(int userId, UserRequestDto userRequest) throws UserRecordNotFoundException {
        UserEntity oldRecord = userRepository.findByUserId(userId).orElseThrow(
                ()->new UserRecordNotFoundException("user record does not exists"));
        oldRecord.setUserName(userRequest.getUserName());
        oldRecord.setMobileNumber(userRequest.getMobileNumber());
        return userRepository.save(oldRecord);
    }

    @Override
    @Cacheable(key = "'allUserRecords'",value = "UserEntities")
    public List<UserEntity> getAllRecords() {
        return userRepository.findAll();
    }

    @Override
    public Page<UserEntity> getAllRecordsWithOffSet(int offset, int pageSize) {
        return userRepository.findAll(PageRequest.of(offset, pageSize));
    }

    @Override
    public Page<UserEntity> getAllRecordsSortWithField(int offset, int pageSize, String field) {
        return userRepository.findAll(PageRequest.of(offset,pageSize).withSort(Sort.by(field)));
    }

    @Override
    @Cacheable(key = "#userId",value = "UserEntity")
    public UserEntity fetchParticularRecord(int userId) throws UserRecordNotFoundException {
        return userRepository.findByUserId(userId).orElseThrow(
                ()->new UserRecordNotFoundException("user record does not exists"));
    }

    @Override
    @Caching(evict = {@CacheEvict(key ="#userId",value = "UserEntity",allEntries = true),
            @CacheEvict(key = "'allUserRecords'",value = "UserEntities",allEntries = true)}) // erasing the particular user record and erasing all records to make sure collection is updated.
    public String deleteRecord(int userId) throws UserRecordNotFoundException {
       if(!userRepository.existsByUserId(userId)){
           throw new UserRecordNotFoundException("user record does not exists");
       }
       userRepository.deleteById(userId);
       return "user : "+userId+" "+"record deleted successfully";
    }
}
