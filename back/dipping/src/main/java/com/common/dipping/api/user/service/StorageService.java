package com.common.dipping.api.user.service;

import com.common.dipping.api.user.domain.entity.Storage;
import com.common.dipping.api.user.repository.StorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final StorageRepository storageRepository;

}
