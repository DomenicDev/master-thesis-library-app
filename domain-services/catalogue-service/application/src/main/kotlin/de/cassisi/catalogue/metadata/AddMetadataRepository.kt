package de.cassisi.catalogue.metadata

import de.cassisi.catalogue.common.AggregateRepository

interface AddMetadataRepository : AggregateRepository<MetadataId, Metadata>