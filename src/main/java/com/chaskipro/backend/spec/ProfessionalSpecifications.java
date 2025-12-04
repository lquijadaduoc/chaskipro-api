package com.chaskipro.backend.spec;

import com.chaskipro.backend.dto.ProfessionalSearchCriteria;
import com.chaskipro.backend.entity.EstadoValidacion;
import com.chaskipro.backend.entity.ProfessionCategory;
import com.chaskipro.backend.entity.ProfessionalProfile;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProfessionalSpecifications {

    private ProfessionalSpecifications() {
    }

    public static Specification<ProfessionalProfile> communeId(Long communeId) {
        return (root, query, cb) -> {
            if (communeId == null) {
                return null;
            }
            query.distinct(true);
            return cb.equal(root.join("coberturas").get("id"), communeId);
        };
    }

    public static Specification<ProfessionalProfile> category(ProfessionCategory category) {
        return (root, query, cb) -> category == null ? null :
                cb.equal(root.get("categoria"), category);
    }

    public static Specification<ProfessionalProfile> minRating(BigDecimal minRating) {
        return (root, query, cb) -> minRating == null ? null :
                cb.greaterThanOrEqualTo(root.get("promedioCalificacion"), minRating);
    }

    public static Specification<ProfessionalProfile> approvedOnly() {
        return (root, query, cb) -> cb.equal(root.get("estadoValidacion"), EstadoValidacion.APROBADO);
    }

    public static Specification<ProfessionalProfile> searchTerm(String searchTerm) {
        return (root, query, cb) -> {
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                return null;
            }
            String searchPattern = "%" + searchTerm.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.join("usuario").get("nombre")), searchPattern),
                    cb.like(cb.lower(root.get("titulo")), searchPattern),
                    cb.like(cb.lower(root.get("descripcion")), searchPattern),
                    cb.like(cb.lower(root.get("categoria").as(String.class)), searchPattern)
            );
        };
    }

    public static Specification<ProfessionalProfile> build(ProfessionalSearchCriteria c) {
        return Specification.where(approvedOnly())
                .and(searchTerm(c.getSearchTerm()))
                .and(communeId(c.getCommuneId()))
                .and(category(c.getProfessionCategory()))
                .and(minRating(c.getMinRating()));
    }
}
