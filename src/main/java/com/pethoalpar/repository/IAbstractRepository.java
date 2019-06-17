package com.pethoalpar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.pethoalpar.entity.AbstractEntity;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
@NoRepositoryBean
public interface IAbstractRepository<T extends AbstractEntity> extends JpaRepository<T, Integer> {

}
