package ph.gov.laoagcity.alertreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.MongoException
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.MongoClient
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

//import com.mongodb.client.MongoClients
//import com.mongodb.kotlin.client.MongoDatabase

data class Test(
    @BsonId val id: ObjectId,
    val field1: String,
    val field2: String,
    val field3: String,
    val field4: String
)

class SmsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val bundle = intent.extras
            if (bundle != null) {
                val pdus = bundle.get("pdus") as Array<Any>?
                if (pdus != null) {
                    for (pdu in pdus) {
                        val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                        val messageBody = smsMessage.messageBody
                        val sender = smsMessage.originatingAddress
                        Log.d("SmsReceiver", "Received SMS from $sender: $messageBody")
                        // Handle the SMS message, e.g., show a notification or process it further
                        // TODO: write to mongodb (eihcek)
                        /*
                                                val connectionString = "mongodb:172.16.0.36"
                                                val databaseName = "myDatabase"
                                                val collectionName = "myCollection"
                                                val mongoClient = MongoClients.create(connectionString)
                                                val database: com.mongodb.client.MongoDatabase = mongoClient.getDatabase(databaseName)
                        */
                        val uri = "<connection string>"
// Set the Stable API version on the client
                        val serverApi = ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build()
                        val settings = MongoClientSettings.builder()
                            .applyConnectionString(ConnectionString(uri))
                            .serverApi(serverApi)
                            .build()
// Create a new client and connect to the server
                        val mongoClient = MongoClient.create(settings)
                        val database = mongoClient.getDatabase("test")
                        val collection = database.getCollection<Test>("test")
                        try {
                            val result = collection.insertOne(
                                Test(
                                    ObjectId(),
                                    "field1 test",
                                    "field2 test",
                                    "field3 test",
                                    "field4 test"
                                )
                            )
                            println("Success! Inserted document id: " + result.insertedId)
                        } catch (e: MongoException){
                            System.err.println("Unable to insert due to an error: $e")
                            Log.d("MongoDB Error","Unable to insert due to an error: $e")
                        }
                        mongoClient.close()
                    }
                }
            }
        }
    }
}
