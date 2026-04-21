package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaUserMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaUserRepository;
import com.previsora.migracion_recetalia.model.domain.SecurityUser;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * Source reader adapter that reads from {@code prod.recetali_receta.user}
 * and derives {@link SecurityUser} domain objects.
 * <p>
 * There is NO securitydb in prod. This adapter reads RecetaUser records
 * and transforms them to SecurityUser using {@link SecurityUser#fromRecetaUser}.
 */
@Component("prodSecurityUserReader")
public class ProdSecurityUserAdapter implements SourceReader<SecurityUser> {

    private static final Logger log = LoggerFactory.getLogger(ProdSecurityUserAdapter.class);

    private final ProdRecetaUserRepository repository;
    private final ProdRecetaUserMapper mapper;

    public ProdSecurityUserAdapter(ProdRecetaUserRepository repository,
                                    ProdRecetaUserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<SecurityUser> readAll(int batchSize) {
        log.info("Reading all users from prod.recetali_receta.user (for SecurityUser derivation) with batchSize={}", batchSize);
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(entity -> SecurityUser.fromRecetaUser(mapper.toDomain(entity)))
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException(
                "Failed to read from prod.recetali_receta.user for security derivation", ex));
    }

    @Override
    public Flux<SecurityUser> readById(String sourceRecordId, int batchSize) {
        log.info("Reading user {} from prod.recetali_receta.user for SecurityUser derivation", sourceRecordId);
        return repository.findBySourceId(sourceRecordId)
                .map(entity -> SecurityUser.fromRecetaUser(mapper.toDomain(entity)))
                .flux()
                .onErrorMap(ex -> new SourceReadException(
                        "Failed to read user " + sourceRecordId + " from prod.recetali_receta.user for security derivation", ex));
    }
}
