package com.rest.pojo.workspace;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/*
*   @JsonInclude(JsonInclude.Include.NON_NULL) annotation will help ignore null
  fields(here its id, as we don't pass any id while making a request)
  when serializing a java class.
  *
  *  @JsonInclude(JsonInclude.Include.NON_NULL) annotation will help ignore default
     values (here int will have a default of 0) when serializing a java class.
     *
     * Also look at
     * 1) @@JsonIgnore
     * 2)@JsonIgnoreProperties
     *@JsonIgnoreProperties(value = "id", allowSetters= true)
     * Above will allow id to be used while deserialization
     * @JsonIgnoreProperties(value = "id", allowGetters= true)
     *Above will allow id to be used while serialization
     *
* */

public class Workspace {
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int i;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String id;
    private String name;
    private String type;
    private String description;

    public Workspace() {
    }
    // id we will get only in response, we will not send any idea while we make the request
    public Workspace(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
