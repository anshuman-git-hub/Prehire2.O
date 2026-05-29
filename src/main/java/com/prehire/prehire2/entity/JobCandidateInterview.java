package com.prehire.prehire2.entity;

import com.prehire.prehire2.enums.InterviewStatus;
import com.prehire.prehire2.enums.InterviewType;
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
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "job_candidate_interview")
public class JobCandidateInterview extends BaseAuditEntity {

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
    @JoinColumn(name = "scheduled_by", nullable = false)
    private User scheduledBy;

    @Column(name = "interview_stage", nullable = false, length = 100)
    private String interviewStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "interview_type", nullable = false, columnDefinition = "interview_type_enum")
    private InterviewType interviewType;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Column(name = "venue")
    private String venue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, columnDefinition = "interview_status_enum")
    private InterviewStatus status = InterviewStatus.SCHEDULED;

    @Column(name = "candidate_email_sent", nullable = false)
    private Boolean candidateEmailSent = false;

    @Column(name = "panel_email_sent", nullable = false)
    private Boolean panelEmailSent = false;

    @Column(name = "notes")
    private String notes;
}
