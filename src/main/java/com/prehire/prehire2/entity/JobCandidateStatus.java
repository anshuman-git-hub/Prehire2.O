package com.prehire.prehire2.entity;

import com.prehire.prehire2.enums.StageStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@Entity
@Table(
        name = "job_candidate_status",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_job_candidate_status_job_candidate",
                columnNames = {"job_id", "candidate_id"}
        )
)
@EntityListeners(AuditingEntityListener.class)
public class JobCandidateStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "current_stage_id", nullable = false)
    private Stage currentStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage_status", nullable = false, columnDefinition = "stage_status_enum")
    private StageStatus stageStatus = StageStatus.PENDING;

    @Column(name = "screening_completed", nullable = false)
    private Boolean screeningCompleted = false;

    @Column(name = "online_test_completed", nullable = false)
    private Boolean onlineTestCompleted = false;

    @Column(name = "video_interview_completed", nullable = false)
    private Boolean videoInterviewCompleted = false;

    @Column(name = "psychometric_completed", nullable = false)
    private Boolean psychometricCompleted = false;

    @Column(name = "panel_interview_completed", nullable = false)
    private Boolean panelInterviewCompleted = false;

    @Column(name = "overall_score", precision = 5, scale = 2)
    private BigDecimal overallScore;

    @Column(name = "notes")
    private String notes;

    @CreatedDate
    @Column(name = "applied_at", nullable = false, updatable = false)
    private LocalDateTime appliedAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}

