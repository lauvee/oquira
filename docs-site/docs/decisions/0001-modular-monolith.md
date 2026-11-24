---
title: ADR 0001 - Modular Monolith
description: Initial backend architecture decision
---

# ADR 0001: Modular Monolith

- Status: Accepted for initial implementation
- Date: 2026-11-23

## Context

Oquira is an experimental project initially developed by a small team or individual contributor.

The initial product requires several related capabilities, including identity, consent management, financial accounts, transactions, and simulated institutions.

## Decision

Start with a modular monolith using Spring Boot.

Organize the code into modules with explicit responsibilities and boundaries.

### Module boundaries

- Each module owns its business logic and data access.
- Modules must not access another module's internal repositories.
- Cross-module interactions should use explicit interfaces or application services.
- Shared code must remain minimal and must not become a dependency for business logic.
- Database access should respect module ownership, even when using a shared PostgreSQL instance.

## Consequences

### Positive

- Simpler local development.
- Easier debugging and testing.
- Lower operational complexity.
- Faster initial delivery.
- Clear module boundaries can support future extraction if justified.

### Trade-offs

- Modules share a deployment lifecycle.
- Architectural boundaries must be enforced through code organization and tests.
- Scaling individual modules independently is not an initial capability.

## Reconsideration criteria

Revisit this decision only when measurable operational, organizational, or technical requirements justify a distributed architecture.
