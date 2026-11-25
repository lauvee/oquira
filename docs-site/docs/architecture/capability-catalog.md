---
title: Capability Catalog
description: Standardized functional capability taxonomy for Oquira financial participants
---

# Capability Catalog

- Status: Proposed
- Version: 0.1
- Date: 2026-11-24

## 1. Purpose

The Capability Catalog defines the standardized functional capabilities that financial ecosystem participants may expose within Oquira.

It establishes a common vocabulary for describing what a participant can do, independently of its institutional category, products, internal systems, or implementation.

The catalog supports traditional financial institutions, cooperatives, fintech companies, payment providers, insurers, pension administrators, investment intermediaries, infrastructure providers, and emerging financial participants.

## 2. Architectural Principles

The Capability Catalog follows these principles:

1. Standardization: capabilities use consistent identifiers and definitions.
2. Participant independence: capabilities are not owned exclusively by a specific institution.
3. Sector neutrality: the same capability may be supported by participants from different sectors.
4. Composability: complex financial scenarios may combine multiple capabilities.
5. Versioning: capability definitions evolve without silently changing historical behavior.
6. Explicit support: a participant must explicitly declare its supported capabilities.
7. Policy awareness: capability availability does not imply unconditional execution.
8. Extensibility: new capabilities and financial models can be introduced without redesigning the catalog.
9. Simulation first: initial capability implementations operate with synthetic data and simulated behavior.

## 3. What Is a Capability?

A capability represents a standardized functional operation or ability that a participant can provide within the Oquira ecosystem.

Examples:

- Reading account information.
- Retrieving transaction history.
- Initiating a simulated payment.
- Providing credit information.
- Quoting an insurance product.
- Retrieving an investment portfolio.
- Exchanging business financial information.
- Verifying a simulated identity attribute.

A capability describes functional behavior, not a specific API endpoint, product, institution, or technology.

### 3.1 Capability vs. Product

A product represents a financial offering, such as a savings account, credit card, loan, insurance policy, or investment fund.

A capability represents an operation that can be performed in relation to a product or financial service.

For example, multiple participants may offer savings accounts and support account balance inquiry, but their products, eligibility rules, and simulated responses may differ.

### 3.2 Capability vs. API

An API endpoint is a technical interface used to invoke functionality.

A capability is the functional concept exposed through that interface.

A capability may eventually be exposed through one or more API operations without changing its business meaning.

### 3.3 Capability vs. Participant

A participant is an actor in the financial ecosystem.

A participant declares which capabilities it supports through its published profile version.

Support may vary by environment, product, policy, or participant configuration.

## 4. Capability Domain Taxonomy

The initial catalog is organized into functional domains.

These domains are conceptual groupings and do not imply that every participant must support every capability.

### 4.1 Account and Financial Information

Capabilities related to financial account information.

Initial capabilities:

- `account.list.read` - Retrieve available financial accounts.
- `account.detail.read` - Retrieve account details.
- `account.balance.read` - Retrieve account balances.
- `account.transactions.read` - Retrieve transaction history.
- `account.statement.read` - Retrieve account statements.
- `account.status.read` - Retrieve account status.
- `account.beneficiaries.read` - Retrieve registered beneficiaries.

Potential participants:

- Banks.
- Savings and credit associations.
- Cooperatives.
- Digital banks.
- Other account providers.

### 4.2 Payments and Transfers

Capabilities related to payment initiation and movement of funds in simulated environments.

Initial capabilities:

- `payment.initiate` - Initiate a simulated payment.
- `payment.status.read` - Retrieve payment status.
- `payment.detail.read` - Retrieve payment details.
- `payment.refund.initiate` - Initiate a simulated refund.
- `payment.cancel` - Request cancellation of an eligible simulated payment.
- `transfer.initiate` - Initiate a simulated account transfer.
- `transfer.status.read` - Retrieve transfer status.
- `payment.request.create` - Create a payment request.
- `payment.request.status.read` - Retrieve payment request status.

Potential participants:

- Banks.
- Payment service providers.
- Acquirers.
- Payment aggregators.
- Digital wallets.
- Fintech companies.

These capabilities do not execute real payments or settle real funds.

### 4.3 Credit and Lending

Capabilities related to credit products, applications, and simulated credit information.

Initial capabilities:

