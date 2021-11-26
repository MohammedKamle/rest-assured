package com.rest.pojo.collection;

public class CollectionRoot {
    private Collection collection;

    public CollectionRoot(Collection collection) {
        this.collection = collection;
    }

    public CollectionRoot() {
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
}
