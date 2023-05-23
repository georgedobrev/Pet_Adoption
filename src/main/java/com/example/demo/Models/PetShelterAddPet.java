package com.example.demo.Models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class PetShelterAddPet implements PetShelterRepo {
    @Autowired
    PetShelterRepo petShelterRepo;

    @Override
    public void flush() {

    }

    @Override
    public <S extends Animals> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Animals> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Animals> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Integer> integers) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Animals getOne(Integer integer) {
        return null;
    }

    @Override
    public Animals getById(Integer integer) {
        return null;
    }

    @Override
    public Animals getReferenceById(Integer integer) {
        return null;
    }

    @Override
    public <S extends Animals> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Animals> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Animals> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Animals> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Animals> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Animals> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Animals, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Animals> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Animals> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Animals> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Animals> findAll() {
        return null;
    }

    @Override
    public List<Animals> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Animals entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Animals> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Animals> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Animals> findAll(Pageable pageable) {
        return null;
    }
}