- `credit.products.read` - Retrieve available credit products.
- `credit.application.create` - Create a simulated credit application.
- `credit.application.status.read` - Retrieve application status.
- `credit.offer.read` - Retrieve a simulated credit offer.
- `credit.loan.detail.read` - Retrieve loan information.
- `credit.repayment.schedule.read` - Retrieve repayment schedules.
- `credit.repayment.simulate` - Simulate a repayment.
- `credit.exposure.read` - Retrieve simulated credit exposure.
- `credit.assessment.simulate` - Execute an experimental credit assessment.

Potential participants:

- Banks.
- Credit cooperatives.
- Savings and credit banks.
- Microfinance institutions.
- Digital lenders.
- Fintech companies.

Experimental assessments must not be represented as actual credit decisions.

### 4.4 Customer and Identity Information

Capabilities related to simulated customer profiles, identity attributes, and verification.

Initial capabilities:

- `customer.profile.read` - Retrieve a synthetic customer profile.
- `customer.profile.update` - Update permitted synthetic profile attributes.
- `identity.attribute.read` - Retrieve a simulated identity attribute.
- `identity.verify.simulate` - Execute a simulated identity verification.
- `identity.document.status.read` - Retrieve synthetic document verification status.
- `customer.relationship.read` - Retrieve simulated customer relationships.

Potential participants:

- Banks.
- Fintech companies.
- Identity providers.
- Digital onboarding platforms.
- Specialized verification providers.

These operations must not imply verification against official identity databases.

### 4.5 Consent and Data Authorization

Capabilities related to permission management and data sharing.

Initial capabilities:

- `consent.create` - Create a simulated consent grant.
- `consent.status.read` - Retrieve consent status.
- `consent.revoke` - Revoke a simulated consent grant.
- `consent.detail.read` - Retrieve consent details.
- `data.access.authorize` - Evaluate simulated data access authorization.
- `data.sharing.initiate` - Initiate a simulated data-sharing operation.
- `data.sharing.status.read` - Retrieve data-sharing status.

Potential participants:

- Data providers.
- Data consumers.
- Banks.
- Fintech companies.
- Consent management platforms.
- Open Finance intermediaries.

Simulated consent must remain distinguishable from legally valid authorization to access real financial data.

### 4.6 Cards and Digital Wallets

Capabilities related to card products and digital wallet operations.

Initial capabilities:

- `card.list.read` - Retrieve synthetic card records.
- `card.detail.read` - Retrieve card details.
- `card.status.read` - Retrieve card status.
- `card.control.update` - Update supported simulated card controls.
- `card.transaction.read` - Retrieve card transactions.
- `wallet.balance.read` - Retrieve wallet balance.
- `wallet.transaction.read` - Retrieve wallet transactions.
- `wallet.payment.initiate` - Initiate a simulated wallet payment.
- `wallet.account.link` - Link a simulated funding account.

Potential participants:

- Banks.
- Card issuers.
- Digital wallets.
- Payment fintechs.
- Electronic money providers.

### 4.7 Remittances and Foreign Exchange

Capabilities related to international money transfers and currency exchange.

Initial capabilities:

- `remittance.quote.create` - Generate a simulated remittance quote.
- `remittance.initiate` - Initiate a simulated remittance.
- `remittance.status.read` - Retrieve remittance status.
- `remittance.beneficiary.read` - Retrieve beneficiary information.
- `fx.quote.read` - Retrieve an exchange rate quote.
- `fx.conversion.simulate` - Simulate currency conversion.
- `fx.rate.read` - Retrieve configured exchange rates.

Potential participants:

- Remittance providers.
- Banks.
- Exchange agents.
- International payment providers.
- Fintech companies.

Exchange rates must identify their source or synthetic classification and applicable timestamp.

### 4.8 Insurance

Capabilities related to insurance products, policies, and claims.

Initial capabilities:

- `insurance.products.read` - Retrieve available insurance products.
- `insurance.quote.create` - Generate an insurance quote.
- `insurance.application.create` - Create a simulated insurance application.
- `insurance.policy.read` - Retrieve policy information.
- `insurance.coverage.read` - Retrieve coverage details.
- `insurance.premium.simulate` - Simulate premium calculation.
- `insurance.claim.create` - Create a simulated claim.
- `insurance.claim.status.read` - Retrieve claim status.

