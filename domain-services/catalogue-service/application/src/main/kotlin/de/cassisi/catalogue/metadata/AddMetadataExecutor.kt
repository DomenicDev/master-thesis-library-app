package de.cassisi.catalogue.metadata

class AddMetadataExecutor(private val repository: AddMetadataRepository) : AddMetadata {

    override fun execute(command: AddMetadataCommand) {
        val metadata = MetadataFactory.create(command)
        repository.save(metadata)
    }
}