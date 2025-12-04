package com.chaskipro.backend.service;

import com.chaskipro.backend.dto.ProfessionalSearchCriteria;
import com.chaskipro.backend.dto.ProfessionalSummaryDto;
import com.chaskipro.backend.entity.ProfessionalProfile;
import com.chaskipro.backend.repository.ProfessionalProfileRepository;
import com.chaskipro.backend.spec.ProfessionalSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfessionalSearchService {

    private final ProfessionalProfileRepository professionalProfileRepository;
    private final ProfessionalMapper professionalMapper;

    @Transactional(readOnly = true)
    public Page<ProfessionalSummaryDto> buscar(ProfessionalSearchCriteria criteria, Pageable pageable) {
        Specification<ProfessionalProfile> spec = ProfessionalSpecifications.build(criteria);
        return professionalProfileRepository.findAll(spec, pageable)
                .map(professionalMapper::toSummary);
    }
}