Potential participants:

- Insurance companies.
- Insurance intermediaries.
- Digital insurance platforms.
- Banks offering insurance distribution.

### 4.9 Investments and Securities

Capabilities related to investment products and capital markets.

Initial capabilities:

- `investment.products.read` - Retrieve investment products.
- `investment.portfolio.read` - Retrieve a synthetic investment portfolio.
- `investment.position.read` - Retrieve investment positions.
- `investment.performance.read` - Retrieve portfolio performance.
- `investment.order.simulate` - Simulate an investment order.
- `investment.order.status.read` - Retrieve simulated order status.
- `securities.instrument.read` - Retrieve instrument information.
- `securities.marketdata.read` - Retrieve configured market data.

Potential participants:

- Securities intermediaries.
- Investment managers.
- Brokerage firms.
- Fund administrators.
- Market data providers.

Simulated investment operations must not be interpreted as real securities orders or executions.

### 4.10 Pensions and Retirement

Capabilities related to pension accounts and retirement services.

Initial capabilities:

- `pension.account.read` - Retrieve pension account information.
- `pension.balance.read` - Retrieve accumulated balance.
- `pension.contributions.read` - Retrieve contribution history.
- `pension.statement.read` - Retrieve pension statements.
- `pension.projection.simulate` - Simulate retirement projections.
- `pension.beneficiary.read` - Retrieve beneficiary information.

Potential participants:

- Pension fund administrators.
- Retirement service providers.
- Relevant financial institutions.

### 4.11 Business and SME Finance

Capabilities related to business financial information and services.

Initial capabilities:

- `business.profile.read` - Retrieve a synthetic business profile.
- `business.accounts.read` - Retrieve business account information.
- `business.cashflow.read` - Retrieve synthetic cash flow information.
- `business.financials.read` - Retrieve financial summaries.
- `business.credit.exposure.read` - Retrieve simulated credit exposure.
- `business.payment.initiate` - Initiate a simulated business payment.
- `business.invoice.read` - Retrieve synthetic invoice information.
- `business.reconciliation.simulate` - Simulate financial reconciliation.

Potential participants:

- Banks.
- Cooperatives.
- Accounting platforms.
- ERP providers.
- Payment companies.
- SME fintech platforms.

### 4.12 Financial Infrastructure and Interoperability

Capabilities related to financial ecosystem connectivity and infrastructure.

Initial capabilities:

- `participant.directory.read` - Retrieve participant directory information.
- `participant.capabilities.read` - Retrieve declared participant capabilities.
- `payment.routing.simulate` - Simulate payment routing.
- `payment.clearing.simulate` - Simulate clearing processes.
- `payment.settlement.simulate` - Simulate settlement processes.
- `institution.status.read` - Retrieve participant operational status.
- `api.availability.read` - Retrieve simulated API availability.
- `interoperability.exchange.simulate` - Simulate an inter-participant data exchange.

Potential participants:

- Payment infrastructure operators.
- Financial market infrastructure providers.
- Banks.
- Payment service providers.
- Open Finance platforms.

### 4.13 Intelligence and Automation

Capabilities related to analytical services and emerging financial experiences.

Initial capabilities:

- `financial.insights.generate` - Generate synthetic financial insights.
- `financial.categorization.simulate` - Simulate transaction categorization.
- `financial.forecast.generate` - Generate experimental financial forecasts.
- `financial.recommendation.generate` - Generate simulated recommendations.
- `financial.agent.execute` - Execute an experimental financial agent task.
- `risk.indicator.generate` - Generate simulated risk indicators.
- `fraud.indicator.simulate` - Simulate fraud indicators.

Potential participants:

- Financial institutions.
- Fintech companies.
- Analytics providers.
- AI platforms.
- Experimental autonomous agents.

These capabilities are exploratory and must not be interpreted as validated financial advice, actual fraud detection, or production risk decisions.

## 5. Capability Definition Structure

The global catalog separates the stable identity of a capability from its versioned functional contract.

### 5.1 Capability Identity

The Capability entity represents the stable identity of an operation.

| Attribute | Description |
|---|---|
| capabilityId | Stable unique capability identifier |
| code | Stable machine-readable capability code |
| name | Human-readable name |
| domain | Functional domain |
| description | General functional purpose |
| lifecycleStatus | Lifecycle status of the capability identity |

