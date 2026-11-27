---
title: Simulation Engine Contract
description: Functional and technical contract for Oquira's shared simulation engine
---

# Simulation Engine Contract

- Status: Proposed
- Version: 0.1
- Date: 2026-11-24

## 1. Purpose

This document defines the functional and technical contract of the Oquira Simulation Engine.

The engine provides a shared execution mechanism for simulating financial participants, products, capabilities, business rules, and financial interactions.

Its purpose is to support a diverse financial ecosystem without requiring independent simulation engines for individual institutions.

The contract establishes common execution rules while allowing participant-specific configurations and behaviors.

## 2. Architectural Principles

The Simulation Engine follows these principles:

1. Shared execution: all participants use the same execution infrastructure.
2. Participant configurability: behavior is determined by published participant profiles and their configurations.
3. Capability-driven execution: operations are resolved through standardized capabilities.
4. Version awareness: every simulation references immutable versions of its dependencies.
5. Synthetic data by default: simulation environments use explicitly classified synthetic datasets.
6. Traceability: every execution produces a traceable simulation record.
7. Isolation: one participant's configuration and simulation data must not leak into another participant's context.
8. Extensibility: new financial sectors, products, capabilities, and scenarios can be introduced without redesigning the entire engine.

## 3. Scope

### 3.1 Included Responsibilities

The engine is responsible for:

- Receiving and validating simulation requests.
- Resolving the requested participant.
- Resolving the participant profile version.
- Checking participant capabilities.
- Resolving applicable simulation policies.
- Resolving synthetic datasets and generation parameters.
- Executing the requested simulation.
- Producing standardized responses.
- Recording simulation events and execution metadata.
- Managing simulation execution states.

### 3.2 Excluded Responsibilities

The initial engine will not:

- Connect to production banking systems.
- Execute real financial transactions.
- Move or settle real funds.
- Authenticate against real financial institutions.
- Replace institutional core banking systems.
- Make real credit approval decisions.
- Guarantee the behavior of actual financial institutions.
- Operate as a production financial transaction processor.

These limitations preserve Oquira's experimental and educational scope.

## 4. Main Components

### 4.1 Simulation Orchestrator

Coordinates the complete simulation lifecycle.

Responsibilities:

- Accept simulation requests.
- Create simulation sessions.
- Coordinate execution components.
- Manage execution states.
- Handle execution failures.
- Return normalized results.

### 4.2 Participant Resolver

Identifies the participant and resolves the correct published profile version.

It must validate:

- Participant existence.
- Participant status.
- Requested profile version.
- Environment compatibility.
- Profile publication status.

### 4.3 Capability Resolver

Determines whether a participant supports the requested operation.

A capability represents a standardized functional ability, such as:

- Account information.
- Transaction history.
- Balance inquiry.
- Payment initiation simulation.
- Credit information.
- Insurance policy information.
- Investment portfolio information.
- Consent-dependent data access.

Capabilities must be explicitly configured rather than assumed from the participant's sector.

The Capability Resolver must resolve the exact CapabilityVersion referenced by the participant assignment, resolve its parent Capability identity, and validate both lifecycle statuses before session creation.

ACTIVE Capability identities permit new assignments and execution through published assignments. DEPRECATED identities permit execution only through previously published assignments, provided the CapabilityVersion permits it. RETIRED identities must be rejected before session creation. Independently, ACTIVE and DEPRECATED CapabilityVersions may initiate sessions through published assignments, while RETIRED versions must be rejected, including when referenced by previously published assignments. Identity lifecycle changes must not automatically change version lifecycle statuses.

### 4.4 Policy Evaluator

Evaluates the policies applicable to an execution.

Examples include:

- Operation eligibility.
- Transaction limits.
- Account status rules.
- Consent requirements.
- Product-specific restrictions.
- Participant-specific business rules.
- Scenario conditions.

Policies must be versioned and associated with their execution context.

### 4.5 Synthetic Data Resolver

Selects the synthetic dataset and resolves the data required by the requested capability.

It must verify:

- Dataset existence.
- Dataset version.
- Data classification.
- Environment compatibility.
- Participant isolation.
- Generation configuration, when applicable.

### 4.6 Execution Processor

Executes the resolved operation using shared processing logic and participant-specific configuration.

It must not contain hardcoded institutional behavior.

### 4.7 Event Recorder

Records relevant events during the simulation lifecycle.

Examples:

- Simulation requested.
- Participant resolved.
- Capability validated.
- Policy evaluated.
- Dataset resolved.
- Execution started.
- Execution completed.
- Execution failed.

