# Oquira Documentation Website

This directory contains the documentation website for **Oquira**, an experimental and educational project exploring Open Finance, starting with the Dominican Republic.

The website is built with [Docusaurus](https://docusaurus.io/).

## Requirements

- Node.js 24 or a compatible version.
- npm.

## Installation

From this directory, install the dependencies:

```bash
npm ci
```

## Local Development

Start the local development server:

```bash
npm run start
```

The website will be available at the local address shown in the terminal. Changes to documentation files are generally reflected automatically.

## Production Build

Generate the optimized static website:

```bash
npm run build
```

The generated files are placed in the `build/` directory.

To preview the production build locally:

```bash
npm run serve
```

## Documentation Structure

Documentation content is organized under `docs/`:

- `product/` - Product vision and roadmap.
- `research/` - Open Finance research and Dominican Republic landscape.
- `architecture/` - System architecture and technical decisions.
- `security/` - Security principles and considerations.
- `decisions/` - Architecture Decision Records (ADRs).

Navigation and sidebar configuration are maintained in `sidebars.js`.

## Deployment

The documentation website is configured for GitHub Pages.

Deployment is managed through the GitHub Actions workflow:

```text
.github/workflows/deploy.yml
```

Changes pushed to the `main` branch that affect the documentation website or deployment workflow trigger the configured build and deployment process.

For deployment configuration, see the repository's GitHub Pages settings.

## Contributing

Contributions to the documentation are welcome. Please review the repository's `CONTRIBUTING.md` and `CODE_OF_CONDUCT.md` before submitting changes.

## Project Status

Oquira is currently in the Discovery and Research phase. Its documentation describes an evolving experimental project, not a production-ready financial service.
