package com.common.dipping.api.dipping.service;

import com.common.dipping.api.dipping.repository.DippingHeartRepository;
import com.common.dipping.api.dipping.repository.DippingRepository;
import com.common.dipping.api.dipping.repository.DippingSongRepository;
import com.common.dipping.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DippingService {

    private final UserRepository userRepository;
    private final DippingRepository dippingRepository;
    private final DippingHeartRepository dippingHeartRepository;
    private final DippingSongRepository dippingSongRepository;
}
