---
title: ADR 0003 - Participant Profile Versioning
description: Architectural decision defining immutable participant profile versions
---

# ADR 0003: Participant Profile Versioning

- Status: Accepted
- Date: 2026-11-24

## Context

Oquira will support multiple financial participants through configurable participant profiles.

These profiles define products, capabilities, simulation policies, consent requirements, and other participant-specific behavior.

As Oquira evolves, profile configurations will change. Existing simulation sessions must remain reproducible even when their original configuration is modified or replaced.

Without explicit versioning, historical experiments could produce different results when executed against updated participant configurations.

## Decision

Participant profiles will use an immutable published-version model.

Each profile will have a stable identity and may contain multiple sequential versions.

A published version cannot be modified. Any change to a published configuration must create a new version.

### Versioning rules

- Each profile has a stable profile identifier.
- Each version has a unique identifier and sequential version number within its profile.
- Draft versions may be modified before publication.
- Published versions are immutable.
- Changes to published profiles require a new version.
- Previous versions remain available for historical reference and reproducibility.
- Deprecated versions cannot be selected for new simulation sessions by default.
- Withdrawn versions cannot be used for new sessions.
- Existing sessions retain their original version reference.

### Environment separation

A participant profile may have published versions for different execution environments:

- DEVELOPMENT.
- TEST.
- DEMO.

Each profile version belongs to exactly one environment.

A profile may have one explicitly selected published version for each environment. A selection is required before that profile can execute a simulation in the corresponding environment. The selection must reference a version belonging to that profile and environment.

The system must not infer the selected version simply from the highest version number or most recent publication.

Changing the selected version affects new simulation sessions only. Existing sessions retain their original version reference.

Environment-specific configurations must not be silently mixed.
### Simulation session reference

Every simulation session must reference:

- Participant identifier.
- Profile identifier.
- Exact profile version identifier.
- Environment.
- Synthetic dataset version, when applicable.
- Scenario configuration and relevant execution parameters.

This ensures that the configuration used during execution can be identified later.

## Consequences

### Positive

- Historical simulation reproducibility.
- Traceability of configuration changes.
- Safer experimentation.
- Clear separation between development and demonstration configurations.
- Easier debugging and comparison between profile versions.

### Trade-offs

- Additional version records and lifecycle management.
- More complex profile publication workflows.
- Historical configuration retention requirements.
- Need for compatibility checks when capabilities and policies evolve.

## Reconsideration criteria

Revisit this decision if Oquira introduces a different configuration-management model, distributed participant configuration, or requirements that make immutable published versions impractical.

Any alternative must preserve the ability to identify the exact configuration used by historical simulation sessions.