---
title: Participant Capability Assignment Model
description: Architecture for assigning, configuring, and versioning financial capabilities across Oquira participants
---

# Participant Capability Assignment Model

- Status: Proposed
- Version: 0.1
- Date: 2026-11-24

## 1. Purpose

This document defines how financial capabilities are assigned, configured, versioned, and made available to participants in Oquira.

It establishes the relationship between the global Capability Catalog, participant profiles, products, policies, and simulation execution.

The model allows different financial participants to support similar capabilities while maintaining distinct products, business rules, execution conditions, and behaviors.

## 2. Core Concepts

Oquira distinguishes four related concepts.

### 2.1 Capability Catalog

The Capability Catalog defines the standardized functional capabilities available within Oquira.

Examples:

- `account.balance.read`
- `account.transactions.read`
- `payment.initiate`
- `credit.application.create`
- `insurance.quote.create`

A catalog definition describes the functional meaning and contract of a capability.

It does not imply that any particular participant supports it.

### 2.2 Participant Capability Assignment

A Participant Capability Assignment declares that a participant supports an exact version of a catalog capability under defined conditions.

It associates a specific CapabilityVersion with:

- A participant profile version.
- An availability status.
- Applicable products through ParticipantCapabilityProduct.
- Additional participant-specific permission requirements.
- Applicable policies through ParticipantCapabilityPolicy.
- Participant-specific configuration through CapabilityConfiguration.

The execution environment is inherited from the associated ParticipantProfileVersion and is not independently declared by the assignment.

### 2.3 Participant Profile Version

A Participant Profile Version represents an immutable published configuration of a participant.

It contains the participant's declared capabilities and the configuration required to simulate its behavior.

Assignments belonging to a published profile version must not be modified in place.

### 2.4 Product

A Product represents a financial offering provided by a participant.

A capability may be available across several products, restricted to one product, or independent of a specific product.

For example, a participant may support account balance inquiry for savings and checking accounts under different product configurations.

## 3. Architectural Principles

The assignment model follows these principles:

1. Global definitions: capability definitions belong to the shared catalog.
2. Participant-specific configuration: assignments define how a participant supports a capability.
3. Versioned assignments: assignments are part of a participant profile version.
4. Explicit availability: support and availability conditions must be declared.
5. Product awareness: capabilities may be associated with one or more products.
6. Policy separation: policies determine execution conditions without redefining the capability.
7. Environment inheritance: assignments inherit the execution environment from their associated participant profile version.
8. Historical consistency: published assignments remain immutable.
9. Shared execution: participant differences are configuration-driven, not separate simulation engines.

## 4. Conceptual Relationship

The conceptual relationship is:

```text
Capability Catalog
       |
       | Capability Definition
       |
       v
Participant Profile Version
       |
       | Participant Capability Assignment
       |
       +---- Capability Configuration
       +---- Product Association
       +---- Policy References
       +---- Permission Requirements
       +---- Inherited execution environment
       |
       v
Simulation Engine
```

A participant may have several profile versions, and each version may contain multiple capability assignments.

The same global capability definition may be assigned to many participants.

## 5. Assignment Structure

Each assignment represents the association between a participant profile version and an exact CapabilityVersion.

| Attribute | Description |
|---|---|
| assignmentId | Unique identifier for the assignment |
| capabilityVersionId | Exact immutable CapabilityVersion referenced |
| profileVersionId | Participant profile version containing the assignment |
| availabilityStatus | Declared availability |
| configuration | Participant-specific CapabilityConfiguration |
| requiredPermissions | Additional participant-specific authorization requirements |

The capability identity is resolved through the referenced CapabilityVersion. ParticipantCapability does not duplicate capabilityId.

Product associations are maintained through ParticipantCapabilityProduct. Policy associations are maintained through ParticipantCapabilityPolicy rather than embedded as unstructured lists.

The execution environment is inherited from the associated ParticipantProfileVersion.

New assignments eligible for publication must reference an ACTIVE Capability identity and an ACTIVE CapabilityVersion.

A transition of a Capability identity from ACTIVE to DEPRECATED prevents new assignments eligible for publication but does not invalidate previously published assignments. Those assignments may continue to initiate new simulation sessions if their referenced CapabilityVersion permits it.

A transition of a Capability identity to RETIRED prevents new simulation sessions through all assignments referencing that identity, including previously published assignments. Historical simulation records and their exact version references must remain intact.

Published assignments must not be modified in place. Changes must be represented through a new participant profile version.

Participant-specific configuration must not redefine the fundamental meaning of the global capability or weaken its input, output, and authorization contracts.

Assignment effective dates and generic metadata are deferred from v1.

## 6. Availability Model

ParticipantCapabilityAvailabilityStatus defines the declared availability of a capability within a participant profile version.

