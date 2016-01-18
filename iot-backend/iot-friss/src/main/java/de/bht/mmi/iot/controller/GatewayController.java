package de.bht.mmi.iot.controller;

import de.bht.mmi.iot.service.GatewayService;
import de.bht.mmi.iot.service.SensorService;
import de.bht.mmi.iot.service.TableCreatorService;
import de.bht.mmi.iot.model.rest.Gateway;
import de.bht.mmi.iot.model.rest.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gateway")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @Autowired
    private SensorService sensorService;

    @Autowired
    private TableCreatorService tableCreator;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Gateway createGateway(@RequestBody Gateway gateway) {
        return gatewayService.createGateway(gateway);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Gateway> getAllGateways() {
        return gatewayService.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Gateway getGateway(@PathVariable("id") String id) {
        return gatewayService.getGateway(id);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public Gateway updateGateway(@PathVariable("id") String id,
                                 @RequestBody Gateway gateway,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        return gatewayService.updateGateway(id, gateway, userDetails);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteGateway(@PathVariable("id") String id) { gatewayService.deleteGateway(id);
    }

    @RequestMapping(value = "/{id}/sensor", method = RequestMethod.GET)
    public Iterable<Sensor> getAllAttachedSensors(@PathVariable("id") String id) {
        return sensorService.getAllSensorsByGatewayId(id);
    }

}