Events must contain sufficient context for auditing and debugging without unnecessarily exposing sensitive information.

## 5. Standard Simulation Lifecycle

The initial execution lifecycle is:

1. Request received.
2. Request structure and parameters validated.
3. Participant resolved and its execution eligibility validated.
4. Exact published profile version resolved and validated against the requested environment. The version must be explicitly selected through ParticipantProfileEnvironmentSelection or explicitly provided and validated in the request.
5. Requested capability assignment resolved from the selected profile version.
6. Exact CapabilityVersion and its parent Capability identity resolved. Both lifecycle statuses must permit the requested execution. ACTIVE identities permit new assignments and execution through published assignments. DEPRECATED identities permit execution only through previously published assignments, provided the CapabilityVersion permits it. RETIRED identities must not initiate new simulation sessions, including through previously published assignments. Independently, ACTIVE and DEPRECATED CapabilityVersions may execute through published assignments, while RETIRED versions must not initiate new sessions. These restrictions must not alter historical simulation records or their exact version references. A transition to RETIRED must not automatically interrupt a simulation session that has already entered RUNNING.
7. Simulation session created with the resolved participant, profile version, assignment, capability version, and execution environment references. The initial session state is CREATED.
8. Session transitions to VALIDATING while the remaining execution dependencies are resolved and validated.
9. Product associations, input contract, authorization requirements, consent requirements, applicable policies, and synthetic datasets validated and resolved.
10. Immutable execution context constructed and session transitions to READY.
11. Session transitions to RUNNING and the simulation is executed.
12. Result normalized and execution events recorded.
13. Session transitions to COMPLETED or FAILED, and the simulation response is returned.

Requests rejected before session creation must return an appropriate error without a session identifier. Validation failures after session creation must transition the session to REJECTED. Execution failures must transition the session to FAILED.

A session must not be created until its participant, exact profile version, capability assignment, and exact CapabilityVersion have been resolved and validated. Its required references must be populated at creation.

Any subsequent validation or execution failure must be recorded according to the session state model and applicable failure category.

## 6. Simulation Request Contract

A simulation request represents an instruction to execute a specific operation against a participant.

The request identifies the desired participant, capability, operation, and execution conditions. The engine resolves the exact published configuration and dependency versions.

The conceptual request structure is:

```json
{
  "participantId": "participant-bank-example",
  "profileVersionId": "profile-version-example",
  "capability": "account.balance.read",
  "operation": "GET_BALANCE",
  "environment": "DEMO",
  "scenario": {
    "scenarioCode": "DOF-01",
    "parameters": {}
  },
  "datasetSelections": [
    {
      "datasetId": "dataset-retail-customers",
      "version": "1.0.0"
    },
    {
      "datasetId": "dataset-account-transactions",
      "version": "2.0.0"
    }
  ],
  "inputs": {},
  "correlationId": "example-correlation-id"
}
```

This structure is illustrative and does not represent a finalized REST API schema.

The client requests a capability by its stable code. It does not choose the internal assignment or override the capability version resolved by the engine.

Policy versions are resolved from the participant capability assignment and must not be selected arbitrarily by the client.

### 6.1 Required Information

A valid request must identify:

- Participant.
- Requested capability or operation.
- Execution environment.
- Required operation inputs.

### 6.2 Optional Information

Depending on the operation, a request may include:

- Explicit published profile version identifier.
- Scenario identifier and parameters.
- One or more dataset selections.
- Execution parameters.
- Correlation identifier.
- Additional validated context.

When a profile version is explicitly provided in the request, the engine must validate that it belongs to the requested participant profile, matches the execution environment, and has PUBLISHED lifecycle status. If valid, that version is used instead of the configured environment selection.

When a profile version is omitted, the engine must use the version explicitly selected through ParticipantProfileEnvironmentSelection for the requested profile and environment. If no selection exists, the request must be rejected. The engine must not automatically select the latest version or silently substitute another version.

The exact resolved profile version must be recorded in the simulation session. Changes to the environment selection affect new sessions only and must not alter existing sessions.

When dataset selections are omitted, the engine resolves the datasets required by the capability, scenario, and applicable configuration.

Explicit dataset selections must be validated against the resolved profile and assignment configuration.

An explicitly requested dataset version must be resolved exactly. The engine must reject unavailable or unsupported versions rather than silently substituting another version.

The engine must never silently change the resolved dependencies of an already created simulation session.

