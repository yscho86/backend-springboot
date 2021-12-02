package cc.grum.base.backendspringboot.core.db.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BaseMapper<T> {
    int insert(T entity) throws Exception;
    int insert(String queryId, T entity) throws Exception;
    int update(T entity) throws Exception;
    int update(String queryId, T entity) throws Exception;
    int delete(T entity) throws Exception;
    int delete(String queryId, T entity) throws Exception;
    Optional<T> findOne(String queryId, Object conditions) throws Exception;
    Optional<T> findById(Long id) throws Exception;
    Optional<List<T>> findList(String queryId, Object conditions) throws Exception;
    long total();
}
