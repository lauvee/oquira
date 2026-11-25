// @ts-check

/** @type {import('@docusaurus/plugin-content-docs').SidebarsConfig} */
const sidebars = {
  tutorialSidebar: [
    'intro',
    {
      type: 'category',
      label: 'Product',
      link: {
        type: 'generated-index',
        title: 'Product',
        description: 'Vision, objectives, and roadmap.',
      },
      items: [
        'product/vision',
        'product/roadmap',
        'product/exploration-framework',
        'product/mvp-participant-selection',
      ],
    },
    {
      type: 'category',
      label: 'Research',
      link: {
        type: 'generated-index',
        title: 'Research',
      },
      items: [
        'research/open-finance-overview',
        'research/dominican-republic-landscape',
        'research/financial-ecosystem-map',
        'research/institutional-registry',
      ],
    },
    {
      type: 'category',
      label: 'Architecture',
      link: {
        type: 'generated-index',
        title: 'Architecture',
      },
      items: [
        'architecture/system-overview',
        'architecture/participant-simulation-model',
        'architecture/domain-data-model',
        'architecture/simulation-engine-contract',
        'architecture/capability-catalog',
        'architecture/participant-capability-assignment-model',
      ],
    },
    {
      type: 'category',
      label: 'Architectural Decisions (ADRs)',
      link: {
        type: 'generated-index',
        title: 'Architectural Decisions',
        description: 'Accepted architectural decisions governing Oquira.',
      },
      items: [
        'decisions/modular-monolith',
        'decisions/participant-domain-model',
        'decisions/participant-profile-versioning',
        'decisions/shared-simulation-engine',
        'decisions/synthetic-data-and-reproducibility',
      ],
    },
    {
      type: 'category',
      label: 'Security',
      link: {
        type: 'generated-index',
        title: 'Security',
      },
      items: [
        'security/security-principles',
      ],
    },
  ],
};

export default sidebars;
