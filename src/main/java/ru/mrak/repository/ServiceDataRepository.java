package ru.mrak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mrak.domain.ServiceData;

@Repository
public interface ServiceDataRepository extends JpaRepository<ServiceData, String> {
}