The initial availability statuses are:

| Status | Description |
|---|---|
| ENABLED | Capability is available subject to applicable permissions, policies, and execution requirements |
| DISABLED | Capability is explicitly disabled in the participant profile version |
| CONDITIONAL | Capability is available only when explicitly defined conditions are satisfied |

These statuses describe assignment availability, not the lifecycle of Capability or CapabilityVersion.

DEPRECATED and RETIRED belong to the CapabilityVersion lifecycle and must not be represented as assignment availability statuses.

Operational unavailability is an execution-time condition and must not be represented as a persistent assignment status.

A capability may be ENABLED but still fail an execution because of a policy, missing permission, product restriction, or invalid input.

A capability marked CONDITIONAL requires explicit conditions to be evaluated before execution.

The engine must never interpret an absent assignment as implicit support.

## 7. Conditional Availability

Conditional availability supports realistic differences between financial participants.

Examples include:

- Account information available only for selected account products.
- Payment initiation available only below configured limits.
- Credit applications available only for eligible customer profiles.
- Insurance quotes available only for selected insurance products.
- Portfolio information available only in certain environments.
- Operations requiring a valid simulated consent grant.

Conditions must be represented through explicit configuration, product associations, and policy references.

The engine must evaluate these conditions before execution.

## 8. Product Association

A capability assignment may have:

- No product association when the operation is participant-wide.
- One product association.
- Multiple product associations.

Product associations must reference products belonging to the relevant participant profile version.

Product-specific differences should be expressed through configuration and policies.

The architecture must avoid duplicating global capability definitions for each product.

## 9. Execution Environment

The execution environment belongs to ParticipantProfileVersion.

The initial environments are:

| Environment | Purpose |
|---|---|
| DEVELOPMENT | Internal development and experimentation |
| TEST | Functional and integration testing |
| DEMO | Demonstrations and educational scenarios |

ParticipantCapability inherits the environment from its associated ParticipantProfileVersion and must not declare an independent list of supported environments.

The Simulation Engine must validate that the requested execution context uses the environment of the resolved profile version.

## 10. Policy and Permission References

Capability assignments may reference one or more exact policy versions through ParticipantCapabilityPolicy.

Each association identifies:

- assignmentId.
- policyVersionId.
- Optional evaluationOrder.
- Validated configurationOverrides, when permitted.

Policy versions are represented by SimulationPolicyVersion and must remain immutable after publication.

Examples of policy responsibilities include:

- Transaction amount limits.
- Customer eligibility.
- Account status restrictions.
- Product eligibility.
- Simulated consent requirements.
- Operation frequency restrictions.
- Service availability and response behavior.

The assignment identifies the policies available to govern its capability. It does not imply that every associated policy will be evaluated in every execution.

The Policy Evaluator resolves and evaluates applicable policy versions according to the operation, environment, scenario, and validated execution context.

Every policy version actually evaluated must be recorded through SimulationSessionPolicy.

SimulationSessionPolicy must preserve:

- Exact policyVersionId.
- Associated assignmentId.
- Evaluation order.
- Evaluation status.
- Sanitized evaluation result.
- Evaluation timestamp.

Policies associated with an assignment but not evaluated during a particular execution must not be recorded as evaluated policies.

CapabilityVersion.requiredPermissions defines the global minimum authorization requirements for the capability.

ParticipantCapability.requiredPermissions defines additional participant-specific authorization requirements.

The effective authorization requirements must include both sets. Participant-specific requirements may add restrictions but must never remove or weaken global requirements.

The application layer must resolve the effective requirements and provide them to the applicable authorization and policy evaluation mechanisms.

The Policy Evaluator determines whether the supplied context satisfies applicable requirements. Policy evaluation must remain distinct from the definition of the capability itself.
## 11. Participant Configuration

Participant-specific configuration allows the same capability to behave differently across participants without redefining its global functional contract.

Examples:

- Supported transaction types.
- Minimum and maximum simulated amounts.
- Supported currencies.
- Account eligibility rules.
- Response characteristics.
- Available payment channels.
- Product-specific parameters.

The domain model distinguishes three separate value objects:

- CapabilityInputSchema: defines the expected global input structure and contract.
- CapabilityOutputSchema: defines the expected global output structure and contract.
- CapabilityConfiguration: represents participant-specific configuration.

CapabilityInputSchema and CapabilityOutputSchema belong to CapabilityVersion. CapabilityConfiguration belongs to ParticipantCapability.

These value objects preserve non-empty JSON content. JSON syntax and structural validation must be performed by the application layer, without introducing serialization-framework dependencies into the domain model.

Participant-specific configuration must be validated against the applicable capability contract.

