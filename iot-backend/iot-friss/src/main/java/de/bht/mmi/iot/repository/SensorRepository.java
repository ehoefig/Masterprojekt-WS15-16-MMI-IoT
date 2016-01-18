package de.bht.mmi.iot.repository;

import de.bht.mmi.iot.model.rest.Sensor;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface SensorRepository extends CrudRepository<Sensor, String> {


}
