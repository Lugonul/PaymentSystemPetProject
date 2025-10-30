package com.meshakin.rest.service;

import com.meshakin.rest.dto.ResponseCodeDtoWithId;
import com.meshakin.rest.dto.ResponseCodeDtoWithoutId;
import com.meshakin.rest.entity.ResponseCode;
import com.meshakin.rest.mapper.ResponseCodeMapper;
import com.meshakin.rest.repository.ResponseCodeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResponseCodeService {
    private final ResponseCodeMapper responseCodeMapper;
    private final ResponseCodeRepository responseCodeRepository;

    @Transactional
    public ResponseCodeDtoWithId create(ResponseCodeDtoWithoutId responseCodeDtoWithoutId) {
        ResponseCode ResponseCode = responseCodeMapper.toEntity(responseCodeDtoWithoutId);
        ResponseCode savedResponseCode = responseCodeRepository.save(ResponseCode);

        return responseCodeMapper.toDtoWithId(savedResponseCode);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service1:ResponseCodes", key = "#id")
    public Optional<ResponseCodeDtoWithId> read(Long id) {

        Optional<ResponseCodeDtoWithId> maybeResponseCode = responseCodeRepository.findById(id)
                .map(responseCodeMapper::toDtoWithId);

        return maybeResponseCode;
    }

    @Transactional(readOnly = true)
    public List<ResponseCodeDtoWithId> readAll() {
        return responseCodeRepository.findAll().stream()
                .map(responseCodeMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service1:ResponseCodes", key = "#dto.id")
    public ResponseCodeDtoWithId update(ResponseCodeDtoWithId responseCodeDtoWithId) {
        ResponseCode ResponseCode = responseCodeRepository.findById(responseCodeDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!ResponseCode.getVersion().equals(responseCodeDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        responseCodeMapper.updateEntity(responseCodeDtoWithId, ResponseCode);
        responseCodeRepository.flush();

        return responseCodeMapper.toDtoWithId(ResponseCode);
    }

    @Transactional
    @CacheEvict(value = "service1:ResponseCodes", key = "#id")
    public void delete(Long id) {
        ResponseCode ResponseCode = responseCodeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        responseCodeRepository.delete(ResponseCode);
    }

    @Transactional
    @CacheEvict(value = "service1:ResponseCodes", allEntries = true)
    public void deleteAll() {
        responseCodeRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        responseCodeRepository.dropTable();
    }
}