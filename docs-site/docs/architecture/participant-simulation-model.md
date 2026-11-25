---
title: Participant and Simulation Model
description: Common participant architecture and configurable financial institution simulation model
---

# Participant and Simulation Model

## 1. Purpose

This document defines the conceptual architecture for representing financial institutions and other ecosystem actors within Oquira.

The objective is to support a diverse financial ecosystem without requiring an independent simulator or codebase for every institution.

Oquira will use a shared simulation engine driven by configurable participant profiles.

This model is an architectural proposal for an experimental platform. It does not imply integrations, partnerships, authorization, or participation by real financial institutions.

## 2. Architectural Principles

The model follows these principles:

1. **Shared infrastructure:** participants use common platform capabilities and simulation infrastructure.
2. **Configurable behavior:** each participant can expose different products, capabilities, policies, and simulated behaviors.
3. **Institutional diversity:** banks, cooperatives, fintech companies, payment providers, insurers, pension administrators, securities intermediaries, and future financial actors can be represented.
4. **Separation of identity and behavior:** institutional identity must not be confused with the participant's technical configuration.
5. **Evidence-based representation:** real-world institutional information must have an explicit verification status and supporting evidence.
6. **Synthetic data by default:** simulated accounts, transactions, customers, and financial records must not be presented as real customer information.
7. **Progressive implementation:** the initial architecture should remain compatible with a modular monolith.

## 3. Core Domain Concepts

### 3.1 Institution

An Institution represents an organization or identifiable actor in the financial ecosystem.

Examples include:

- Commercial banks.
- Savings and credit banks.
- Savings and loan associations.
- Cooperatives.
- Payment institutions and fintech companies.
- Remittance providers.
- Insurance companies.
- Pension fund administrators.
- Securities intermediaries.
- Financial market infrastructure operators.

An institution may exist in Oquira's research registry without being enabled as an operational participant.

### 3.2 Participant

A Participant represents an institution, organization, or synthetic actor configured to participate in an Oquira experiment.

A participant references an institutional identity when applicable, but its existence in Oquira does not establish a real integration or commercial relationship.

A participant has:

- A unique platform identifier.
- A display name.
- An institutional reference, when available.
- A sector and participant category.
- A representation model.
- A lifecycle status.
- One or more participant profiles.

### 3.3 Participant Profile

A Participant Profile defines how a participant behaves in a particular simulation context.

It may configure:

- Products and services.
- Available capabilities.
- API exposure.
- Supported data categories.
- Consent requirements.
- Business rules.
- Transaction limits.
- Simulation policies.
- Synthetic data generation.
- Error and response behavior.

A participant may eventually have multiple profiles for different experiments or versions of its simulated behavior.

### 3.4 Capability

A Capability describes an operation or function that a participant can expose.

Potential capabilities include:

- Account information.
- Account balances.
- Transaction history.
- Customer identification.
- Payment initiation.
- Beneficiary management.
- Credit information.
- Loan application status.
- Insurance policy information.
- Pension information.
- Investment portfolio information.
- Business financial information.

Capabilities must be modeled independently from institutional categories. Different types of participants may expose overlapping capabilities.

### 3.5 Simulation Engine

The Simulation Engine executes participant behavior using shared platform services and profile-specific configuration.

It should not contain hardcoded behavior for individual institutions.

The engine is responsible for:

- Loading the participant profile.
- Resolving available capabilities.
- Applying configured business rules.
- Validating authorization and consent.
- Reading or generating synthetic data.
- Producing deterministic responses where required.
- Recording simulation events.
- Returning standardized results and errors.

## 4. Participant Representation Models

Oquira will distinguish three representation models.

| Model | Description |
|---|---|
| Verified real institution | Institutional identity and relevant attributes have been checked against reliable evidence. |
| Real institution experimental profile | A real institution is represented in an experimental simulation without implying an actual integration. |
| Synthetic archetype | A fictional participant represents a type of institution or financial business model. |

These models must remain distinguishable throughout the application, documentation, API responses, and user interface.

A real institution experimental profile must never be described as an operational connection.

## 5. Conceptual Domain Structure

The initial domain model includes the following concepts:

- Institution
- Participant
- ParticipantProfile
- ParticipantCapability
- Product
- SimulationPolicy
- SyntheticDataset
- ConsentPolicy
- EvidenceRecord
- SimulationSession
- SimulationEvent

### Conceptual relationships

- An Institution may be associated with zero or more Participants.
- A Participant may have one or more Participant Profiles.
- A Participant Profile may expose multiple Capabilities.
- A Capability may be shared by different participant categories.
- A Participant Profile may reference multiple Products and Simulation Policies.
- A Simulation Session uses a specific Participant Profile.
- A Simulation Session produces Simulation Events.
- An Evidence Record supports institutional identity or verification claims.
- A Synthetic Dataset supplies simulated financial information to a participant profile.

These relationships are conceptual. They do not yet prescribe a final relational database schema.

## 6. Institutional Identity and Research Registry

The institutional research registry and the operational participant model serve different purposes.

