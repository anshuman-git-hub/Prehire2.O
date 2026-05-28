package com.prehire.prehire2.entity;

import com.prehire.prehire2.enums.EmploymentType;
import com.prehire.prehire2.enums.JobStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "job")
public class Job extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hiring_manager_id")
    private User hiringManager;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "location", length = 255)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "employment_type", columnDefinition = "employment_type_enum")
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "job_status_enum")
    private JobStatus status = JobStatus.DRAFT;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "persona_name", length = 255)
    private String personaName;

    @Column(name = "persona_description")
    private String personaDescription;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "preferred_skills", columnDefinition = "text[]")
    private String[] preferredSkills;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "psychometric_profile", columnDefinition = "jsonb")
    private Map<String, Object> psychometricProfile;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "required_skills", columnDefinition = "text[]")
    private String[] requiredSkills;

    @Column(name = "min_experience_years")
    private Integer minExperienceYears;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "qualifications", columnDefinition = "text[]")
    private String[] qualifications;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "mandatory_requirements", columnDefinition = "text[]")
    private String[] mandatoryRequirements;
}
