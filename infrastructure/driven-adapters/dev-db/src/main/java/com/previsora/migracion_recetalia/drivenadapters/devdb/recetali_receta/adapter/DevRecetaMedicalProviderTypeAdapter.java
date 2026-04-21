package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevRecetaMedicalProviderTypeMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaMedicalProviderTypeRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaMedicalProviderType;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devRecetaMedicalProviderTypeWriter")
public class DevRecetaMedicalProviderTypeAdapter implements TargetWriter<RecetaMedicalProviderType> {

    private final DevRecetaMedicalProviderTypeRepository repository;
    private final DevRecetaMedicalProviderTypeMapper mapper;

    public DevRecetaMedicalProviderTypeAdapter(DevRecetaMedicalProviderTypeRepository repository, DevRecetaMedicalProviderTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaMedicalProviderType record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaMedicalProviderType record) {
        return repository.save(mapper.toEntity(record))
                .then()
                .onErrorMap(ex -> new TargetWriteException("Error writing to recetali_receta.medical_provider_type" + ": " + ex.getMessage(), ex));
    }
}
