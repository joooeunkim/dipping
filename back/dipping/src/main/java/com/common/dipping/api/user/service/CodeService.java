package com.common.dipping.api.user.service;

import com.common.dipping.api.user.domain.entity.Code;
import com.common.dipping.api.user.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class CodeService {

    private final CodeRepository codeRepository;

    public Code findByCode(String code) {

        Code codeInfo = codeRepository.findByCode(code).orElse(null);

        codeRepository.deleteById(codeInfo.getId());

        return codeInfo;
    }
}
