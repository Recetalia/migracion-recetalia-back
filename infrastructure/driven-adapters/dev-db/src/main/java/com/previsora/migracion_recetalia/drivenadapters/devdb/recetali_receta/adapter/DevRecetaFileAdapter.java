package com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.mapper.DevRecetaFileMapper;
import com.previsora.migracion_recetalia.drivenadapters.devdb.recetali_receta.repository.DevRecetaFileRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaFile;
import com.previsora.migracion_recetalia.model.error.TargetWriteException;
import com.previsora.migracion_recetalia.usecase.gateway.TargetWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("devRecetaFileWriter")
public class DevRecetaFileAdapter implements TargetWriter<RecetaFile> {

    private final DevRecetaFileRepository repository;
    private final DevRecetaFileMapper mapper;

    public DevRecetaFileAdapter(DevRecetaFileRepository repository, DevRecetaFileMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<Boolean> exists(RecetaFile record) {
        return repository.countById(record.id()).map(count -> count > 0);
    }

    @Override
    public Mono<Void> insert(RecetaFile record) {
        return repository.save(mapper.toEntity(record))
                .then()
                .onErrorMap(ex -> new TargetWriteException("Error writing to recetali_receta.file" + ": " + ex.getMessage(), ex));
    }
}