The capability code must remain stable across compatible versions.

A capability identity must not contain participant-specific configuration.

### 5.2 Capability Version

CapabilityVersion represents an immutable version of the functional contract.

| Attribute | Description |
|---|---|
| capabilityVersionId | Unique version identifier |
| capabilityId | Parent capability identity |
| versionNumber | Semantic version |
| inputSchema | Expected input structure |
| outputSchema | Expected output structure |
| executionMode | Synchronous or asynchronous |
| requiredPermissions | Required authorization context |
| dependencies | Other capabilities or domain requirements |
| dataClassification | Applicable data classification |
| lifecycleStatus | Draft, proposed, active, deprecated, or retired |
| publishedAt | Publication timestamp |

Capability versions define the exact behavior and contract expected by the Simulation Engine.

Published versions must remain immutable.

A new version must be created when the contract or behavior changes.

Participant assignments must reference an exact CapabilityVersion, not merely a capability code.

### 5.3 Versioning Rules

The catalog follows semantic versioning:

- Major: breaking changes to inputs, outputs, permissions, or execution semantics.
- Minor: backward-compatible functional extensions.
- Patch: compatible corrections to documentation or metadata.

A published version must remain resolvable for historical simulation sessions.

## 6. Participant Capability Assignment

A participant declares its supported capabilities through assignments associated with a published ParticipantProfileVersion.

The assignment is represented by ParticipantCapability.

Each assignment must reference:

- A specific participant profile version.
- An exact CapabilityVersion.
- An availability status.
- Supported execution environments.
- Participant-specific configuration.
- Required permissions.
- Optional effective dates.

### 6.1 Availability Status

| Status | Description |
|---|---|
| ENABLED | Capability is available under the assignment conditions |
| DISABLED | Capability is explicitly disabled |
| CONDITIONAL | Capability is available only when defined conditions are met |
| DEPRECATED | Capability remains identifiable but is discouraged for new use |
| UNAVAILABLE | Capability is not currently executable |

The absence of an assignment must never imply that a participant supports a capability.

### 6.2 Product Associations

ParticipantCapabilityProduct associates an assignment with products represented by ParticipantProfileProduct.

The relationship supports:

- One capability associated with multiple products.
- Multiple capabilities associated with one product.
- Capabilities that are not tied to a specific product.

Product associations must belong to the same participant profile version as the capability assignment.

### 6.3 Policy Associations

ParticipantCapabilityPolicy associates an assignment with one or more exact SimulationPolicyVersion references.

The assignment may define evaluation ordering and permitted configuration overrides.

Policies govern simulated behavior and must not be confused with capability definitions.

### 6.4 Environment and Configuration

Assignments must explicitly declare supported environments.

The initial environments are:

- DEVELOPMENT
- TEST
- DEMO

Participant-specific behavior belongs in assignment configuration, not in the global capability definition.

Published assignments are immutable because they belong to immutable participant profile versions.

A capability being present in the global catalog does not imply that every participant supports it.

## 7. Capability Lifecycle

The catalog distinguishes the lifecycle of a capability identity from the lifecycle of its individual versions.

### 7.1 Capability Identity Lifecycle

A capability identity may be maintained, deprecated, or retired independently of its published historical versions.

### 7.2 Capability Version Lifecycle

| Status | Description |
|---|---|
| DRAFT | Version is being designed |
| PROPOSED | Version is ready for review |
| ACTIVE | Version is available for new participant configurations |
| DEPRECATED | Version remains identifiable but is discouraged for new assignments |
| RETIRED | Version is no longer available for new assignments |

Published capability versions must remain identifiable for historical simulation sessions.

A breaking change to input, output, permissions, or execution semantics requires a new major version.

Backward-compatible extensions may use minor versions.

Documentation or metadata corrections may use patch versions.

Deprecation or retirement must not silently invalidate historical simulation references.

## 8. Capability Composition

A complex scenario may require several capabilities.

For example, an experimental personal finance experience may combine:

1. `account.list.read`
2. `account.balance.read`
3. `account.transactions.read`
4. `consent.status.read`
5. `financial.categorization.simulate`
6. `financial.insights.generate`

A business financing scenario may combine:

