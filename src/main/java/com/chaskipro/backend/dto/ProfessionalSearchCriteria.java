package com.chaskipro.backend.dto;

import com.chaskipro.backend.entity.ProfessionCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessionalSearchCriteria {
    private Long communeId;
    private ProfessionCategory professionCategory;
    private BigDecimal minRating;
}
