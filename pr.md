# Pull request drafts

## Design pull request

**Issue:** `Refs #N`

**Traces to:** `UC-STU-deactivate-student`, `docs/design/stu.md`

### Why

Student deactivation was only an incidental enabled-flag update with no confirmation, lifecycle boundary, or complete handling of preserved records.

### What changed

Firmed team removal, historical peer-evaluation retention, immediate JWT access denial, already-inactive rejection, section-scoped authorization, and confirmation cancellation in the requirements and `BR-student-lifecycle`.

### How it was verified

- **Verified:** Questions test against the approved design: dedicated endpoint, authorization rule, active-account enforcement, historical report behavior, and extension coverage are specified in `docs/design/stu.md`.
- **Not verified:** No implementation or automated tests exist in the design pull request.

### Reviewer focus

Review the lifecycle policy, especially removal of team membership, historical report inclusion, and the shared active-account enforcement change.

## Build pull request

**Issue:** `Closes #N`

**Traces to:** `UC-STU-deactivate-student`, `docs/design/stu.md`

**Design pull request:** Local design commit `d7863d4` (no GitHub PR created).

### Why

Implement the approved student deactivation workflow without deleting the student's account or submitted records.

### What changed

Implement the dedicated action, confirmation UI, authorization, immediate active-account enforcement, team removal, and evaluation/reminder/report boundaries from `docs/design/stu.md`.

### How it was verified

- **Verified:** `cd backend && bash mvnw -q -Dtest=StudentServiceTest,EvaluationServiceTest test` passed (main, E1, E4, E5, and report behavior); the intentional report mutation made `EvaluationServiceTest.testGenerateWeeklyPeerEvaluationReportForSection` fail, then the line was restored.
- **Not verified:** E2 unauthorized instructor, E3 confirmation cancellation, and E6 already-issued JWT lack dedicated automated tests; `cd backend && bash mvnw -q test` could not run integration tests because Docker is unavailable (`/var/run/docker.sock` missing); frontend type-check is blocked by pre-existing errors in unrelated files, and no running-app check or screenshot was captured.

### Reviewer focus

Trunk: `c648b33`; security rule: `4f3c19e`; leaves: frontend/API and traceability changes. Read the trunk and security commits fully, then reviewed the student/evaluation paths and design. Focus first on active-account request lookup and historical report filtering.

### The tests I kept

- `StudentServiceTest.testDeactivateStudentRemovesTeamAndPreservesStudent` — oracle: main flow, E4, `BR-student-lifecycle`.
- `StudentServiceTest.testDeactivateStudentRejectsAlreadyInactiveStudent` — oracle: E1.
- `EvaluationServiceTest.testAddPeerEvaluationRejectsDeactivatedEvaluatee` — oracle: E5.
- `EvaluationServiceTest.testGenerateWeeklyPeerEvaluationReportForSection` — oracle: E7/report test-list row.
- No tests were deleted; E2, E3, and E6 remain untested.

### The code I broke

I changed the report filter in `EvaluationService.generateWeeklyPeerEvaluationReportForSection` to `student -> false`; `testGenerateWeeklyPeerEvaluationReportForSection` went red (expected 5 entries, got 0). I restored the production line and reran the targeted suite successfully.

### My review

- Triage: trunk `c648b33` fully read; security rule `4f3c19e` fully read; frontend/API and trace leaves reviewed at normal depth.
- Test list: main/E1/E4/E5/E7 covered by the tests above; E2 unauthorized instructor, E3 cancellation, and E6 token invalidation not covered; reactivation constraint is out of scope.
- Diff comment: `suggestion (non-blocking)` — replace the generic status control with the dedicated confirmation action so deactivation cannot happen silently; implemented in `EditStudentForm.vue`.
- Fresh-context review: not run; this local workflow does not create GitHub PRs or invoke `/code-review`. Follow-up: add security integration tests for E2/E6 and a component test for E3.

These are local drafts only. No GitHub issue or pull request has been created.
