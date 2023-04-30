object Producer {
  def main(args: Array[String]): Unit = {

    // Import libraries
    import java.util.Properties
    import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
    import org.apache.kafka.common.serialization.StringSerializer
    import java.time.LocalDateTime
    import java.time.format.DateTimeFormatter
    import scala.util.Random

    // Function to generate random transaction data
    def generateTransaction(): String = {
      val transaction_id = Random.nextInt(1000000)
      val account_id = Random.nextInt(200) + 1
      val branch_id = Random.nextInt(10) + 1
      val channel_id = Random.nextInt(4) + 1
      val timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
      val transaction_amount = BigDecimal(Random.nextDouble() * 1000).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble

      s"""{
        "transaction_id": $transaction_id,
        "account_id": $account_id,
        "branch_id": $branch_id,
        "channel_id": $channel_id,
        "transaction_timestamp": "$timestamp",
        "transaction_amount": $transaction_amount
      }"""
    }

    // Initialize producer properties
    val kafkaProducerProps: Properties = {
      val props = new Properties()
      props.put("bootstrap.servers", "localhost:9092")
      props.put("key.serializer", classOf[StringSerializer].getName)
      props.put("value.serializer", classOf[StringSerializer].getName)
      props
    }
    // Initialize producer
    val producer = new KafkaProducer[String, String](kafkaProducerProps)

    // Iterate through 10000 transactions
    for (_ <- 1 to 10000) {
      // Generate a random transaction
      val transaction = generateTransaction()
      // Send the transaction to Kafka
      producer.send(new ProducerRecord[String, String]("transactions", transaction))
      println(s"Sent transaction: $transaction")
      // Wait before sending the next transaction
      Thread.sleep(1000)
    }

    // Close the producer once done
    producer.close()
  }
}