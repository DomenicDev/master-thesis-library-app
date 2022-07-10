package de.cassisi.catalogue.book

import de.cassisi.catalogue.common.AggregateRepository

interface BookRepository : AggregateRepository<BookId, Book>