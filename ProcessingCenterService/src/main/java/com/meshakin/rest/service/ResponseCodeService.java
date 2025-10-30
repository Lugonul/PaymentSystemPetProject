package com.meshakin.rest.service;

import com.meshakin.rest.dto.id.ResponseCodeDtoWithId;
import com.meshakin.rest.dto.no.id.ResponseCodeDtoWithoutId;
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
    private final ResponseCodeRepository ResponseCodeRepository;

    @Transactional
    public ResponseCodeDtoWithId create(ResponseCodeDtoWithoutId ResponseCodeDtoWithoutId) {
        ResponseCode ResponseCode = responseCodeMapper.toEntity(ResponseCodeDtoWithoutId);
        ResponseCode savedResponseCode = ResponseCodeRepository.save(ResponseCode);

        return responseCodeMapper.toDtoWithId(savedResponseCode);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service2:responseCodes", key = "#id")
    public Optional<ResponseCodeDtoWithId> read(Long id) {

        Optional<ResponseCodeDtoWithId> maybeResponseCode = ResponseCodeRepository.findById(id)
                .map(responseCodeMapper::toDtoWithId);

        return maybeResponseCode;
    }

    @Transactional(readOnly = true)
    public List<ResponseCodeDtoWithId> readAll() {
        return ResponseCodeRepository.findAll().stream()
                .map(responseCodeMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service2:responseCodes", key = "#dto.id")
    public ResponseCodeDtoWithId update(ResponseCodeDtoWithId ResponseCodeDtoWithId) {
        ResponseCode ResponseCode = ResponseCodeRepository.findById(ResponseCodeDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!ResponseCode.getVersion().equals(ResponseCodeDtoWithId.version())) {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        responseCodeMapper.updateEntity(ResponseCodeDtoWithId, ResponseCode);
        ResponseCodeRepository.flush();

        return responseCodeMapper.toDtoWithId(ResponseCode);
    }

    @Transactional
    @CacheEvict(value = "service2:responseCodes", key = "#id")
    public void delete(Long id) {
        ResponseCode ResponseCode = ResponseCodeRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        ResponseCodeRepository.delete(ResponseCode);
    }

    @Transactional
    @CacheEvict(value = "service2:responseCodes", allEntries = true)
    public void deleteAll() {
        ResponseCodeRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        ResponseCodeRepository.dropTable();
    }
}