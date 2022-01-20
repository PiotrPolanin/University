package com.university.generics;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface GenericRepository <T extends UpdateEntity<T>> extends PagingAndSortingRepository<T, Long> {
}
