package com.eks.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface EnityRepositoryUtils<T,ID extends Serializable> extends JpaRepository<T,ID>, JpaSpecificationExecutor<T>{
    T findOneByIdAndIsDeleted(ID id,Integer isDeleted);
}