package com.previsora.migracion_recetalia.utility;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.BiFunction;

/**
 * Reactive utility helpers for paginated reads.
 */
public final class ReactiveUtils {

    private ReactiveUtils() {
        // utility class
    }

    /**
     * Paginate sequentially through a data source using OFFSET/LIMIT.
     * <p>
     * Fetches one page at a time. Moves to the next page only after the current one is
     * fully emitted. Stops when a page returns fewer elements than the batch size.
     *
     * @param batchSize  number of records per page
     * @param pageFetcher function that takes (offset, limit) and returns a Mono of a list
     * @param <T>        element type
     * @return a Flux that emits all records across all pages
     */
    public static <T> Flux<T> paginateSequential(int batchSize,
                                                   BiFunction<Integer, Integer, Mono<List<T>>> pageFetcher) {
        return fetchPage(0, batchSize, pageFetcher);
    }

    private static <T> Flux<T> fetchPage(int offset, int limit,
                                          BiFunction<Integer, Integer, Mono<List<T>>> pageFetcher) {
        return pageFetcher.apply(offset, limit)
                .flatMapMany(page -> {
                    Flux<T> current = Flux.fromIterable(page);
                    if (page.size() < limit) {
                        // Last page — no more data
                        return current;
                    }
                    // Recurse to the next page
                    return current.concatWith(fetchPage(offset + limit, limit, pageFetcher));
                });
    }
}
