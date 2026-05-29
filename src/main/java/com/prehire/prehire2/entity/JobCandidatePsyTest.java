package com.prehire.prehire2.entity;

import com.prehire.prehire2.enums.EmailStatus;
import com.prehire.prehire2.enums.PsyTestStatus;
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
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(
        name = "job_candidate_psy_test",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_job_candidate_psy_test_job_candidate",
                columnNames = {"job_id", "candidate_id"}
        )
)
public class JobCandidatePsyTest extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(name = "test_link", nullable = false)
    private String testLink;

    @Column(name = "email_sent", nullable = false)
    private Boolean emailSent = false;

    @Column(name = "email_sent_at")
    private LocalDateTime emailSentAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "email_status", nullable = false, columnDefinition = "email_status_enum")
    private EmailStatus emailStatus = EmailStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "psy_test_status_enum")
    private PsyTestStatus status = PsyTestStatus.PENDING;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "trait_scores", columnDefinition = "jsonb")
    private Map<String, Object> traitScores;

    @Column(name = "overall_score", precision = 5, scale = 2)
    private BigDecimal overallScore;

    @Column(name = "result_status", length = 50)
    private String resultStatus;

    @Column(name = "evaluated_at")
    private LocalDateTime evaluatedAt;
}
