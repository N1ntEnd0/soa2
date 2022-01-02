package com.example.spring.repository;

import com.example.spring.beans.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CityRepository extends PagingAndSortingRepository<City, Long> {
    ArrayList<City> findAll();
    List<City> getCityById(Long id);
    Optional<City> findCityById(Long id);
    void deleteCityById(Long id);
    List<City> findFirstByOrderByAreaDesc();
    List<City> findFirstByOrderByAreaAsc();
    List<City> findFirstByOrderByPopulationDesc();
    @Transactional
    Integer deleteCitiesByCapital(Boolean capital);
    List<City> getCitiesByCapital(Boolean capital);

    @Query(nativeQuery = true, value = "SELECT c.name FROM City c GROUP BY c.name")
    List<String> getCitiesGroup();

    @Query(nativeQuery = true, value = "SELECT COUNT(c.name) FROM City c GROUP BY c.name")
    List<Long> getCountCitiesGroup();
}
