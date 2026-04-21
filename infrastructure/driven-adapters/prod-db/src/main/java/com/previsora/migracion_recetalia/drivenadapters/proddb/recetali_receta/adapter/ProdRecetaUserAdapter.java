package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaUserMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaUserRepository;
import com.previsora.migracion_recetalia.model.domain.RecetaUser;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Source reader adapter for {@code prod.recetali_receta.user}.
 * <p>
 * Reads all users from prod using paginated queries and maps to domain model.
 */
@Component("prodRecetaUserReader")
public class ProdRecetaUserAdapter implements SourceReader<RecetaUser> {

    private static final Logger log = LoggerFactory.getLogger(ProdRecetaUserAdapter.class);

    private final ProdRecetaUserRepository repository;
    private final ProdRecetaUserMapper mapper;

    public ProdRecetaUserAdapter(ProdRecetaUserRepository repository,
                                  ProdRecetaUserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaUser> readAll(int batchSize) {
        log.info("Reading all users from prod.recetali_receta.user with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException(
                "Failed to read from prod.recetali_receta.user", ex));
    }

    @Override
    public Flux<RecetaUser> readById(String sourceRecordId, int batchSize) {
        log.info("Reading user {} from prod.recetali_receta.user", sourceRecordId);
        return repository.findBySourceId(sourceRecordId)
                .map(mapper::toDomain)
                .flux()
                .onErrorMap(ex -> new SourceReadException(
                        "Failed to read user " + sourceRecordId + " from prod.recetali_receta.user", ex));
    }
}
