package com.company.demo;

import com.couchbase.client.core.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;

import java.util.Iterator;
import java.util.Map;

public class Run {

    private static final String CB_CONNECTION_STRING = "localhost";
    private static final String CB_USERNAME = "Administrator";
    private static final String CB_PASSWORD = "a12345";

    public static void main(String[] args) {
        Cluster cluster = Cluster.connect(CB_CONNECTION_STRING, CB_USERNAME, CB_PASSWORD);
        Bucket bucket = cluster.bucket("example");
        Collection collection = bucket.defaultCollection();

        Job job = new Job("Junior Software Engineer",1,new String[]{"Java","Oracle"});
        Employee employee1 = new Employee("Ali","ALİOĞLU",20,job);
        MutationResult employe1Res = collection.upsert("employe1", employee1);
        System.out.println("Employee1 Result : "+employe1Res);

        Job job2 = new Job("Senior Software Engineer",10,new String[]{"Oracle","MongoDB","C++","Java"});
        Employee employee2 = new Employee("Veli","VELİOĞLU",30,job2);
        MutationResult employe2Res = collection.upsert("employe2", employee2);
        System.out.println("Employee2 Result : "+employe2Res);

        System.out.println("\nGetting employe1 from couchbase...");
        GetResult result = collection.get("employe1");
        Employee employeeRes = result.contentAs(Employee.class);
        System.out.println("Employee1 : "+employeeRes);

        System.out.println("\nUpdating employe1...");
        employeeRes.setLastname("HASANOĞLU");
        employeeRes.setFirstname("Hasan");
        collection.upsert("employe1",employeeRes);

        GetResult updatedResult = collection.get("employe1");
        Employee updatedEmployeeRes = updatedResult.contentAs(Employee.class);
        System.out.println("Employee1 : "+updatedEmployeeRes);


        System.out.println("\nDeleting employe2...");
        collection.remove("employe2");

        try{
            GetResult deletedResult = collection.get("employe2");
            Employee deletedEmployeeRes = deletedResult.contentAs(Employee.class);
            System.out.println("Employe2 : "+deletedEmployeeRes);
        }catch (DocumentNotFoundException e){
            System.out.println("Employee2 deleted.");
        }


    }

}
