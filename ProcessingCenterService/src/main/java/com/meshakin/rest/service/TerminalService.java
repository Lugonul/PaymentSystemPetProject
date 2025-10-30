package com.meshakin.rest.service;

import com.meshakin.rest.dto.id.TerminalDtoWithId;
import com.meshakin.rest.dto.no.id.TerminalDtoWithoutId;
import com.meshakin.rest.entity.Terminal;
import com.meshakin.rest.mapper.TerminalMapper;
import com.meshakin.rest.repository.TerminalRepository;
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
public class TerminalService {
    private final TerminalMapper terminalMapper;
    private final TerminalRepository TerminalRepository;

    @Transactional
    public TerminalDtoWithId create(TerminalDtoWithoutId TerminalDtoWithoutId) {
        Terminal Terminal = terminalMapper.toEntity(TerminalDtoWithoutId);
        Terminal savedTerminal = TerminalRepository.save(Terminal);

        return terminalMapper.toDtoWithId(savedTerminal);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service2:terminals", key = "#id")
    public Optional<TerminalDtoWithId> read(Long id) {

        Optional<TerminalDtoWithId> maybeTerminal = TerminalRepository.findById(id)
                .map(terminalMapper::toDtoWithId);

        return maybeTerminal;
    }

    @Transactional(readOnly = true)
    public List<TerminalDtoWithId> readAll() {
        return TerminalRepository.findAll().stream()
                .map(terminalMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service2:terminals", key = "#dto.id")
    public TerminalDtoWithId update(TerminalDtoWithId TerminalDtoWithId) {
        Terminal Terminal = TerminalRepository.findById(TerminalDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!Terminal.getVersion().equals(TerminalDtoWithId.version())) {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        terminalMapper.updateEntity(TerminalDtoWithId, Terminal);
        TerminalRepository.flush();

        return terminalMapper.toDtoWithId(Terminal);
    }

    @Transactional
    @CacheEvict(value = "service2:terminals", key = "#id")
    public void delete(Long id) {
        Terminal Terminal = TerminalRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        TerminalRepository.delete(Terminal);
    }

    @Transactional
    @CacheEvict(value = "service2:terminals", allEntries = true)
    public void deleteAll() {
        TerminalRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        TerminalRepository.dropTable();
    }
}