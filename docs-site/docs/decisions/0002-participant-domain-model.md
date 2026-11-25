---
title: "ADR 0002: Participant Domain Model"
description: Decision to separate institutional identity from operational participants
---

# ADR 0002: Participant Domain Model

- Status: Accepted
- Date: 2026-11-24
- Decision owners: Oquira architecture

## 1. Context

Oquira must represent a diverse financial ecosystem, including traditional financial institutions, cooperatives, fintech companies, payment providers, insurance companies, pension administrators, securities intermediaries, and future financial actors.

The platform must distinguish documented real-world organizations from their experimental representations.

An institution appearing in a public registry does not automatically become an operational participant in Oquira.

Likewise, representing a real institution in a simulation does not imply a partnership, technical integration, authorization, or endorsement.

## 2. Problem

Without a clear separation between institutional identity and operational participation, Oquira risks:

- Confusing institutional research records with active platform participants.
- Treating historical registry observations as current authorization.
- Mixing real institutional attributes with synthetic simulation configuration.
- Making participant-specific behavior dependent on institutional identity.
- Limiting the platform's ability to represent fictional and emerging financial actors.

## 3. Decision

Oquira will maintain separate but related concepts for Institution and Participant.

### Institution

Represents a real-world organization or identifiable financial ecosystem actor.

Its information includes:

- Institutional identity.
- Legal and display names.
- Sector classification.
- Source references.
- Evidence records.
- Verification status.
- Historical observations.

### Participant

Represents an actor configured to participate in an Oquira experiment.

Its information includes:

- Internal platform identity.
- Display name.
- Representation type.
- Lifecycle status.
- Optional institutional reference.
- Participant profiles.

A Participant may reference an Institution, but an Institution does not automatically become a Participant.

## 4. Representation Types

Oquira will support three representation types.

### 4.1 Verified Real Institution

A real institution whose identity and relevant attributes have been checked against reliable evidence.

Verification is limited to the specific claims supported by the evidence.

### 4.2 Real Institution Experimental Profile

An experimental representation of a real institution.

It may use publicly documented characteristics as inspiration or reference, but its behavior, products, APIs, and data are simulated unless an actual integration is independently established.

### 4.3 Synthetic Archetype

A fictional participant representing an institutional category, financial business model, or emerging ecosystem actor.

It does not require a real-world institutional identity.

## 5. Alternatives Considered

### Alternative A: Institution and Participant as a Single Entity

Rejected because it couples real-world identity with experimental behavior and makes synthetic participants difficult to represent consistently.

### Alternative B: Participant-Only Model

Rejected because it does not adequately preserve institutional research, evidence, and historical registry information.

### Alternative C: Separate Institution and Participant Domains

Accepted because it preserves research integrity while allowing flexible experimental participation.

## 6. Consequences

### Positive

- Clear distinction between research and simulation.
- Support for real, experimental, and synthetic participants.
- Independent institutional verification.
- Easier expansion into additional financial sectors.
- Reduced coupling between institutional identity and simulation behavior.

### Trade-offs

- Additional entity relationships.
- Explicit mapping between research records and participants.
- More careful lifecycle and verification management.
- Potential duplication of display attributes that must be governed consistently.

## 7. Domain Rules

1. An institution may exist without a participant.
2. A participant may exist without an institution reference.
3. A participant referencing a real institution must declare its representation type.
4. Experimental profiles must not imply actual integrations.
5. Institutional verification and participant activation are independent.
6. Historical registry data must not automatically determine operational status.
7. Institutional claims must be traceable to supporting evidence.
8. Synthetic participants must be distinguishable from real institutions.

## 8. Implementation Implications

The initial modular monolith should keep institutional research and participant management as separate logical modules.

The participant module may reference institutional records through stable internal identifiers.

The exact persistence strategy and API contracts will be defined during implementation design.

## 9. Related Documents

- Participant and Simulation Model.
- Domain Data Model.
- Institutional Registry.
- Dominican Financial Ecosystem Map.
- ADR 0001: Modular Monolith.

## 10. Review Conditions

This decision should be reviewed if Oquira introduces actual external institutional integrations, multi-organization governance, or requirements that materially change institutional identity management.
