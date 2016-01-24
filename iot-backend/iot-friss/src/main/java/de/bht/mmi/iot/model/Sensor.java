package de.bht.mmi.iot.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import de.bht.mmi.iot.constants.DbConstants;
import de.bht.mmi.iot.converter.JodaDateTimeMarshaller;
import org.joda.time.DateTime;

import java.util.List;

@DynamoDBTable(tableName = DbConstants.TABLENAME_SENSOR)
public class Sensor {

    private String id;

    private String name;

    private List<String> sensorTypes;

    private String location;

    private List<String> attachedClusters;

    private String attachedGateway;

    private String owner;

    private DateTime creationDate;

    private boolean isActive;

    public Sensor(List<String> sensorTypes, String location, String attachedGateway,
                  List<String> attachedClusters, String owner, DateTime creationDate, boolean isActive) {
        this.sensorTypes = sensorTypes;
        this.location = location;
        this.attachedGateway = attachedGateway;
        this.attachedClusters = attachedClusters;
        this.owner = owner;
        this.creationDate = creationDate;
        this.isActive = isActive;
    }

    public Sensor() { }

    @DynamoDBHashKey(attributeName = DbConstants.ATTRIBUTE_ID)
    @DynamoDBAutoGeneratedKey
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @DynamoDBMarshalling(marshallerClass = JodaDateTimeMarshaller.class)
    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getAttachedClusters() {
        return attachedClusters;
    }

    public void setAttachedClusters(List<String> attachedClusters) {
        this.attachedClusters = attachedClusters;
    }

    public String getAttachedGateway() {
        return attachedGateway;
    }

    public void setAttachedGateway(String attachedGateway) {
        this.attachedGateway = attachedGateway;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getSensorTypes() {
        return sensorTypes;
    }

    public void setSensorTypes(List<String> sensorTypes) {
        this.sensorTypes = sensorTypes;
    }
}