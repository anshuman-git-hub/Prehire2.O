-- =============================================================
-- PreHire 2.0 — Base Schema DDL
-- Database: PostgreSQL
-- Version: 2.0
-- =============================================================
-- Run order matters due to FK dependencies.
-- Execute this entire file once to set up your dev environment.
-- =============================================================

-- Enable UUID generation
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- =============================================================
-- DROP TABLES (for clean re-runs, reverse FK order)
-- =============================================================
DROP TABLE IF EXISTS psy_test_submission CASCADE;
DROP TABLE IF EXISTS job_candidate_psy_test CASCADE;
DROP TABLE IF EXISTS psy_test_master CASCADE;
DROP TABLE IF EXISTS job_candidate_test_skill_result CASCADE;
DROP TABLE IF EXISTS job_candidate_question_response CASCADE;
DROP TABLE IF EXISTS job_candidate_test CASCADE;
DROP TABLE IF EXISTS question_bank CASCADE;
DROP TABLE IF EXISTS test_skill_mapping CASCADE;
DROP TABLE IF EXISTS test_master CASCADE;
DROP TABLE IF EXISTS job_candidate_screening_submission CASCADE;
DROP TABLE IF EXISTS job_screening_questions CASCADE;
DROP TABLE IF EXISTS job_candidate_interview CASCADE;
DROP TABLE IF EXISTS job_panel CASCADE;
DROP TABLE IF EXISTS job_candidate_feedback CASCADE;
DROP TABLE IF EXISTS job_candidate_status CASCADE;
DROP TABLE IF EXISTS feature CASCADE;
DROP TABLE IF EXISTS job CASCADE;
DROP TABLE IF EXISTS candidate CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS stage CASCADE;
DROP TABLE IF EXISTS tenant CASCADE;
DROP TABLE IF EXISTS prehire_role CASCADE;

-- Drop custom types
DROP TYPE IF EXISTS subscription_plan_enum CASCADE;
DROP TYPE IF EXISTS employment_type_enum CASCADE;
DROP TYPE IF EXISTS job_status_enum CASCADE;
DROP TYPE IF EXISTS stage_status_enum CASCADE;
DROP TYPE IF EXISTS question_type_enum CASCADE;
DROP TYPE IF EXISTS interview_type_enum CASCADE;
DROP TYPE IF EXISTS interview_status_enum CASCADE;
DROP TYPE IF EXISTS recommendation_enum CASCADE;
DROP TYPE IF EXISTS email_status_enum CASCADE;
DROP TYPE IF EXISTS psy_test_status_enum CASCADE;
DROP TYPE IF EXISTS feature_key_enum CASCADE;

-- =============================================================
-- ENUMS
-- =============================================================

CREATE TYPE subscription_plan_enum AS ENUM ('FREE', 'STARTER', 'PRO', 'ENTERPRISE');
CREATE TYPE employment_type_enum  AS ENUM ('FULL_TIME', 'PART_TIME', 'CONTRACT', 'INTERNSHIP');
CREATE TYPE job_status_enum       AS ENUM ('DRAFT', 'ACTIVE', 'PAUSED', 'CLOSED');
CREATE TYPE stage_status_enum     AS ENUM ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'FAILED', 'SKIPPED');
CREATE TYPE question_type_enum    AS ENUM ('YES_NO', 'SHORT_TEXT', 'NUMERIC', 'MULTIPLE_CHOICE');
CREATE TYPE interview_type_enum   AS ENUM ('ONLINE', 'OFFLINE', 'TELEPHONIC');
CREATE TYPE interview_status_enum AS ENUM ('SCHEDULED', 'COMPLETED', 'CANCELLED', 'RESCHEDULED');
CREATE TYPE recommendation_enum   AS ENUM ('HIRE', 'REJECT', 'HOLD');
CREATE TYPE email_status_enum     AS ENUM ('PENDING', 'DELIVERED', 'FAILED');
CREATE TYPE psy_test_status_enum  AS ENUM ('PENDING', 'IN_PROGRESS', 'COMPLETED');
CREATE TYPE feature_key_enum      AS ENUM ('SCREENING', 'ONLINE_TEST', 'VIDEO_INTERVIEW', 'PSYCHOMETRIC', 'PANEL_INTERVIEW', 'CHATBOT');

