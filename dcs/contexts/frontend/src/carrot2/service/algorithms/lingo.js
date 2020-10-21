import descriptor from "./descriptors/org.carrot2.clustering.lingo.LingoClusteringAlgorithm.json";


const isContainer = descriptor => {
  const implementations = descriptor.implementations;
  if (implementations) {
    const implementationKeys = Object.keys(implementations);
    return implementationKeys.length === 1 && descriptor.type === implementations[implementationKeys[0]].type;
  }
  return false;
};


const depthFirstAttributes = descriptor => {
  const collect = (descriptor, target) => {
    Object.keys(descriptor.attributes).forEach(k => {
      const attribute = descriptor.attributes[k];

      if (!isContainer(attribute)) {
        target.push(attribute);
      }

      if (attribute.attributes) {
        collect(attribute, target);
      }
      if (attribute.implementations) {
        const keys = Object.keys(attribute.implementations);
        if (keys.length === 1) {
          collect(attribute.implementations[keys[0]], target);
        }
      }
    });

    return target;
  };

  return collect(descriptor, []);
};

const descriptorsById = depthFirstAttributes(descriptor).reduce((map, a) => {
  map.set(a.id, a);
  return map;
}, new Map());
console.log(descriptorsById);

const parseNumberConstraintValue = constraint => {
  const split = constraint.split(/\s+/);
  return parseFloat(split[2]);
};

const settingConfigFromIntegerDescriptor = descriptor => {
  const c1 = parseNumberConstraintValue(descriptor.constraints[0]);
  const c2 = parseNumberConstraintValue(descriptor.constraints[1]);
  const min = Math.min(c1, c2);
  const max = Math.max(c1, c2);

  return {
    type: "number",
    min: min,
    max: max,
    step: (max - min) / 10
  }
};

const settingFrom = (id, overrides) => {
  const descriptor = descriptorsById.get(id);
  if (!descriptor) {
    throw new Error(`Unknown attribute ${id}.`);
  }

  const setting = {
    id: id,
    label: descriptor.javadoc.summary,
    description: descriptor.javadoc.text
  };

  switch (descriptor.type) {
    case "Double":
    case "Float":
    case "Integer":
      Object.assign(setting, settingConfigFromIntegerDescriptor(descriptor));
      break;

    case "Boolean":
      setting.type = "boolean";
      break;

    default:
      throw new Error(`Unsupported type ${descriptor.type}`);
  }

  return Object.assign(setting, overrides);
};


export const lingo = {
  label: "Lingo",
  description: "Well-described flat clusters.",
  descriptionHtml: "creates well-described flat clusters. Does not scale beyond a few thousand search results. Available as part of the open source <a href='http://project.carrot2.org' target='_blank'>Carrot<sup>2</sup> framework</a>.",
  tag: "open source",
  getSettings: () => {
    return [
      {
        id: "lingo:clusters",
        type: "group",
        label: "Clusters",
        settings: [
          settingFrom("desiredClusterCount"),
          settingFrom("clusterBuilder.clusterMergingThreshold"),
          settingFrom("scoreWeight", { label: "Size-score sorting ratio" }),
          settingFrom("preprocessing.documentAssigner.exactPhraseAssignment")
        ]
      },
      {
        id: "lingo:labels",
        type: "group",
        label: "Cluster labels",
        settings: [
          settingFrom("clusterBuilder.phraseLabelBoost"),
          settingFrom("clusterBuilder.phraseLengthPenaltyStart"),
          settingFrom("clusterBuilder.phraseLengthPenaltyStop")
        ]
      }
    ];
  }
};