package com.chaskipro.backend.controller;

import com.chaskipro.backend.dto.ProfessionalSearchCriteria;
import com.chaskipro.backend.dto.ProfessionalSummaryDto;
import com.chaskipro.backend.entity.ProfessionCategory;
import com.chaskipro.backend.service.ProfessionalSearchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
@Tag(name = "Profesionales - Búsqueda", description = "Búsqueda avanzada de profesionales")
public class ProfessionalSearchController {

    private final ProfessionalSearchService professionalSearchService;

    @GetMapping("/search")
    public Page<ProfessionalSummaryDto> search(
            @RequestParam(required = false) Long communeId,
            @RequestParam(required = false) ProfessionCategory professionCategory,
            @RequestParam(required = false) BigDecimal minRating,
            Pageable pageable) {

        ProfessionalSearchCriteria criteria = ProfessionalSearchCriteria.builder()
                .communeId(communeId)
                .professionCategory(professionCategory)
                .minRating(minRating)
                .build();

        return professionalSearchService.buscar(criteria, pageable);
    }
}
