package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevCountryMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevCountryRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaCountry;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devCountryWriter")
public class DevCountryAdapter implements TargetWriter<RecetaCountry> {

    private final DevCountryRepository repository;
    private final DevCountryMapper mapper;

    public DevCountryAdapter(DevCountryRepository repository, DevCountryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaCountry record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaCountry record) {
        return repository.save(mapper.toEntity(record))
                .then()
                .onErrorMap(ex -> new TargetWriteException("Error writing country to dev" + ": " + ex.getMessage(), ex));
    }
}
