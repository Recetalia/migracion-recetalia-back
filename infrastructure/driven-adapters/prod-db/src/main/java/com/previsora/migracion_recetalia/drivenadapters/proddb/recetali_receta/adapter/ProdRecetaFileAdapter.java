package com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.adapter;

import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.mapper.ProdRecetaFileMapper;
import com.previsora.migracion_recetalia.drivenadapters.proddb.recetali_receta.repository.ProdRecetaFileRepository;
import com.previsora.migracion_recetalia.model.domain.receta.RecetaFile;
import com.previsora.migracion_recetalia.model.error.SourceReadException;
import com.previsora.migracion_recetalia.usecase.gateway.SourceReader;
import com.previsora.migracion_recetalia.utility.ReactiveUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component("prodRecetaFileReader")
public class ProdRecetaFileAdapter implements SourceReader<RecetaFile> {

    private final ProdRecetaFileRepository repository;
    private final ProdRecetaFileMapper mapper;

    public ProdRecetaFileAdapter(ProdRecetaFileRepository repository, ProdRecetaFileMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Flux<RecetaFile> readAll(int batchSize) {
        return ReactiveUtils.paginateSequential(batchSize,
                (offset, limit) -> repository.findAllPaginated(offset, limit)
                        .map(mapper::toDomain)
                        .collectList()
        ).onErrorMap(ex -> new SourceReadException("Error reading from recetali_receta.file", ex));
    }
}
