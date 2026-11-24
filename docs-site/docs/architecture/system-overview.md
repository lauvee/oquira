---
title: System Overview
description: Initial system architecture of Oquira
---

# System Overview

## Architecture approach

Oquira will initially use a modular monolith.

This approach keeps deployment and operations simple while allowing clear domain boundaries and future evolution.

## Proposed technology stack

| Layer | Technology |
|---|---|
| Backend | Java and Spring Boot |
| Frontend | Next.js, React and TypeScript |
| Database | PostgreSQL |
| Identity | Keycloak |
| Migrations | Flyway |
| Testing | JUnit, Mockito and Testcontainers |
| Infrastructure | Docker |
| CI/CD | GitHub Actions |

## Main components

### Consumer Application

Provides the user experience for connecting simulated institutions, authorizing consent, and viewing financial information.

### Bank Simulator

Simulates financial institutions, accounts, balances, transactions, and APIs.

### Developer Platform

Provides API documentation, integration examples, and developer-oriented resources.

## Architectural principles

- Modular design.
- Explicit domain boundaries.
- Secure defaults.
- API-first integration.
- Synthetic data for experimentation.
- Automated testing.
- Avoid premature distributed-system complexity.

## Initial deployment model

The first implementation will be developed and tested locally using containerized dependencies where appropriate.

Production hosting is outside the initial discovery scope.
