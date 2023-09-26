package com.pedroresende.rateiofacil.services;

import java.util.List;

/**
 * Interface para implementação de uma camada de serviço.
 *
 * @param <T> Entidade que manipulada do banco de dados.
 */
public interface BasicService<T> {

  public T create(T entity);

  public T getById(Long id);

  public List<T> getAll();

  public T update(Long id, T entity);

  public T delete(Long id);
}
