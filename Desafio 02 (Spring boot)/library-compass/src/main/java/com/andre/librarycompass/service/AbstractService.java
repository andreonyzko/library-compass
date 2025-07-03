package com.andre.librarycompass.service;

import com.andre.librarycompass.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractService<T>{

    private final JpaRepository<T, Long> repository;

    public AbstractService(JpaRepository<T, Long> repository){
        this.repository = repository;
    }

    public List<T> findAll(){
        return repository.findAll();
    }

    // May not found object
    public T findById(Long id){
        return repository.findById(id)
                .orElseThrow( () -> new NotFoundException(getEntityName() + " não encontrado") );
    }

    // Must check if client has fill the required fields and set id as null
    public T save(T obj){
        return repository.save(obj);
    }

    public void delete(T obj){
        repository.delete(obj);
    }

    public void deleteById(Long id){
        delete(findById(id));
    }

    public abstract String getEntityName();
}
