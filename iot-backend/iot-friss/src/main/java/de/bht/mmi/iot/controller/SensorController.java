package de.bht.mmi.iot.controller;

import de.bht.mmi.iot.constants.RoleConstants;
import de.bht.mmi.iot.exception.EntityNotFoundException;
import de.bht.mmi.iot.exception.NotAuthorizedException;
import de.bht.mmi.iot.model.Sensor;
import de.bht.mmi.iot.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    // GET
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Sensor> getAllSensor(@RequestParam(value = "owner", required = false) String owner,
                                         @RequestParam(value = "types", required = false) String types,
                                         @AuthenticationPrincipal UserDetails authenticatedUser)
            throws EntityNotFoundException, NotAuthorizedException {
        if (owner != null) {
            return sensorService.getAllByOwner(owner, authenticatedUser);
        } else if(types != null) {
            return sensorService.getAllByTypes(types, authenticatedUser);
        } else {
            return sensorService.getAll(authenticatedUser);
            
        }
    }

    @PreAuthorize(RoleConstants.HAS_ROLE_ADMIN_OR_USER)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Sensor getSensor(@PathVariable("id") String id) throws EntityNotFoundException {
        return sensorService.getOne(id);
    }

    // POST
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Sensor createSensor(@RequestBody @Validated Sensor sensor,
                               @AuthenticationPrincipal UserDetails authenticatedUser)
            throws EntityNotFoundException, NotAuthorizedException {
                System.out.println("POST ");
        return sensorService.save(sensor, authenticatedUser);
    }

    // PUT
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Sensor updateSensor(@PathVariable("id") String id,
                               @RequestBody @Validated Sensor sensor,
                               @AuthenticationPrincipal UserDetails authenticatedUser)
            throws NotAuthorizedException, EntityNotFoundException {
                System.out.println("PUT ");
        sensor.setId(id);
        return sensorService.save(sensor, authenticatedUser);
    }

    // DELETE
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public void deleteSensor(@PathVariable("id") String id, @AuthenticationPrincipal  UserDetails authenticatedUser)
            throws EntityNotFoundException, NotAuthorizedException {
        sensorService.delete(id, authenticatedUser);
    }

}
