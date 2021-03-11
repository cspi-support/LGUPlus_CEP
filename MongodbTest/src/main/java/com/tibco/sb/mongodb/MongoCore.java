package com.tibco.sb.mongodb;

import com.mongodb.Block;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;

import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSONParseException;
import com.streambase.sb.DataType;
import com.streambase.sb.Schema;
import com.streambase.sb.StreamBaseRuntimeException;

public class MongoCore {
	private static Schema schema;

	static {
		schema = new Schema("MongoAdapter", Schema.createField(DataType.STRING, "ID"),
				Schema.createField(DataType.STRING, "Collection"), Schema.createField(DataType.STRING, "Data"),
				Schema.createField(DataType.BOOL, "Error"), Schema.createField(DataType.STRING, "ErrorMessage"));
	}

	private MongoDatabase db;

	public MongoCore(MongoClient client, String DB) {
		this.db = client.getDatabase(DB);
	}

	public MongoCore(MongoClient client, String Collection, String DB) {
		this.db = client.getDatabase(DB);
	}

	/**
	 * @return the schema
	 */
	public static Schema getSchema() {
		return schema;
	}

	/**
	 * @param schema the schema to set
	 */
	public static void setSchema(Schema schema) {
		MongoCore.schema = schema;
	}

	Block<Document> printBlock = new Block<Document>() {
		@Override
		public void apply(final Document document) {
			System.out.println(document.toJson());
		}
	};

	public FindIterable<Document> getData(String collection, String filter){
		Document bFilter = null;
		if (filter != null) {
			bFilter = Document.parse(filter);
		} else {
			bFilter = Document.parse("{}");
		}
		
		FindIterable<Document> ret = db.getCollection(collection).find(bFilter);
		
		return ret;
	}

	public void insertData(String collection, String data) {
		final Document payload = Document.parse(data);
		db.getCollection(collection).insertOne(payload);
	}

	public Document bulkinsert(String collection, String[] data) {
			final List<InsertOneModel<Document>> payload = Arrays.stream(data)
				.map(e -> new InsertOneModel<>(Document.parse(e))).collect(Collectors.toList());
		BulkWriteResult ret = db.getCollection(collection).bulkWrite(payload);
		
		Document doc = null;
		if (ret != null) {
			doc = new Document();
			doc.append("nInserted", ret.getInsertedCount());
			doc.append("wasAcknowledged", ret.wasAcknowledged());
		}
		return doc;
	}

	public Document updateData(String collection, String filter, boolean upsert, String data) {
		Document ret = null;
		try {
			final Document payload = Document.parse(data);
			final Document selector = Document.parse(filter);
			
			// final Document set = new Document();
			// set.append("$set", payload);
			UpdateOptions upd = new UpdateOptions();
			upd.upsert(upsert);
			
			UpdateResult result = db.getCollection(collection).updateMany(selector, payload, upd);
			if (result != null) {
				ret = new Document();
				ret.append("nModified", (int) result.getModifiedCount());
				ret.append("nMatched", (int) result.getMatchedCount());
				ret.append("acknowledged", result.wasAcknowledged());
//				ret.append("error", t != null);
//				ret.append("errorMessage", t == null ? "" : t.getMessage());
			}
		} catch (JSONParseException e) {
			throw new StreamBaseRuntimeException(e.getMessage());
		}
		return ret;
	}

	public Document findOneAndUpdate(String collection, String filter, String data) {
		Document ret = null;
		try {
			final Document payload = Document.parse(data);
			final Document selector = Document.parse(filter);
			ret = db.getCollection(collection).findOneAndUpdate(selector, payload);
		} catch (JSONParseException e) {
			throw new StreamBaseRuntimeException(e.getMessage());
		}
		
		return ret;
	}

	public Document deleteData(String Collection, String filter) {
		Document ret = null;
		try {
			Document _filter = Document.parse(filter);
			DeleteResult result = db.getCollection(Collection).deleteMany(_filter);
			ret = new Document();
			if (result != null) {
				ret.append("nDeleted", (int) result.getDeletedCount());
				ret.append("acknowledged", result.wasAcknowledged());
			}
		} catch (JSONParseException e) {
			throw new StreamBaseRuntimeException(e.getMessage());
		}
		
		return ret;
	}

}
