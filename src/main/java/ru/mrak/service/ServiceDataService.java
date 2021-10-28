package ru.mrak.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mrak.model.entity.ServiceData;
import ru.mrak.model.enumeration.ServiceDataKeysEnum;
import ru.mrak.repository.ServiceDataRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceDataService {

    private final Logger log = LoggerFactory.getLogger(WordService.class);

    private final ServiceDataRepository serviceDataRepository;

    @Transactional(readOnly = true)
    public ServiceData getByKey(ServiceDataKeysEnum key) {
        log.debug("Get service data by key {}", key);
        return serviceDataRepository.findById(key.name()).orElseThrow(() -> new RuntimeException("Key not found: " + key));
    }

    public void save(ServiceDataKeysEnum key, String value) {
        log.debug("Save service data. Key: {}, value {}", key.name(), value);
        serviceDataRepository.save(new ServiceData(key.name(), value));
    }

}
