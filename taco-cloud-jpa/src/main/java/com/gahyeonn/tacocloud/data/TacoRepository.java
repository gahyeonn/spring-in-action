package com.gahyeonn.tacocloud.data;

import com.gahyeonn.tacocloud.Taco;
import org.springframework.data.repository.CrudRepository;

public interface TacoRepository extends CrudRepository<Taco, Long> {
}
