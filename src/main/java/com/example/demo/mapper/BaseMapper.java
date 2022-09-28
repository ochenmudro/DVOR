package com.example.demo.mapper;

public interface BaseMapper<T, V> {
    V mapToDTO(T entity);

    T mapToEntity(V dto);
}
