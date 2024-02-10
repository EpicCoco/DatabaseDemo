package com.example;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

/**
 * Hello world!
 *
 */
public class App {

    private static final String connectionString = "mongodb+srv://codeyb:codeyb@cluster0.psukrp1.mongodb.net/?retryWrites=true&w=majority";
    private static final String defaultDatabaseName = "Cluster0";
    public static void main(String[] args) {
        
        ServerApi serverApi = ServerApi.builder().version(ServerApiVersion.V1).build();
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();
        // Create a new client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase database = mongoClient.getDatabase(defaultDatabaseName);
                
                //create collection "hackers"
                //database.createCollection("hackers");
                System.out.println("Collections: ");
                database.listCollectionNames().forEach(System.out::println);

                
                //insert into database
                MongoCollection<Document> hackerCollection = database.getCollection("hackers");
                /*
                Document document = new Document();
                document.put("fName", "Kevin");
                document.put("school", "KSU");
                hackerCollection.insertOne(document);
                */
                Document searchQuery = new Document();
                searchQuery.put("school", "KSU");
                FindIterable<Document> cursor = hackerCollection.find(searchQuery);
                System.out.println("All hackers in database: ");
                try (final MongoCursor<Document> cursorIterator = cursor.cursor()) {
                    while (cursorIterator.hasNext()) {
                        Document doc = cursorIterator.next();
                        System.out.println(doc.get("fName") + " " + doc.get("school"));
                    } //while
                } //try


            } catch (MongoException e) {
                e.printStackTrace();
            } //try
        } //try
    } //main
} //App
