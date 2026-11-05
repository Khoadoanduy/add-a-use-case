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

**Design pull request:** Link the merged design pull request here.

### Why

Implement the approved student deactivation workflow without deleting the student's account or submitted records.

### What changed

Implement the dedicated action, confirmation UI, authorization, immediate active-account enforcement, team removal, and evaluation/reminder/report boundaries from `docs/design/stu.md`.

### How it was verified

- **Verified:** Replace with the exact targeted and full test commands and results; map each result to the design test-list rows.
- **Not verified:** Replace with any untested extension, skipped suite, or unavailable running-app check.

### Reviewer focus

Name the trunk commit, security-rule commit, leaf commits, shared security changes, and any design deviation. Review the active-account request lookup and historical peer-evaluation report filtering first.

### The tests I kept

Add one line per retained test naming its oracle: the use-case main flow, E1–E7, `BR-student-lifecycle`, `BR-submission-owed`, or the corresponding `stu.md` test row. List rewritten or deleted tests and why.

### The code I broke

Name the least-trusted test, the one production line changed to violate its oracle, whether it went red, and the corrective action.

### My review

Add the trunk/security/leaf triage, a row-by-row test-list coverage check, at least one labeled diff comment, and the fresh-context review findings with each fixed, rejected, or deferred outcome.

These are local drafts only. No GitHub issue or pull request has been created.
