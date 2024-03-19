package com.app.manage_ressource.services;

import com.app.manage_ressource.entities.Reponse;

import java.util.UUID;

public interface CrudService<T> {
    public Reponse create(T obj);
    public Reponse update(T obj);
    public Reponse  getAll();
    public Reponse  getById(UUID ID);



}
