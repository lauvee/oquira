---
title: Security Principles
description: Security principles and initial controls
---

# Security Principles

Security is a core design requirement, not a feature to add at the end.

## Identity and authentication

- Use established identity protocols.
- Evaluate OpenID Connect.
- Use OAuth 2.0 authorization flows appropriate to the client.
- Prefer Authorization Code with PKCE for public clients.

## Authorization

- Apply least privilege.
- Define explicit scopes.
- Validate authorization on every protected resource request.
- Enforce resource ownership and access boundaries.

## Consent management

- Record the purpose and scope of consent.
- Track creation, expiration, and revocation.
- Enforce consent status when accessing protected financial data.
- Maintain an auditable history of consent events.

## Data protection

- Use synthetic financial data.
- Avoid storing real banking credentials.
- Protect secrets and configuration.
- Minimize collected data.
- Avoid exposing sensitive values in logs.

## AI and LLM safety

- Do not allow an LLM to invent balances or transactions.
- Retrieve financial facts from deterministic application services.
- Validate structured model outputs.
- Avoid sending real sensitive financial information to external models.

## API security

- Enforce authentication and authorization on every protected endpoint.
- Verify resource ownership and tenant boundaries.
- Prevent insecure direct object references (IDOR).
- Validate input and reject unauthorized scope escalation.
- Apply rate limiting to sensitive endpoints.
- Use secure transport for all non-local environments.
- Avoid exposing tokens, credentials, or sensitive data in responses and logs.

## Consent lifecycle

Consent records should include:

- The user or data subject.
- The requesting application or client.
- The purpose of data access.
- The authorized data categories and scopes.
- Creation and expiration timestamps.
- Current consent status.
- Revocation timestamp, when applicable.

Consent states and transitions must be explicit.
Revoked or expired consent must not authorize subsequent
access to protected financial data.

Consent events must remain auditable.

## Audit and monitoring

- Record security-relevant events.
- Protect audit records against unauthorized modification.
- Avoid storing access tokens or unnecessary personal information.
- Record authorization failures and suspicious access patterns.
- Define retention policies for audit data.

## Important limitation

Using OAuth 2.0, OpenID Connect, or Keycloak does not automatically make an implementation FAPI-compliant or regulator-approved. Security claims must be validated against the relevant requirements.