Configuration must not override mandatory security controls, weaken global permission requirements, or redefine the global capability contract.

The initial implementation must use structured configuration rather than arbitrary executable scripts.

## 12. Assignment Lifecycle

Assignments are managed through participant profile versions.

The initial lifecycle is:

1. Assignment created in a draft profile.
2. Exact CapabilityVersion resolved.
3. Participant-specific configuration provided.
4. Product associations validated.
5. Policy references validated.
6. Global and additional participant-specific permission requirements validated.
7. Assignment availability and applicable conditions validated.
8. Profile configuration validated, including its execution environment.
9. Profile published.
10. Assignment becomes immutable within that published version.

New assignments eligible for publication must reference an ACTIVE CapabilityVersion.

Changes to a published assignment require a new participant profile version.

The system must preserve previous profile versions and their exact capability references for historical simulation traceability.

## 13. Versioning Rules

Three independent version dimensions must be preserved:

### 13.1 Capability Definition Version

Represents the version of the global functional contract.

Breaking changes require a new major version.

Backward-compatible extensions may use minor versions.

Documentation and metadata corrections may use patch versions.

### 13.2 Participant Profile Version

Represents a published participant configuration.

It identifies the exact set of assignments, products, policies, and configuration used by the participant.

### 13.3 Policy Version

Represents the exact business rules applied to an assignment.

Published policy versions must remain identifiable and immutable.

A simulation session must reference all relevant versions.

Changing one version must not silently alter historical execution context.

## 14. Simulation Engine Resolution Flow

When a simulation request arrives, the engine must:

1. Validate the request structure and required inputs.
2. Resolve the participant and validate its execution eligibility.
3. Resolve the requested or explicitly selected published profile version and validate its execution environment.
4. Find the corresponding capability assignment within that profile version.
5. Resolve the exact CapabilityVersion referenced by the assignment.
6. Resolve the Capability identity through the referenced CapabilityVersion and validate both lifecycle statuses before session creation. ACTIVE identities permit new assignments and execution through published assignments. DEPRECATED identities permit execution only through previously published assignments, provided the CapabilityVersion permits it. RETIRED identities must be rejected before session creation, including through previously published assignments. Independently, ACTIVE and DEPRECATED CapabilityVersions may initiate sessions through published assignments, while RETIRED versions must be rejected. Historical simulation records must retain their exact version references.
7. Resolve the execution environment from the associated ParticipantProfileVersion and validate environment compatibility.
8. Create the SimulationSession with the resolved participant, profile version, assignment, capability version, and execution environment references. The initial session state is CREATED.
9. Transition the session to VALIDATING and resolve the remaining execution dependencies.
10. Validate applicable product associations.
11. Validate requested inputs against CapabilityInputSchema.
12. Combine CapabilityVersion.requiredPermissions with ParticipantCapability.requiredPermissions and resolve the applicable authorization context.
13. Resolve applicable policy versions through ParticipantCapabilityPolicy.
14. Evaluate applicable policies in their defined order.
15. Evaluate explicit conditions when the assignment availability is CONDITIONAL.
16. Record every policy version actually evaluated through SimulationSessionPolicy.
17. Resolve the required SyntheticDatasetVersion references.
18. Record every dataset version used through SimulationSessionDataset.
19. Preserve dataset integrity references and generation configuration when applicable.
20. Construct and freeze the resolved execution context and transition the session to READY.
21. Transition the session to RUNNING and execute the capability through the shared Simulation Engine.
22. Validate and normalize the result against CapabilityOutputSchema.
23. Record execution events and the final session outcome.

The session must preserve the exact participant profile version, capability assignment, capability version, evaluated policy versions, dataset versions, engine version, scenario parameters, environment, and deterministic generation inputs when applicable.

Requests rejected before session creation must not produce a session identifier. Validation failures after session creation must transition the session to REJECTED. Execution failures must transition the session to FAILED.

The engine must never silently substitute a different published dependency version after the session has resolved its execution context.
## 15. Error Conditions

The initial assignment-related error categories include:

| Error Code | Description |
|---|---|
| CAPABILITY_NOT_ASSIGNED | Participant has no assignment for the requested capability |
| CAPABILITY_DISABLED | Assignment is explicitly disabled |
| CAPABILITY_UNAVAILABLE | Assignment is unavailable in the requested context |
| CAPABILITY_CONDITION_NOT_MET | Conditional availability requirements were not satisfied |
| CAPABILITY_VERSION_UNAVAILABLE | Referenced CapabilityVersion cannot be resolved or is not eligible to initiate a new simulation session |
| PRODUCT_NOT_SUPPORTED | Requested product is not associated with the capability |
| ENVIRONMENT_NOT_SUPPORTED | Capability is not available in the requested environment |
| ASSIGNMENT_CONFIGURATION_INVALID | Participant configuration violates the capability contract |
| POLICY_VERSION_UNAVAILABLE | Referenced policy version cannot be resolved |

