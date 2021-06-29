package com.dylandewit.skeleton.api.base;

import com.dylandewit.skeleton.api.base.dto.BaseCreateDto;
import com.dylandewit.skeleton.api.base.dto.BaseViewDto;
import com.dylandewit.skeleton.api.base.models.BaseModel;
import com.dylandewit.skeleton.exception.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public abstract class BaseService<T extends BaseModel, VIEW_DTO extends BaseViewDto<T>, CREATE_DTO extends BaseCreateDto<T>> {
    protected final BaseRepository<T> repository;

    protected BaseService(BaseRepository<T> repository) {
        this.repository = repository;
    }

    public Page<VIEW_DTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToDto);
    }

    public VIEW_DTO findById(Long id) {
        return mapToDto(find(id));
    }

    public Page<VIEW_DTO> findByIds(List<Long> ids, Pageable pageable) {
        return repository.findAllByIdIn(ids, pageable).map(this::mapToDto);
    }

    public Set<T> find(List<Long> ids) {
        return repository.findAllByIdIn(ids);
    }

    public T find(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public VIEW_DTO update(Long id, CREATE_DTO dto) {
        T t = mapForUpdate(find(id), dto);

        return mapToDto(repository.save(t));
    }

    public VIEW_DTO create(CREATE_DTO dto) {
        return mapToDto(repository.save(mapForCreate(dto)));
    }

    public void delete(Long id) {
        repository.delete(find(id));
    }

    protected T mapForCreate(CREATE_DTO dto) {
        return dto.toModel();
    }

    protected abstract VIEW_DTO mapToDto(T t);

    protected abstract T mapForUpdate(T t, CREATE_DTO dto);
}
