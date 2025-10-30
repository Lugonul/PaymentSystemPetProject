package com.meshakin.rest.service;

import com.meshakin.rest.dto.with.id.ClientDtoWithId;
import com.meshakin.rest.dto.without.id.ClientDtoWithoutId;
import com.meshakin.rest.entity.Client;
import com.meshakin.rest.mapper.ClientMapper;
import com.meshakin.rest.repository.ClientRepository;
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
public class ClientService {
    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    @Transactional
    public ClientDtoWithId create(ClientDtoWithoutId ClientDtoWithoutId) {
        Client Client = clientMapper.toEntity(ClientDtoWithoutId);
        Client savedClient = clientRepository.save(Client);

        return clientMapper.toDtoWithId(savedClient);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "service3:Clients", key = "#id")
    public Optional<ClientDtoWithId> read(Long id) {

        Optional<ClientDtoWithId> maybeClient = clientRepository.findById(id)
                .map(clientMapper::toDtoWithId);

        return maybeClient;
    }

    @Transactional(readOnly = true)
    public List<ClientDtoWithId> readAll() {
        return clientRepository.findAll().stream()
                .map(clientMapper::toDtoWithId)
                .collect(Collectors.toList());
    }

    @Transactional
    @CachePut(value = "service3:Clients", key = "#dto.id")
    public ClientDtoWithId update(ClientDtoWithId ClientDtoWithId) {
        Client Client = clientRepository.findById(ClientDtoWithId.id())
                .orElseThrow(EntityNotFoundException::new);

        if (!Client.getVersion().equals(ClientDtoWithId.version()))
        {
            throw new OptimisticLockException("Concurrent modification detected");
        }

        clientMapper.updateEntity(ClientDtoWithId, Client);
        clientRepository.flush();

        return clientMapper.toDtoWithId(Client);
    }

    @Transactional
    @CacheEvict(value = "service3:Clients", key = "#id")
    public void delete(Long id) {
        Client Client = clientRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        clientRepository.delete(Client);
    }

    @Transactional
    @CacheEvict(value = "service3:Clients", allEntries = true)
    public void deleteAll() {
        clientRepository.deleteAll();
    }

    @Transactional
    public void dropTable() {
        clientRepository.dropTable();
    }
}