## 7. Simulation Execution Context

Before execution, the orchestrator constructs an immutable execution context.

The context records the exact versions and assignments resolved for the session.

### 7.1 Core Context

| Attribute | Description |
|---|---|
| sessionId | Unique simulation identifier |
| participantId | Resolved participant |
| profileVersionId | Exact published participant profile version |
| assignmentId | Exact ParticipantCapability assignment |
| capability | Stable capability code |
| capabilityVersionId | Exact CapabilityVersion used |
| engineVersion | Engine implementation version |
| environment | Execution environment |
| scenarioCode | Optional scenario reference |
| randomSeed | Seed when deterministic generation applies |
| simulatedAt | Controlled simulation timestamp |
| correlationId | Request correlation reference |

### 7.2 Resolved Policy Versions

The context must preserve every policy version evaluated during execution.

Each resolved policy reference must identify:

- policyId.
- policyVersionId.
- versionNumber.
- Assignment relationship.
- Evaluation order, when applicable.

Policy configurations and sensitive internal parameters must not be unnecessarily exposed in response metadata.

### 7.3 Resolved Dataset Versions

The context must preserve every dataset version used during execution.

Each resolved dataset reference must identify:

- datasetId.
- datasetVersionId.
- versionNumber.
- Usage purpose.
- Content checksum or integrity reference, when available.

A simulation may use multiple datasets for different purposes.

### 7.4 Execution Snapshot

The execution context must be persisted or represented through immutable references sufficient to reconstruct the resolved execution dependencies.

The snapshot must preserve:

- Participant profile version.
- Capability assignment and capability version.
- All evaluated policy versions.
- All dataset versions used.
- Engine version.
- Execution environment.
- Scenario and validated parameters.
- Random seed, when applicable.
- Simulated timestamp.

The context must not be modified after execution begins, except for controlled lifecycle and outcome metadata maintained by the session.

## 8. Simulation Response Contract

All operations must return a standardized response envelope.

Conceptual structure:

```json
{
  "sessionId": "sim-example-001",
  "status": "COMPLETED",
  "participantId": "participant-bank-example",
  "capability": "account.balance.read",
  "result": {},
  "metadata": {
    "profileVersionId": "profile-version-example",
    "assignmentId": "assignment-example",
    "scenarioCode": "DOF-01",
    "randomSeed": 428193,
    "simulatedAt": "2026-11-24T12:00:00Z",
    "capabilityVersion": {
      "capabilityVersionId": "capability-version-example",
      "version": "1.0.0"
    },
    "policyVersions": [
      {
        "policyVersionId": "policy-version-example",
        "version": "1.0.0"
      }
    ],
    "datasetVersions": [
      {
        "datasetVersionId": "dataset-version-example",
        "version": "1.0.0",
        "usagePurpose": "CUSTOMER_DATA",
        "checksum": "sha256:example-customer-dataset-checksum"
      },
      {
        "datasetVersionId": "dataset-version-transactions",
        "version": "2.0.0",
        "usagePurpose": "TRANSACTION_HISTORY",
        "checksum": "sha256:example-transaction-dataset-checksum"
      }
    ],
    "engineVersion": "0.1.0",
    "environment": "DEMO",
    "dataClassification": "SYNTHETIC"
  },
  "executedAt": "2026-11-24T12:00:00Z",
  "correlationId": "example-correlation-id"
}
```

The example illustrates a response with multiple resolved dependencies. Actual policy and dataset collections depend on the executed capability and scenario.

The result payload depends on the capability being executed.

The response envelope remains consistent across participants and operations.

Metadata must provide sufficient version references for traceability without exposing secrets, credentials, or sensitive internal configuration.

## 9. Simulation States

The initial state model is:

| State | Description |
|---|---|
| CREATED | Simulation session created |
| VALIDATING | Request and execution context being validated |
| READY | All required dependencies resolved |
| RUNNING | Simulation execution in progress |
| COMPLETED | Execution completed successfully |
| FAILED | Execution failed |
| REJECTED | Request rejected before execution |
| CANCELLED | Execution cancelled |

Terminal states are COMPLETED, FAILED, REJECTED, and CANCELLED.

State transitions must be controlled by the orchestration component.

## 10. Error Contract

Errors must use standardized categories.

