package de.bht.mmi.iot.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import de.bht.mmi.iot.constants.DbConstants;
import de.bht.mmi.iot.converter.JodaDateTimeMarshaller;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

@DynamoDBTable(tableName = DbConstants.TABLENAME_CLUSTER)
public class Cluster {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String owner;

    @NotNull
    private DateTime creationDate;

    public Cluster() {
        this.creationDate = new DateTime();
    }

    public Cluster(String name, String owner) {
        this.name = name;
        this.owner = owner;
    }

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

    @DynamoDBMarshalling(marshallerClass = JodaDateTimeMarshaller.class)
    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
