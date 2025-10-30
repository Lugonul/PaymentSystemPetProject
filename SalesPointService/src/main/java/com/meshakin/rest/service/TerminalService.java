package com.meshakin.rest.service;

import com.meshakin.rest.dto.TerminalDtoWithId;
import com.meshakin.rest.dto.TerminalDtoWithoutId;
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
    private final TerminalRepository terminalRepository;

    @Transactional
    public TerminalDtoWithId create(TerminalDtoWithoutId terminalDtoWithoutId) {
        Terminal Terminal = terminalMapper.toEntity(terminalDtoWithoutId);
        Terminal savedTerminal = terminalRepository.save(Terminal);

        return terminalMapper.toDtoWithId(savedTerminal);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service1:Terminals", key = "#id")
    public Optional<TerminalDtoWithId> read(Long id) {

        Optional<TerminalDtoWithId> maybeTerminal = terminalRepository.findById(id)
                .map(terminalMapper::toDtoWithId);

        return maybeTerminal;
    }

    @Transactional(readOnly = true)
    public List<TerminalDtoWithId> readAll() {
        return terminalRepository.findAll().stream()
                .map(terminalMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service1:Terminals", key = "#dto.id")
    public TerminalDtoWithId update(TerminalDtoWithId terminalDtoWithId) {
        Terminal Terminal = terminalRepository.findById(terminalDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!Terminal.getVersion().equals(terminalDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        terminalMapper.updateEntity(terminalDtoWithId, Terminal);
        terminalRepository.flush();

        return terminalMapper.toDtoWithId(Terminal);
    }

    @Transactional
    @CacheEvict(value = "service1:Terminals", key = "#id")
    public void delete(Long id) {
        Terminal Terminal = terminalRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        terminalRepository.delete(Terminal);
    }

    @Transactional
    @CacheEvict(value = "service1:Terminals", allEntries = true)
    public void deleteAll() {
        terminalRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        terminalRepository.dropTable();
    }
}