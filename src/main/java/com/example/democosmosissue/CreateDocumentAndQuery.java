// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.example.democosmosissue;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.models.CosmosItemRequestOptions;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.PartitionKey;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.example.democosmosissue.common.Family;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class CreateDocumentAndQuery {

    private static final Logger logger = LoggerFactory.getLogger(CreateDocumentAndQuery.class);

    private static final String ENDPOINT = "<your-cosmos-endpoint>";
    private static final String KEY = "<your-cosmos-access-key>";

    public static void main(String[] args) {
        final String databaseName = "products";
        final String containerName = "users";
        final String documentId = UUID.randomUUID().toString();
        final String documentLastName = "Peterson";

        CosmosClientBuilder builder = new CosmosClientBuilder();
        builder.endpoint(ENDPOINT);
        builder.key(KEY);
        CosmosClient cosmosClient = builder.buildClient();
        logger.info("Get database " + databaseName + " .........");
        //  Get database
        CosmosDatabase database = cosmosClient.getDatabase(databaseName);
        logger.info("Get container " + containerName + " .........");
        //  Get container
        CosmosContainer container = database.getContainer(containerName);
        // Define a document as a POJO (internally this
        // is converted to JSON via custom serialization)
        Family family = new Family();
        family.setLastName(documentLastName);
        family.setId(documentId);

        // Insert this item as a document
        // Explicitly specifying the /pk value improves performance.
        container.createItem(family,new PartitionKey(family.getId()),new CosmosItemRequestOptions());

        CosmosPagedIterable<Family> filteredFamilies = container.queryItems("SELECT * FROM c", new CosmosQueryRequestOptions(), Family.class);

        // Print
        if (filteredFamilies.iterator().hasNext()) {
            Family familyTmp = filteredFamilies.iterator().next();
            logger.info(String.format("First query result: Family with (/id, partition key) = (%s,%s)",
                    familyTmp.getId(),
                    familyTmp.getLastName()));
        }
    }

}