The registry preserves institutional observations and source references.

The participant model defines which actors are configured for Oquira experiments.

The relationship should be explicit:

- A registry identity may exist without an associated participant.
- A participant may reference a registry identity.
- A synthetic participant may have no real institutional identity.
- A registry record must not automatically become an enabled participant.
- Historical presence in a source does not establish current authorization or operational status.

Institutional verification and participant activation must be separate processes.

## 7. Shared Simulation Architecture

The proposed simulation flow is:

1. A consumer application or authorized client requests a capability.
2. The platform identifies the participant and active profile.
3. The authorization layer validates the requesting client and applicable consent.
4. The capability resolver checks whether the profile exposes the requested operation.
5. The simulation engine applies the relevant policies and business rules.
6. The data layer retrieves or generates synthetic information.
7. The engine returns a standardized response.
8. The platform records the relevant simulation event.

Participant-specific behavior should be configuration-driven wherever practical.

Shared technical components should handle common concerns such as authentication, authorization, consent, validation, logging, and error handling.

## 8. Synthetic Data and Simulation Boundaries

The initial platform must use synthetic financial data.

Potential synthetic objects include:

- Customers.
- Accounts.
- Balances.
- Transactions.
- Payment beneficiaries.
- Loans.
- Credit applications.
- Insurance policies.
- Pension accounts.
- Investment portfolios.
- Business financial records.

Synthetic datasets must be clearly identified as simulated.

The platform should avoid using real customer data or implying access to private institutional databases.

Simulation policies should support controlled scenarios such as:

- Successful operations.
- Insufficient funds.
- Invalid account information.
- Rejected transactions.
- Pending operations.
- Authorization failures.
- Expired consent.
- Rate limits.
- Temporary service unavailability.

Where reproducibility matters, scenarios should support deterministic inputs and predictable outputs.

## 9. API and Interoperability Model

The platform should expose standardized capabilities through documented APIs.

OpenAPI specifications should describe:

- Request and response schemas.
- Authentication requirements.
- Authorization scopes.
- Consent requirements.
- Error responses.
- Versioning.
- Simulation-specific behavior.

The participant profile determines which capabilities are available.

The API contract should remain independent of any specific institution's internal systems.

This creates an environment in which different participant profiles can be tested against common interoperability contracts.

## 10. Security and Consent

The participant simulation model must integrate with Oquira's security architecture.

Relevant controls include:

- Authentication of applications and users.
- Client authorization.
- Explicit consent where personal financial data is involved.
- Scope-based access control.
- Consent expiration and revocation.
- Audit trails.
- Data minimization.
- Separation of participant configuration and customer data.
- Protection of administrative operations.

A simulation must not bypass consent and authorization controls simply because its data is synthetic.

## 11. Initial Technical Architecture

The first implementation should use the existing modular monolith direction.

Potential modules include:

- Institutional Registry.
- Participant Management.
- Participant Configuration.
- Capability Catalog.
- Consent Management.
- Simulation Engine.
- Synthetic Data Management.
- API Gateway or API Exposure Layer.
- Audit and Observability.

These are logical module boundaries, not a commitment to independent microservices.

PostgreSQL can provide persistence for structured platform data. OpenAPI can define API contracts. Keycloak can support authentication and identity management.

Implementation details should be refined through subsequent architecture decisions.

## 12. MVP Scope

The initial implementation should prioritize:

1. A common Participant model.
2. Participant representation types.
3. Configurable Participant Profiles.
4. A shared Capability Catalog.
5. A small set of reusable simulation policies.
6. Synthetic customer, account, and transaction data.
7. A common simulation execution flow.
8. API documentation.
9. Basic audit events.

The first MVP should not attempt to reproduce every financial product or institutional process.

It should demonstrate that different participant types can operate through the same platform architecture.

## 13. Future Extensions

The architecture should leave room for:

- Multi-party payment networks.
- Account aggregation.
- Cooperative interoperability.
- Embedded finance.
- Insurance data exchange.
- Pension and investment data access.
- Business financial data exchange.
- Alternative credit information.
- Digital identity.
- Programmable payments.
- AI-assisted financial services.
- Autonomous financial agents.
- Cross-border financial interoperability.

These are exploration opportunities, not commitments to implement them in the initial MVP.

## 14. Open Architecture Questions

The following questions require further design work:

- Should participant profiles be versioned and independently publishable?
- How should capabilities be grouped into API products?
- How should participant-specific policies be configured and validated?
- How should simulation sessions preserve reproducibility?
- How should synthetic datasets be shared or isolated between participants?
- Which domain objects should be globally standardized?
- How should the platform represent participants that operate in multiple financial sectors?
- How should institutional evidence and verification history be maintained?

## 15. Guiding Principle

Oquira should model the financial ecosystem as a network of diverse participants connected through shared interoperability, identity, consent, and simulation capabilities.

The platform should be institution-agnostic at the technical level while preserving the distinct characteristics of each participant.

One shared simulation engine, multiple configurable participants, and explicit boundaries between real-world evidence and experimental behavior form the foundation of this model.