1. `business.profile.read`
2. `business.cashflow.read`
3. `credit.products.read`
4. `credit.application.create`
5. `credit.assessment.simulate`

Capability composition must preserve the authorization, policy, versioning, and traceability requirements of each operation.

## 9. Relationship with the Simulation Engine

The Capability Catalog provides standardized definitions consumed by the shared Simulation Engine.

For each execution request, the engine must:

1. Resolve the participant and exact published profile version.
2. Resolve the requested capability assignment within that profile version.
3. Resolve the exact CapabilityVersion referenced by the assignment.
4. Validate availability status and supported environment.
5. Validate the requested inputs against the capability version schema.
6. Resolve applicable product associations.
7. Resolve exact policy versions associated with the assignment.
8. Validate permissions and applicable conditions.
9. Resolve the required synthetic dataset versions.
10. Execute the capability through shared execution mechanisms.
11. Validate and normalize the output.
12. Record the resolved dependency versions and execution events.

The engine must not rely on hardcoded institution-specific branches.

The engine must reject unsupported assignments, unavailable versions, invalid environments, and unmet conditions.

Every simulation session must preserve the exact capability version and assignment used.

## 10. Relationship with the Domain Data Model

The Capability Catalog maps to the following domain entities:

| Domain Entity | Responsibility |
|---|---|
| Capability | Stable global capability identity |
| CapabilityVersion | Immutable version of the capability contract |
| ParticipantCapability | Assignment of an exact capability version to a profile version |
| ParticipantCapabilityProduct | Association between an assignment and profile products |
| ParticipantCapabilityPolicy | Association between an assignment and exact policy versions |
| ParticipantProfileVersion | Immutable participant configuration context |
| ParticipantProfileProduct | Product configuration within a profile version |
| SimulationPolicy | Stable policy identity |
| SimulationPolicyVersion | Immutable policy configuration version |
| SyntheticDataset | Stable synthetic dataset identity |
| SyntheticDatasetVersion | Immutable dataset version |
| SimulationSession | Execution record preserving resolved references |
| SimulationSessionDataset | Exact dataset versions used by a session |
| SimulationEvent | Execution event and traceability record |

The catalog defines global capability contracts.

The participant profile defines which exact capability versions are available and under which conditions.

The assignment defines product associations, policy references, permissions, environments, and participant-specific configuration.

The Simulation Engine resolves these relationships at execution time.

A global capability definition does not establish participant support, product availability, or real-world integration.

## 11. Initial Catalog Governance

The catalog must be maintained as a versioned platform artifact.

New capabilities should be evaluated according to:

- Functional uniqueness.
- Relevance to financial ecosystem exploration.
- Reusability across participants.
- Clear input and output semantics.
- Security and authorization implications.
- Synthetic data requirements.
- Compatibility with existing capabilities.

Duplicate or overly specialized capabilities should be avoided.

Institution-specific differences should normally be expressed through configuration and policies rather than new global capability identifiers.

## 12. Implementation Priorities

The initial implementation should focus on a representative subset:

1. `account.list.read`
2. `account.balance.read`
3. `account.transactions.read`
4. `payment.initiate`
5. `payment.status.read`
6. `consent.create`
7. `consent.status.read`
8. `credit.products.read`
9. `credit.application.create`
10. `participant.capabilities.read`

This subset provides enough functional diversity to validate the shared execution architecture.

It does not limit the long-term catalog.

## 13. Open Design Questions

The following questions remain open:

- Should capability definitions use JSON Schema for input and output contracts?
- How should capabilities depend on one another?
- Should capabilities support multiple execution modes?
- How should capability-specific permissions map to simulated consent?
- How should capability assignments inherit configuration from profile versions?
- How should long-running operations expose progress and cancellation?
- How should capabilities be grouped into standardized financial journeys?
- How should experimental AI capabilities be governed and versioned?
- How should capability compatibility be tested across engine versions?

These questions must be resolved during detailed technical design.

## 14. Guiding Principle

The Capability Catalog describes what participants can do.

Participant profiles describe which capabilities they support and under what conditions.

Products describe the financial offerings associated with those capabilities.

Policies define the rules governing execution.

The Simulation Engine coordinates and executes the resulting behavior.

This separation allows Oquira to represent a broad and evolving financial ecosystem through a common functional language without imposing identical behavior on every participant.
