package org.sdb.nosql.db.compensation;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

import org.jboss.narayana.compensations.api.ConfirmationHandler;

import javax.inject.Inject;

/**
 * This compensation handler is used to undo a credit operation.
 *
 * @author paul.robinson@redhat.com 09/01/2014
 */
public class ConfirmInsert implements ConfirmationHandler {

    @Inject
    InsertCounterData insertCounterData;

    @Override
    public void confirm() {

        MongoClient mongoClient = CounterManager.getMongoClient();
        DB database = mongoClient.getDB("test");
        DBCollection accounts = database.getCollection("counters");

        try{
        	accounts.update(new BasicDBObject("name", String.valueOf(insertCounterData.getCounter())), new BasicDBObject("$inc", new BasicDBObject("tx", 1)));
		} catch (MongoException e){

		}
	}
}