Internal assignment-related errors must be normalized to the public Simulation Engine Error Contract as follows:

| Internal Error Code | Public Error Code |
|---|---|
| CAPABILITY_NOT_ASSIGNED | CAPABILITY_NOT_SUPPORTED |
| CAPABILITY_DISABLED | CAPABILITY_NOT_SUPPORTED |
| CAPABILITY_UNAVAILABLE | CAPABILITY_NOT_SUPPORTED |
| CAPABILITY_CONDITION_NOT_MET | CAPABILITY_NOT_SUPPORTED |
| CAPABILITY_VERSION_UNAVAILABLE | CAPABILITY_VERSION_UNAVAILABLE |
| PRODUCT_NOT_SUPPORTED | PRODUCT_NOT_SUPPORTED |
| ENVIRONMENT_NOT_SUPPORTED | ENVIRONMENT_MISMATCH |
| ASSIGNMENT_CONFIGURATION_INVALID | ASSIGNMENT_CONFIGURATION_INVALID |
| POLICY_VERSION_UNAVAILABLE | POLICY_VERSION_UNAVAILABLE |

The public error code must communicate the failure category without exposing internal configuration or sensitive information.

## 16. Relationship with the Domain Data Model

The assignment model builds on the following existing domain entities:

### 16.1 Institutional and Participant Entities

- Institution.
- Participant.
- ParticipantProfile.
- ParticipantProfileVersion.

These entities establish the participant identity and the exact profile version in which the assignment exists.

### 16.2 Product and Capability Entities

- Product.
- ParticipantProfileProduct.
- Capability.
- CapabilityVersion.
- ParticipantCapability.
- ParticipantCapabilityProduct.

These entities define the stable capability identity, its immutable definition version, and its association with a participant profile and applicable products.

### 16.3 Policy Entities

- SimulationPolicy.
- SimulationPolicyVersion.
- ParticipantCapabilityPolicy.
- SimulationSessionPolicy.

ParticipantCapabilityPolicy identifies policy versions associated with an assignment.

SimulationSessionPolicy records the exact policy versions actually evaluated during a specific simulation session.

### 16.4 Dataset and Execution Entities

- SyntheticDataset.
- SyntheticDatasetVersion.
- SimulationSessionDataset.
- SimulationSession.
- SimulationEvent.

These entities preserve the exact synthetic dataset versions and execution dependencies used by a simulation session, together with relevant execution events.

The existing ParticipantCapability concept represents the association between a participant profile version and a capability definition version.

The assignment model must preserve these exact version references and must not introduce duplicate entities for concepts already represented in the domain model.
## 17. Architectural Boundaries

Responsibilities remain separated:

- Capability Catalog: defines global capability contracts.
- Participant Profile: defines participant-specific configuration.
- Participant Capability Assignment: declares participant support and conditions.
- Product: defines financial offerings.
- Policy Evaluator: evaluates business and authorization conditions.
- Simulation Engine: coordinates execution.
- Synthetic Data Resolver: supplies the required experimental data.

These are logical domain and application responsibilities within the modular monolith.

They do not require separate deployment units.

## 18. Implementation Priorities

The initial implementation should support:

1. Global capability definitions with versions.
2. Capability assignments inside draft participant profiles.
3. Assignment validation during profile publication.
4. Product associations.
5. Explicit availability statuses.
6. Execution environment inheritance and validation through ParticipantProfileVersion.
7. Policy references.
8. Structured participant configuration.
9. Immutable published assignments.
10. Assignment resolution during simulation execution.

Advanced capability composition and complex conditional rules may be implemented incrementally.

## 19. Open Design Questions

The following questions remain open for future design:

- Should participant assignments use relational configuration tables, JSON configuration, or a hybrid model?
- How should product inheritance work when multiple products share the same capability?
- How should assignment compatibility be tested after a capability definition changes?
- Should conditional availability use a standardized expression model?
- How should profile publication report configuration errors?

Capability dependencies are deferred from v1 and are not part of the initial assignment validation model.

These questions must be resolved during detailed domain and technical design before their corresponding capabilities are implemented.

## 20. Guiding Principle

The Capability Catalog defines what can be done.

Participant Capability Assignments define what a participant supports and under which conditions.

Participant Profile Versions preserve the exact configuration of those assignments.

Products define the financial offerings associated with capabilities.

Policies determine whether a requested operation is eligible for execution.

The Simulation Engine coordinates the resulting behavior through one shared execution architecture.

This separation allows Oquira to model a broad financial ecosystem while preserving institutional diversity, historical consistency, and extensibility.