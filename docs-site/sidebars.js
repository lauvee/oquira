
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
        'decisions/modular-monolith',
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
