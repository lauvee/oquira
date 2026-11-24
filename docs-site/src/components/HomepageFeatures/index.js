
import clsx from 'clsx';
import Heading from '@theme/Heading';
import Link from '@docusaurus/Link';
import styles from './styles.module.css';

const FeatureList = [
  {
    title: 'Open Finance Research',
    description: (
      <>
        Explore Open Finance concepts, international developments,
        and the Dominican Republic financial ecosystem.
      </>
    ),
    link: '/docs/research/open-finance-overview',
    linkLabel: 'Explore research',
  },
  {
    title: 'Architecture & Engineering',
    description: (
      <>
        Understand Oquira&apos;s architecture, technical decisions,
        system components, and engineering roadmap.
      </>
    ),
    link: '/docs/architecture/system-overview',
    linkLabel: 'Explore architecture',
  },
  {
    title: 'Security & Trust',
    description: (
      <>
        Discover the security principles, privacy considerations,
        and responsible practices guiding the project.
      </>
    ),
    link: '/docs/security/security-principles',
    linkLabel: 'Explore security',
  },
];

function Feature({title, description, link, linkLabel}) {
  return (
    <div className={clsx('col col--4')}>
      <div className="text--center padding-horiz--md">
        <Heading as="h3">{title}</Heading>
        <p>{description}</p>
        <Link to={link}>{linkLabel} â†’</Link>
      </div>
    </div>
  );
}

export default function HomepageFeatures() {
  return (
    <section className={styles.features}>
      <div className="container">
        <div className="row">
          {FeatureList.map((props) => (
            <Feature key={props.title} {...props} />
          ))}
        </div>
      </div>
    </section>
  );
}

