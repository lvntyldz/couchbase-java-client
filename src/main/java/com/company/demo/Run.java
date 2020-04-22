package com.company.demo;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.MutationResult;

public class Run {

    private static final String CB_CONNECTION_STRING = "localhost";
    private static final String CB_USERNAME = "Administrator";
    private static final String CB_PASSWORD = "a12345";

    public static void main(String[] args) {
        Cluster cluster = Cluster.connect(CB_CONNECTION_STRING, CB_USERNAME, CB_PASSWORD);
        Bucket bucket = cluster.bucket("example");
        Collection collection = bucket.defaultCollection();

        JsonArray jobInfo = JsonArray.create();
        jobInfo.add(JsonObject.create().put("title", "Junior Software Engineer").put("experience", 2).put("skils", "Java,Oracle"));
        jobInfo.add(JsonObject.create().put("title", "Senior Software Engineer").put("experience", 6).put("skils", "mongoDb,hadoop"));

        JsonObject person1 = JsonObject.create()
                .put("firstname", "Ali")
                .put("lastname", "ALİOĞLU")
                .put("age", 30)
                .put("job", jobInfo);

        JsonObject person2 = JsonObject.create()
                .put("firstname", "Veli")
                .put("lastname", "VELİOĞLU")
                .put("age", 20)
                .put("job", jobInfo);

        MutationResult person1Result = collection.upsert("person1", person1);
        System.out.println("Person1 Result : "+person1Result);
        MutationResult person2Result = collection.upsert("person2", person2);
        System.out.println("Person2 Result : "+person2Result);
    }

}