| Error Code | Description |
|---|---|
| INVALID_REQUEST | Request structure or parameters are invalid |
| PARTICIPANT_NOT_FOUND | Participant does not exist |
| PARTICIPANT_INACTIVE | Participant cannot execute simulations |
| PROFILE_NOT_FOUND | Requested profile version does not exist |
| PROFILE_NOT_PUBLISHED | Profile version is not eligible for execution |
| CAPABILITY_NOT_SUPPORTED | Participant does not support the requested capability, or the assignment is disabled or unavailable |
| CAPABILITY_VERSION_UNAVAILABLE | Referenced CapabilityVersion cannot be resolved or is not eligible to initiate a new simulation |
| PRODUCT_NOT_SUPPORTED | Requested product is not associated with the capability assignment |
| ASSIGNMENT_CONFIGURATION_INVALID | Participant-specific configuration violates the capability contract |
| POLICY_VERSION_UNAVAILABLE | Referenced policy version cannot be resolved |
| POLICY_REJECTED | Applicable policy rejected the operation |
| CONSENT_REQUIRED | Required simulated consent is missing or invalid |
| DATASET_NOT_FOUND | Required dataset does not exist |
| DATASET_VERSION_UNAVAILABLE | Requested dataset version is unavailable |
| ENVIRONMENT_MISMATCH | Configuration is incompatible with the environment |
| SIMULATION_EXECUTION_FAILED | Execution failed unexpectedly |
| SIMULATION_TIMEOUT | Execution exceeded its permitted time |

Error responses must include a stable error code, descriptive message, simulation identifier when available, and correlation identifier.

Internal implementation details and sensitive configuration must not be exposed through public error responses.

## 11. Participant Behavior Resolution

The engine must not implement institutional differences through participant-specific code branches.

Instead, behavior must be resolved from:

- Participant profile.
- Published profile version.
- Supported capabilities.
- Products and services.
- Simulation policies.
- Synthetic datasets.
- Scenario parameters.

For example, two banks may support the same account balance capability while applying different simulated account rules, limits, product configurations, or response characteristics.

The execution mechanism remains shared.

## 12. API Exposure

The initial architecture should expose the Simulation Engine through application services and controlled REST endpoints.

Illustrative endpoints:

| Method | Endpoint | Purpose |
|---|---|---|
| POST | /api/v1/simulations | Create and execute a simulation |
| GET | /api/v1/simulations/:id | Retrieve simulation status and result |
| GET | /api/v1/simulations/:id/events | Retrieve simulation events |
| POST | /api/v1/simulations/:id/cancel | Request cancellation |

These endpoints are proposed interface examples, not implemented API commitments.

The final API specification must be defined through OpenAPI before implementation.

## 13. Security and Isolation

The engine must enforce:

- Authentication and authorization at the application boundary.
- Participant and environment access controls.
- Dataset access restrictions.
- Validation of requested profile versions.
- Controlled execution of participant policies.
- Protection against cross-participant data exposure.
- Traceability of execution actions.
- Appropriate handling of errors and execution metadata.

Simulated consent must remain distinguishable from real institutional authorization.

No simulated authorization may be interpreted as permission to access a real financial institution.

## 14. Versioning and Reproducibility

Every simulation session must preserve the exact versions of the dependencies used during execution.

These include:

- ParticipantProfileVersion.
- ParticipantCapability assignment.
- CapabilityVersion.
- All SimulationPolicyVersion records actually evaluated.
- All SyntheticDatasetVersion records actually used.
- Simulation engine version.
- Scenario definition and validated parameters.
- Execution environment.
- Generation parameters and random seed, when applicable.

### 14.1 Immutable Dependencies

Published versions of profiles, capabilities, policies, and datasets must remain immutable.

A simulation session must reference exact dependency versions rather than relying on mutable aliases or whichever version happens to be current.

The engine must never silently replace a resolved dependency after session creation.

### 14.2 Policy Evaluation Traceability

Every policy version actually evaluated must be associated with the session through SimulationSessionPolicy.

The execution record must preserve:

- Exact policyVersionId.
- Associated capability assignment.
- Evaluation order.
- Evaluation status.
- Sanitized evaluation result.
- Evaluation timestamp.

Policies associated with an assignment but not evaluated during execution must not be recorded as evaluated policies.

### 14.3 Dataset Integrity

Every dataset version used during execution must be associated with the session through SimulationSessionDataset.

The execution record must preserve:

- Exact datasetVersionId.
- Usage purpose.
- Content checksum or equivalent integrity reference.
- Relevant generation configuration, when applicable.

Dataset content and generation configuration must remain available through controlled, versioned references when historical reconstruction is required.

### 14.4 Deterministic Execution

When deterministic generation is supported, the engine must preserve:

