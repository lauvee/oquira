---
title: ADR 0005 - Synthetic Data and Reproducibility
description: Architectural decision defining synthetic data governance and reproducible simulations
---

# ADR 0005: Synthetic Data and Reproducibility

- Status: Accepted
- Date: 2026-11-24

## Context

Oquira is an experimental Open Finance platform designed to explore financial data exchange, institutional interoperability, consent management, and emerging financial services.

The platform must support realistic demonstrations and simulations without requiring access to real customer accounts, production banking systems, or confidential institutional information.

Its simulations may represent financial institutions, consumers, businesses, accounts, transactions, products, payments, credit activity, and other financial scenarios.

Without explicit data governance, synthetic information could be mistaken for real financial records, become difficult to reproduce, or introduce privacy and security risks.

Oquira therefore requires a consistent approach to synthetic data generation, classification, versioning, isolation, and reproducibility.

## Decision

Oquira will use synthetic data as the default and standard data source for its experimental environments.

Synthetic datasets must be explicitly identified, versioned, and associated with the simulation context in which they are used.

Real customer personal data, real account credentials, confidential financial records, and production banking data must not be introduced into the standard simulation environment.

## Synthetic Data Principles

### 1. Synthetic by Default

All initial demonstrations, development environments, test scenarios, and participant simulations must use synthetic data.

Synthetic data must be generated for experimentation and must not be represented as actual customer information or actual financial activity.

### 2. Explicit Classification

Every dataset and generated record must have a clear data classification.

The initial classifications are:

- Synthetic: artificially generated information that does not represent an actual customer or transaction.
- Public Reference: information obtained from publicly available sources and retained for research or reference purposes.
- Experimental: hypothetical information created to represent a proposed institution, product, capability, or financial scenario.
- Restricted: information that requires additional authorization and handling controls. It is not permitted in the standard simulation environment.

Classification must remain visible in dataset metadata and relevant simulation outputs.

### 3. No Production Personal Data

The standard simulation environment must not store or process:

- Real customer identity documents.
- Real banking credentials or authentication secrets.
- Real account numbers or payment credentials.
- Confidential customer financial records.
- Personal information obtained without an appropriate legal basis and explicit authorization.

Public institutional information may be used for research and participant configuration, subject to source attribution and verification.

### 4. Generation Methods

Synthetic datasets may be created through:

- Rule-based generation.
- Parameterized templates.
- Deterministic pseudorandom generation.
- Scenario-specific data generation.
- Carefully controlled combinations of synthetic records.

Generation methods must be documented sufficiently to understand the origin and characteristics of the resulting dataset.

### 5. Dataset Versioning

Every reusable synthetic dataset must have:

- A stable dataset identifier.
- A version.
- A description and intended use.
- A data classification.
- A generation method.
- Relevant configuration parameters.
- Creation or publication metadata.

Published dataset versions must be immutable.

Changes to a published dataset must produce a new version rather than silently modifying the existing version.

## Reproducibility

### 1. Reproducible Simulation Context

Oquira must preserve enough information to reproduce a simulation when its execution components support deterministic behavior.

A simulation execution record should reference:

- Participant identifier.
- Published participant profile version.
- Synthetic dataset identifier and version.
- Simulation policy version.
- Simulation engine version.
- Scenario inputs and configuration.
- Random seed, when applicable.
- Simulated date and time, when applicable.
- Relevant locale, currency, and regional configuration.

### 2. Deterministic Generation

Where pseudorandom generation is used, the system should support an explicit seed.

Using the same seed, dataset version, generation configuration, and compatible generator version should produce equivalent generated data.

A seed alone does not guarantee reproducibility if other inputs, algorithms, dependencies, or execution conditions change.

### 3. Simulation Snapshots

A simulation session must retain references to the versions and configuration used at execution time.

Subsequent changes to participant profiles, datasets, policies, or the simulation engine must not silently alter the historical context of an existing session.

### 4. Reproducibility Limitations

Oquira must distinguish between:

- Configuration reproducibility: reconstructing the same declared simulation inputs and versions.
- Data reproducibility: regenerating equivalent synthetic datasets under controlled conditions.
- Execution reproducibility: obtaining equivalent simulation behavior under compatible engine and dependency versions.

Exact output reproduction must not be promised when external services, nondeterministic algorithms, uncontrolled time sources, or changing dependencies affect execution.

## Data Isolation and Security

Synthetic datasets and simulation sessions must be logically isolated according to their environment and execution context.

The architecture should prevent one participant's simulation configuration or data from being unintentionally exposed to another participant.

Synthetic data must not contain production secrets or credentials.

Simulation outputs must remain clearly identified as experimental and must not be interpreted as evidence of actual financial transactions or institutional activity.

## Architectural Implications

The domain model should support:

- SyntheticDataset and dataset version metadata.
- Data classification.
- Generation configuration and method.
- Random seed metadata when applicable.
- Simulation session snapshots.
- References to participant profile, policy, and engine versions.
- Traceability between generated records and their originating dataset or generation process.

The shared Simulation Engine must consume versioned datasets through controlled interfaces rather than depending on hardcoded participant-specific data.

## Consequences

### Positive

- Reduces privacy and confidentiality risks.
- Enables safe experimentation without production banking access.
- Improves consistency across participant simulations.
- Supports repeatable demonstrations and testing.
- Makes simulation inputs and outputs traceable.
- Allows new financial scenarios to be introduced without requiring real institutional integrations.

### Trade-offs

- Synthetic data may not capture every characteristic of real financial behavior.
- Realistic datasets require deliberate modeling and validation.
- Versioning introduces additional metadata and lifecycle management.
- Exact reproducibility requires control over multiple execution dependencies.

## Reconsideration Criteria

This decision may be revisited if Oquira introduces an explicitly authorized research environment that requires controlled access to real-world financial data.

Such a change would require a separate architecture and governance decision covering legal basis, consent, privacy, security, access control, retention, and data minimization.

It must not weaken the synthetic-data defaults of the standard simulation environment.