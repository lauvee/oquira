---
title: ADR 0004 - Shared Simulation Engine
description: Architectural decision defining a shared configuration-driven simulation engine
---

# ADR 0004: Shared Simulation Engine

- Status: Accepted
- Date: 2026-11-24

## Context

Oquira intends to represent a broad financial ecosystem, including banks, cooperatives, fintech companies, payment providers, insurers, pension administrators, securities intermediaries, and emerging financial actors.

Each participant may expose different products, capabilities, business rules, and simulated behaviors.

Building an independent simulator for every participant would create duplicated logic, inconsistent API behavior, and unnecessary maintenance complexity.

Oquira requires a common execution architecture that supports institutional diversity without coupling the platform to individual organizations.

## Decision

Oquira will implement a shared, configuration-driven Simulation Engine.

The engine will execute participant behavior using published participant profile versions, shared capability definitions, simulation policies, and synthetic datasets.

Participant-specific behavior should be expressed through configuration and reusable policies wherever practical, rather than institution-specific code.

### Core responsibilities

The shared engine will include the following logical responsibilities:

- **Simulation Orchestration:** coordinates simulation requests and execution lifecycle.
- **Capability Resolution:** determines whether a participant profile exposes the requested capability.
- **Policy Evaluation:** evaluates applicable simulation policies and business rules.
- **Synthetic Data Access:** retrieves or generates the required synthetic information.
- **Response Generation:** produces standardized API responses and errors.
- **Simulation Event Recording:** records relevant execution events.

These are logical responsibilities and do not require independent deployable services.

### Participant isolation

Each simulation execution must resolve its participant and exact published profile version.

Participant-specific configuration and datasets must not leak into another participant's execution context.

The engine must enforce authorization and applicable consent requirements before accessing protected simulated information.

### Standardized contracts

The engine will use shared capability contracts and standardized response and error structures.

Different participant profiles may implement different supported behaviors while preserving common interoperability contracts.

### Extensibility

New participant types, capabilities, and policies should be introduced through reusable domain components and validated configuration.

Institution-specific custom code should only be introduced when a genuine domain requirement cannot be represented through existing extension mechanisms.

## Consequences

### Positive

- Reduced code duplication.
- Consistent simulation behavior.
- Easier onboarding of new participant profiles.
- Shared security, consent, and audit controls.
- Better support for interoperability experiments.
- Lower operational complexity than independent simulators.

### Trade-offs

- The engine requires clear domain boundaries.
- Configuration validation becomes important.
- Poorly designed abstractions could create an overly generic engine.
- Complex participant-specific behavior may require controlled extensions.
- Testing must cover both shared execution logic and profile-specific configurations.

## Reconsideration criteria

Revisit this decision if measurable performance, isolation, scaling, or domain complexity requirements justify separating specific simulation capabilities into independent execution components.

Any future separation must preserve common contracts, participant isolation, authorization, consent, and simulation traceability.