-- =============================================================
-- 1. prehire_role  (no dependencies)
-- =============================================================
CREATE TABLE prehire_role (
    id              BIGSERIAL   PRIMARY KEY,
    name            VARCHAR(100) NOT NULL UNIQUE,
    display_name    VARCHAR(100) NOT NULL,
    description     TEXT,
    permissions     JSONB,
    is_system_role  BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE prehire_role IS 'Master role definitions managed by PreHire admins. Replaces the role enum on User.';

-- =============================================================
-- 2. tenant  (no dependencies)
-- =============================================================
CREATE TABLE tenant (
    id                  UUID                    PRIMARY KEY DEFAULT gen_random_uuid(),
    name                VARCHAR(255)            NOT NULL,
    slug                VARCHAR(100)            NOT NULL UNIQUE,
    logo_url            TEXT,
    admin_email         VARCHAR(255)            NOT NULL,
    subscription_plan   subscription_plan_enum  NOT NULL DEFAULT 'FREE',
    is_active           BOOLEAN                 NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP               NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP               NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE tenant IS 'Company/organisation onboarded to PreHire. All entities (except Candidate) are scoped to a tenant.';

-- =============================================================
-- 3. stage  (no dependencies — lookup table)
-- =============================================================
CREATE TABLE stage (
    id              BIGSERIAL   PRIMARY KEY,
    name            VARCHAR(100) NOT NULL UNIQUE,
    display_name    VARCHAR(100) NOT NULL,
    sequence_order  INTEGER     NOT NULL,
    description     TEXT,
    is_terminal     BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE stage IS 'Lookup table for all hiring pipeline stages. Replaces current_stage enum on JobCandidateStatus.';

-- =============================================================
-- 4. user  (depends on: tenant, prehire_role)
-- =============================================================
CREATE TABLE "user" (
    id              UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id       UUID        REFERENCES tenant(id) ON DELETE SET NULL,
    role_id         BIGINT      NOT NULL REFERENCES prehire_role(id),
    email           VARCHAR(255) NOT NULL,
    password_hash   TEXT        NOT NULL,
    first_name      VARCHAR(100) NOT NULL,
    last_name       VARCHAR(100) NOT NULL,
    avatar_url      TEXT,
    is_active       BOOLEAN     NOT NULL DEFAULT TRUE,
    last_login_at   TIMESTAMP,
    created_at      TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP   NOT NULL DEFAULT NOW(),
    UNIQUE (email, tenant_id)
);

COMMENT ON TABLE "user" IS 'All users across all roles. role_id references prehire_role instead of enum. tenant_id nullable for candidates.';
CREATE INDEX idx_user_tenant_id ON "user"(tenant_id);
CREATE INDEX idx_user_role_id   ON "user"(role_id);
CREATE INDEX idx_user_email     ON "user"(email);

-- =============================================================
-- 5. candidate  (depends on: user, tenant)
-- =============================================================
CREATE TABLE candidate (
    id                  UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id             UUID        NOT NULL UNIQUE REFERENCES "user"(id) ON DELETE CASCADE,
    tenant_id           UUID        REFERENCES tenant(id) ON DELETE SET NULL,
    phone               VARCHAR(20),
    location            VARCHAR(255),
    linkedin_url        TEXT,
    cv_url              TEXT,
    skills              TEXT[],
    years_of_experience INTEGER,
    current_title       VARCHAR(255),
    summary             TEXT,
    created_at          TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE candidate IS 'Extended profile for candidate users. tenant_id = NULL means platform-level profile. Filled = tenant-specific copy when candidate applies.';
CREATE INDEX idx_candidate_user_id   ON candidate(user_id);
CREATE INDEX idx_candidate_tenant_id ON candidate(tenant_id);

-- =============================================================
-- 6. job  (depends on: tenant, user)
-- =============================================================
CREATE TABLE job (
    id                      UUID                    PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id               UUID                    NOT NULL REFERENCES tenant(id) ON DELETE CASCADE,
    created_by              UUID                    NOT NULL REFERENCES "user"(id),
    hiring_manager_id       UUID                    REFERENCES "user"(id),
    title                   VARCHAR(255)            NOT NULL,
    description             TEXT                    NOT NULL,
    department              VARCHAR(100),
    location                VARCHAR(255),
    employment_type         employment_type_enum,
    status                  job_status_enum         NOT NULL DEFAULT 'DRAFT',
    published_at            TIMESTAMP,
    closed_at               TIMESTAMP,
    -- Persona fields (merged in from Personas table)
    persona_name            VARCHAR(255),
    persona_description     TEXT,
    preferred_skills        TEXT[],
    psychometric_profile    JSONB,
    -- Requirement fields (merged in from Requirement table)
    required_skills         TEXT[],
    min_experience_years    INTEGER,
    qualifications          TEXT[],
    mandatory_requirements  TEXT[],
    created_at              TIMESTAMP               NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP               NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE job IS 'Job opening. Personas and Requirements merged directly into this table (v2.0 change).';
CREATE INDEX idx_job_tenant_id ON job(tenant_id);
CREATE INDEX idx_job_status    ON job(status);

-- =============================================================
-- 7. job_candidate_status  (depends on: job, candidate, tenant, stage)
-- =============================================================
CREATE TABLE job_candidate_status (
    id                          UUID                PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id                      UUID                NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    candidate_id                UUID                NOT NULL REFERENCES candidate(id) ON DELETE CASCADE,
    tenant_id                   UUID                NOT NULL REFERENCES tenant(id),
    current_stage_id            BIGINT              NOT NULL REFERENCES stage(id),
    stage_status                stage_status_enum   NOT NULL DEFAULT 'PENDING',
    screening_completed         BOOLEAN             NOT NULL DEFAULT FALSE,
    online_test_completed       BOOLEAN             NOT NULL DEFAULT FALSE,
    video_interview_completed   BOOLEAN             NOT NULL DEFAULT FALSE,
    psychometric_completed      BOOLEAN             NOT NULL DEFAULT FALSE,
    panel_interview_completed   BOOLEAN             NOT NULL DEFAULT FALSE,
    overall_score               DECIMAL(5,2),
    notes                       TEXT,
    applied_at                  TIMESTAMP           NOT NULL DEFAULT NOW(),
    updated_at                  TIMESTAMP           NOT NULL DEFAULT NOW(),
    UNIQUE (job_id, candidate_id)
);

COMMENT ON TABLE job_candidate_status IS 'Central pipeline table. Tracks each candidate through a job pipeline. current_stage_id references stage lookup table.';
CREATE INDEX idx_jcs_job_id       ON job_candidate_status(job_id);
CREATE INDEX idx_jcs_candidate_id ON job_candidate_status(candidate_id);
CREATE INDEX idx_jcs_stage_id     ON job_candidate_status(current_stage_id);

-- =============================================================
-- 8. feature  (depends on: tenant)
-- NOTE: Pre-seeded. Not part of v1 release APIs.
-- =============================================================
CREATE TABLE feature (
    id          UUID                PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id   UUID                NOT NULL REFERENCES tenant(id) ON DELETE CASCADE,
    feature_key feature_key_enum    NOT NULL,
    is_enabled  BOOLEAN             NOT NULL DEFAULT FALSE,
    config      JSONB,
    enabled_at  TIMESTAMP,
    created_at  TIMESTAMP           NOT NULL DEFAULT NOW(),
    UNIQUE (tenant_id, feature_key)
);

COMMENT ON TABLE feature IS 'Feature flags per tenant. Pre-seeded. Not exposed via APIs in this release.';

-- =============================================================
-- 9. test_master  (depends on: tenant, job, user)
-- Owner: Ajinkya
-- =============================================================
CREATE TABLE test_master (
    id                  UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id           UUID        NOT NULL REFERENCES tenant(id) ON DELETE CASCADE,
    job_id              UUID        NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    test_name           VARCHAR(255) NOT NULL,
    description         TEXT,
    test_type           VARCHAR(50) NOT NULL,   -- MCQ / Coding / Aptitude
    duration_minutes    INTEGER     NOT NULL,
    total_marks         INTEGER     NOT NULL,
    passing_marks       INTEGER     NOT NULL,
    passing_criteria    DECIMAL(5,2) NOT NULL,  -- minimum % to pass
    status              VARCHAR(50) NOT NULL DEFAULT 'Draft', -- Draft / Published
    created_by          UUID        NOT NULL REFERENCES "user"(id),
    created_at          TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE test_master IS 'Online test configuration per job. Owner: Ajinkya.';
CREATE INDEX idx_test_master_job_id    ON test_master(job_id);
CREATE INDEX idx_test_master_tenant_id ON test_master(tenant_id);

-- =============================================================
-- 10. test_skill_mapping  (depends on: test_master)
-- Owner: Ajinkya
-- NOTE: skill_id references SkillMaster — to be added when skill module is built
-- =============================================================
CREATE TABLE test_skill_mapping (
    id                      UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    test_id                 UUID        NOT NULL REFERENCES test_master(id) ON DELETE CASCADE,
    skill_name              VARCHAR(255) NOT NULL,  -- denormalized until SkillMaster is built
    number_of_questions     INTEGER     NOT NULL,
    passing_marks           DECIMAL(5,2) NOT NULL,
    weightage_percentage    DECIMAL(5,2),
    created_at              TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE test_skill_mapping IS 'Skill-to-test mapping with question count and weightage. Owner: Ajinkya. skill_id FK deferred until SkillMaster table is created.';

-- =============================================================
-- 11. question_bank  (depends on: test_skill_mapping)
-- Owner: Ajinkya
-- =============================================================
CREATE TABLE question_bank (
    id                  UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    test_skill_mapping_id UUID      REFERENCES test_skill_mapping(id) ON DELETE SET NULL,
    skill_name          VARCHAR(255) NOT NULL,  -- denormalized until SkillMaster is built
    question_text       TEXT        NOT NULL,
    question_type       VARCHAR(50) NOT NULL,  -- MCQ / Coding / Aptitude
    difficulty_level    VARCHAR(50),           -- Easy / Medium / Hard
    marks               INTEGER     NOT NULL,
    created_at          TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE question_bank IS 'Repository of assessment questions. Owner: Ajinkya.';

-- =============================================================
-- 12. job_candidate_test  (depends on: candidate, job, test_master)
-- Owner: Ajinkya
-- =============================================================
CREATE TABLE job_candidate_test (
    id                  UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    candidate_id        UUID        NOT NULL REFERENCES candidate(id) ON DELETE CASCADE,
    job_id              UUID        NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    test_id             UUID        NOT NULL REFERENCES test_master(id),
    test_link           TEXT        NOT NULL,
    email_sent_status   BOOLEAN     NOT NULL DEFAULT FALSE,
    email_sent_at       TIMESTAMP,
    overall_result      VARCHAR(50),            -- Pass / Fail / Pending
    test_status         VARCHAR(50) NOT NULL DEFAULT 'Assigned', -- Assigned / Started / Submitted / Evaluated
    final_score         INTEGER     NOT NULL DEFAULT 0,
    created_at          TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE job_candidate_test IS 'Candidate test assignment tracking. Owner: Ajinkya.';
CREATE INDEX idx_jct_candidate_id ON job_candidate_test(candidate_id);
CREATE INDEX idx_jct_job_id       ON job_candidate_test(job_id);

-- =============================================================
-- 13. job_candidate_question_response  (depends on: job_candidate_test, candidate, job, test_skill_mapping, question_bank)
-- Owner: Ajinkya
-- =============================================================
CREATE TABLE job_candidate_question_response (
    id                      UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    candidate_test_id       UUID        NOT NULL REFERENCES job_candidate_test(id) ON DELETE CASCADE,
    candidate_id            UUID        NOT NULL REFERENCES candidate(id),
    job_id                  UUID        NOT NULL REFERENCES job(id),
    test_skill_mapping_id   UUID        REFERENCES test_skill_mapping(id),
    question_id             UUID        NOT NULL REFERENCES question_bank(id),
    selected_answer         TEXT,
    is_correct              BOOLEAN,
    obtained_marks          DECIMAL(5,2),
    answered_at             TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE job_candidate_question_response IS 'Candidate responses per question in online test. Owner: Ajinkya.';

-- =============================================================
-- 14. job_candidate_test_skill_result  (depends on: candidate, job, test_master, test_skill_mapping)
-- Owner: Ajinkya
-- =============================================================
CREATE TABLE job_candidate_test_skill_result (
    id                      UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    candidate_id            UUID        NOT NULL REFERENCES candidate(id),
    job_id                  UUID        NOT NULL REFERENCES job(id),
    test_id                 UUID        NOT NULL REFERENCES test_master(id),
    test_skill_mapping_id   UUID        NOT NULL REFERENCES test_skill_mapping(id),
    skill_name              VARCHAR(255) NOT NULL,  -- denormalized
    total_questions         INTEGER     NOT NULL,
    attempted_questions     INTEGER     NOT NULL DEFAULT 0,
    correct_answers         INTEGER     NOT NULL DEFAULT 0,
    wrong_answers           INTEGER     NOT NULL DEFAULT 0,
    total_marks             DECIMAL(6,2) NOT NULL,
    percentage              DECIMAL(5,2) NOT NULL,
    passing_marks           DECIMAL(5,2) NOT NULL,
    result_status           VARCHAR(50) NOT NULL,   -- Pass / Fail
    evaluated_at            TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE job_candidate_test_skill_result IS 'Skill-wise test result summary per candidate. Owner: Ajinkya.';

-- =============================================================
-- 15. psy_test_master  (depends on: tenant, job, user)
-- Owner: Anshuman
-- =============================================================
CREATE TABLE psy_test_master (
    id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id   UUID        NOT NULL REFERENCES tenant(id) ON DELETE CASCADE,
    job_id      UUID        NOT NULL UNIQUE REFERENCES job(id) ON DELETE CASCADE,
    created_by  UUID        NOT NULL REFERENCES "user"(id),
    name        VARCHAR(255) NOT NULL,
    min_passing JSONB,           -- e.g. {"EXT": 3, "AGR": 2}
    total_ques  INTEGER     NOT NULL,
    traits      TEXT[]      NOT NULL, -- Big Five traits configured for this test
    time_limit  INTEGER,             -- in minutes, NULL = no limit
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE psy_test_master IS 'Psychometric test configuration per job (1 config per job). Owner: Anshuman.';
CREATE INDEX idx_psy_test_master_job_id    ON psy_test_master(job_id);
CREATE INDEX idx_psy_test_master_tenant_id ON psy_test_master(tenant_id);

-- =============================================================
-- 16. job_candidate_psy_test  (depends on: tenant, job, candidate, psy_test_master)
-- Owner: Anshuman
-- =============================================================
CREATE TABLE job_candidate_psy_test (
    id              UUID                PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id       UUID                NOT NULL REFERENCES tenant(id),
    job_id          UUID                NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    candidate_id    UUID                NOT NULL REFERENCES candidate(id) ON DELETE CASCADE,
    test_link       TEXT                NOT NULL,
    email_sent      BOOLEAN             NOT NULL DEFAULT FALSE,
    email_sent_at   TIMESTAMP,
    email_status    email_status_enum   NOT NULL DEFAULT 'PENDING',
    status          psy_test_status_enum NOT NULL DEFAULT 'PENDING',
    started_at      TIMESTAMP,
    submitted_at    TIMESTAMP,
    trait_scores    JSONB,              -- e.g. {"EXT": {"score": 4.2, "percentage": 84}}
    overall_score   DECIMAL(5,2),
    result_status   VARCHAR(50),        -- Pass / Fail
    evaluated_at    TIMESTAMP,
    created_at      TIMESTAMP           NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP           NOT NULL DEFAULT NOW(),
    UNIQUE (job_id, candidate_id)
);

COMMENT ON TABLE job_candidate_psy_test IS 'Tracks email, link, submission and result for candidate psychometric test per job. Owner: Anshuman.';
CREATE INDEX idx_jcpt_candidate_id ON job_candidate_psy_test(candidate_id);
CREATE INDEX idx_jcpt_job_id       ON job_candidate_psy_test(job_id);

-- =============================================================
-- 17. psy_test_submission  (depends on: job_candidate_psy_test, tenant, job, candidate)
-- Owner: Anshuman
-- NOTE: question_id and variation_id reference Question/Variation tables
--       to be added when those tables are shared from NeonDB
-- =============================================================
CREATE TABLE psy_test_submission (
    id              UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    psy_test_id     UUID        NOT NULL REFERENCES job_candidate_psy_test(id) ON DELETE CASCADE,
    tenant_id       UUID        NOT NULL REFERENCES tenant(id),
    job_id          UUID        NOT NULL REFERENCES job(id),
    candidate_id    UUID        NOT NULL REFERENCES candidate(id),
    question_id     UUID        NOT NULL,    -- FK to Question table (pending — to be added)
    variation_id    UUID        NOT NULL,    -- FK to Variation table (pending — to be added)
    response        VARCHAR(50) NOT NULL,    -- strongly_agree | agree | neutral | disagree | strongly_disagree
    answered_at     TIMESTAMP   NOT NULL DEFAULT NOW(),
    created_at      TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE psy_test_submission IS 'Per-question candidate response for psychometric test. question_id and variation_id FKs pending until Question/Variation tables are added. Owner: Anshuman.';
CREATE INDEX idx_pts_psy_test_id  ON psy_test_submission(psy_test_id);
CREATE INDEX idx_pts_candidate_id ON psy_test_submission(candidate_id);

-- =============================================================
-- 18. job_screening_questions  (depends on: tenant, job, user)
-- Owner: Tanish
-- =============================================================
CREATE TABLE job_screening_questions (
    id              UUID                PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id       UUID                NOT NULL REFERENCES tenant(id) ON DELETE CASCADE,
    job_id          UUID                NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    created_by      UUID                NOT NULL REFERENCES "user"(id),
    question_text   TEXT                NOT NULL,
    question_type   question_type_enum  NOT NULL,
    options         TEXT[],             -- only for multiple_choice
    expected_answer TEXT,
    sequence_order  INTEGER             NOT NULL,
    created_at      TIMESTAMP           NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP           NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE job_screening_questions IS 'Custom screening questions per job defined by recruiter. Owner: Tanish.';
CREATE INDEX idx_jsq_job_id    ON job_screening_questions(job_id);
CREATE INDEX idx_jsq_tenant_id ON job_screening_questions(tenant_id);

-- =============================================================
-- 19. job_candidate_screening_submission  (depends on: tenant, job, job_screening_questions, candidate)
-- Owner: Tanish
-- =============================================================
CREATE TABLE job_candidate_screening_submission (
    id                  UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    tenant_id           UUID        NOT NULL REFERENCES tenant(id),
    job_id              UUID        NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    question_id         UUID        NOT NULL REFERENCES job_screening_questions(id) ON DELETE CASCADE,
    candidate_id        UUID        NOT NULL REFERENCES candidate(id) ON DELETE CASCADE,
    answer_text         TEXT,       -- for yes_no / short_text / numeric
    selected_options    TEXT[],     -- for multiple_choice
    submitted_at        TIMESTAMP   NOT NULL DEFAULT NOW(),
    created_at          TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE job_candidate_screening_submission IS 'Candidate answers to screening questions. Owner: Tanish.';
CREATE INDEX idx_jcss_candidate_id ON job_candidate_screening_submission(candidate_id);
CREATE INDEX idx_jcss_job_id       ON job_candidate_screening_submission(job_id);

-- =============================================================
-- 20. job_candidate_feedback  (depends on: job, candidate, stage, user)
-- Owner: Priyanshi
-- =============================================================
CREATE TABLE job_candidate_feedback (
    id              UUID                PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id          UUID                NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    candidate_id    UUID                NOT NULL REFERENCES candidate(id) ON DELETE CASCADE,
    stage_id        BIGINT              NOT NULL REFERENCES stage(id),
    given_by        UUID                NOT NULL REFERENCES "user"(id),
    rating          DECIMAL(3,2),       -- e.g. 4.20 / 5.00
    recommendation  recommendation_enum NOT NULL,
    feedback_text   TEXT,
    created_at      TIMESTAMP           NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP           NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE job_candidate_feedback IS 'Feedback by HM/Panel/Recruiter for a candidate at a specific stage. Owner: Priyanshi.';
CREATE INDEX idx_jcf_job_id       ON job_candidate_feedback(job_id);
CREATE INDEX idx_jcf_candidate_id ON job_candidate_feedback(candidate_id);

-- =============================================================
-- 21. job_panel  (depends on: job, user)
-- Owner: Priyanshi
-- =============================================================
CREATE TABLE job_panel (
    id              UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id          UUID        NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    panel_user_id   UUID        NOT NULL REFERENCES "user"(id),
    assigned_by     UUID        REFERENCES "user"(id),   -- recruiter who assigned
    created_at      TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP   NOT NULL DEFAULT NOW(),
    UNIQUE (job_id, panel_user_id)
);

COMMENT ON TABLE job_panel IS 'Panel members assigned to a job. Owner: Priyanshi.';
CREATE INDEX idx_jp_job_id ON job_panel(job_id);

-- =============================================================
-- 22. job_candidate_interview  (depends on: job, candidate, user)
-- Owner: Priyanshi
-- =============================================================
CREATE TABLE job_candidate_interview (
    id                      UUID                    PRIMARY KEY DEFAULT gen_random_uuid(),
    job_id                  UUID                    NOT NULL REFERENCES job(id) ON DELETE CASCADE,
    candidate_id            UUID                    NOT NULL REFERENCES candidate(id) ON DELETE CASCADE,
    scheduled_by            UUID                    NOT NULL REFERENCES "user"(id),
    interview_stage         VARCHAR(100)            NOT NULL,  -- Technical / HR / Final
    interview_type          interview_type_enum     NOT NULL,
    scheduled_at            TIMESTAMP               NOT NULL,
    duration_minutes        INTEGER,
    meeting_link            TEXT,
    venue                   TEXT,
    status                  interview_status_enum   NOT NULL DEFAULT 'SCHEDULED',
    candidate_email_sent    BOOLEAN                 NOT NULL DEFAULT FALSE,
    panel_email_sent        BOOLEAN                 NOT NULL DEFAULT FALSE,
    notes                   TEXT,
    created_at              TIMESTAMP               NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP               NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE job_candidate_interview IS 'Interview scheduling for candidates. Owner: Priyanshi.';
CREATE INDEX idx_jci_job_id       ON job_candidate_interview(job_id);
CREATE INDEX idx_jci_candidate_id ON job_candidate_interview(candidate_id);

-- =============================================================
-- SEED DATA
-- =============================================================

-- Seed: prehire_role
INSERT INTO prehire_role (name, display_name, description, is_system_role) VALUES
    ('prehire_admin',   'PreHire Admin',    'Super admin with full platform access', TRUE),
    ('tenant_admin',    'Tenant Admin',     'Admin for a specific company/tenant',   TRUE),
    ('recruiter',       'Recruiter',        'Creates jobs, manages candidates',      TRUE),
    ('hiring_manager',  'Hiring Manager',   'Reviews candidates, gives feedback',    TRUE),
    ('panel',           'Panel Member',     'Conducts interviews, gives feedback',   TRUE),
    ('candidate',       'Candidate',        'Job applicant on the platform',         TRUE);

-- Seed: stage (pipeline stages in order)
INSERT INTO stage (name, display_name, sequence_order, description, is_terminal) VALUES
    ('applied',           'Applied',            1,  'Candidate has applied to the job',           FALSE),
    ('screening',         'Screening',          2,  'Recruiter screening via questions',          FALSE),
    ('online_test',       'Online Test',        3,  'Aptitude / MCQ / Coding assessment',         FALSE),
    ('psychometric',      'Psychometric Test',  4,  'Big Five personality assessment',            FALSE),
    ('video_interview',   'Video Interview',    5,  'Async or live video interview',              FALSE),
    ('panel_interview',   'Panel Interview',    6,  'Interview with assigned panel members',      FALSE),
    ('offer',             'Offer',              7,  'Offer extended to candidate',                FALSE),
    ('hired',             'Hired',              8,  'Candidate accepted and hired',               TRUE),
    ('rejected',          'Rejected',           9,  'Candidate rejected from pipeline',           TRUE);

-- =============================================================
-- END OF SCHEMA
-- =============================================================
