package de.bht.mmi.iot.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.ArrayList;

@DynamoDBTable(tableName = "Gateway")
public class Gateway {

    private String gatewayID;

    private String name;

    private ArrayList<String> sensorList;

    public Gateway() {
    }

    public Gateway(String name) {
        this.name = name;
    }

    @DynamoDBHashKey(attributeName = "gatewayID")
    @DynamoDBAutoGeneratedKey
    public String getGatewayID() {
        return gatewayID;
    }

    public void setGatewayID(String gatewayID) {
        this.gatewayID = gatewayID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSensorList() {
        return sensorList;
    }

    public void setSensorList(ArrayList<String> sensorList) {
        this.sensorList = sensorList;
    }
}