- Random seed.
- Dataset version references.
- Generation configuration.
- Scenario parameters.
- Engine version.
- Relevant execution configuration.

The system should produce equivalent outputs when the same deterministic inputs and compatible execution dependencies are used.

Exact output reproduction must not be guaranteed when execution depends on nondeterministic processes, external runtime behavior, or dependencies that cannot be reconstructed.

### 14.5 Execution Snapshot

The execution snapshot must preserve sufficient information to explain and reconstruct the resolved execution context.

It must include:

- Participant and profile version.
- Capability assignment and capability version.
- Evaluated policy versions and evaluation records.
- Dataset versions and integrity references.
- Engine version.
- Environment and scenario.
- Validated execution parameters.
- Random seed, when applicable.
- Controlled simulation timestamp.

Sensitive values, credentials, and unnecessary personal information must not be copied into execution snapshots.

Reproducibility guarantees must remain consistent with ADR 0005.

## 15. Relationship with the Domain Data Model

The Simulation Engine Contract relies on the following domain entities and their versioned relationships.

### 15.1 Institutional and Participant Entities

- Institution.
- Sector.
- InstitutionSector.
- EvidenceRecord.
- Participant.
- ParticipantProfile.
- ParticipantProfileVersion.

These entities establish the institutional identity and the participant configuration used by simulations.

### 15.2 Product and Capability Entities

- Product.
- ParticipantProfileProduct.
- Capability.
- CapabilityVersion.
- ParticipantCapability.
- ParticipantCapabilityProduct.

These entities define the products and capabilities available within a specific participant profile version.

The engine resolves the exact capability assignment and capability version before execution.

### 15.3 Policy Entities

- SimulationPolicy.
- SimulationPolicyVersion.
- ParticipantCapabilityPolicy.
- SimulationSessionPolicy.

ParticipantCapabilityPolicy identifies the policy versions associated with a capability assignment.

SimulationSessionPolicy records the policy versions actually evaluated during a specific simulation session.

The engine must preserve the relationship between the evaluated policy version and its originating capability assignment.

### 15.4 Synthetic Dataset Entities

- SyntheticDataset.
- SyntheticDatasetVersion.
- SimulationSessionDataset.

These entities define versioned synthetic data and preserve the exact dataset versions used by each simulation session.

### 15.5 Execution and Event Entities

- SimulationSession.
- SimulationEvent.

SimulationSession represents the execution context and its resolved dependency references.

SimulationEvent records relevant execution events for debugging, traceability, and auditability.

The domain model provides the persistence and identity structures consumed by the engine through application interfaces.

These entities do not require independent services or microservices. They remain part of the modular monolith architecture.

## 16. Architectural Boundaries

The initial implementation will remain within the modular monolith architecture.

The following logical boundaries should be maintained:

- Simulation orchestration.
- Participant resolution.
- Capability management.
- Policy evaluation.
- Synthetic data management.
- Simulation execution.
- Event recording.

These boundaries represent application responsibilities and module organization, not mandatory deployment units.

## 17. Initial Implementation Priorities

The first implementation should focus on:

1. A standardized simulation request and response.
2. Participant and profile version resolution.
3. Capability validation.
4. Basic policy evaluation.
5. Synthetic dataset resolution.
6. A shared execution processor.
7. Simulation session persistence.
8. Event recording.
9. Standardized errors.
10. Automated tests for successful and rejected executions.

Advanced financial scenarios, asynchronous processing, external integrations, and distributed execution can be introduced in later stages.

## 18. Open Design Questions

The following decisions remain open for detailed design:

- Which capabilities require synchronous versus asynchronous execution?
- How should capability inputs and outputs be formally versioned?
- How should policy evaluation be represented and composed?
- Should simulation sessions support partial execution and retries?
- How should cancellation interact with an operation already in progress?
- What retention policy should apply to simulation events?
- Which execution metadata must be immutable?
- How should multiple datasets participate in one simulation?
- How should scenario orchestration coordinate multiple participants?

These questions must be resolved before the corresponding implementation commitments are made.

## 19. Architectural Guiding Principle

The Simulation Engine must provide one consistent execution contract for a diverse financial ecosystem.

Institutional diversity belongs in participant configuration, capabilities, policies, products, and synthetic data.

Execution orchestration, validation, traceability, and response normalization belong to the shared engine.

This separation allows Oquira to explore traditional financial institutions, fintech companies, cooperatives, payment providers, and emerging financial participants through one extensible simulation architecture.