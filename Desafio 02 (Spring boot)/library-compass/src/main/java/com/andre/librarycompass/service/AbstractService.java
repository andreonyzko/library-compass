package com.andre.librarycompass.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class AbstractService<T>{

    private JpaRepository<T, Integer> repository;

    public AbstractService(JpaRepository<T, Integer> repository){
        this.repository = repository;
    }

    public List<T> findAll(){
        return repository.findAll();
    }

    public T findById(Integer id){
        Optional<T> obj = repository.findById(id);

        if(obj.isEmpty()) throw new RuntimeException("Objeto não encontrado");

        return obj.get();
    }

    public void save(T obj){
        repository.save(obj);
    }

    public void update(T obj){
        repository.save(obj);
    }

    public void delete(T obj){
        repository.delete(obj);
    }
}
