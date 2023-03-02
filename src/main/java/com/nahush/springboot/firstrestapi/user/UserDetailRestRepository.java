package com.nahush.springboot.firstrestapi.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserDetailRestRepository extends PagingAndSortingRepository<UserDetail, Long> {
}
