object Producer {
  def main(args: Array[String]): Unit = {

    // Import libraries
    import java.util.Properties
    import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
    import org.apache.kafka.common.serialization.StringSerializer

    // Function to convert map to json string
    def toJson(query: Any): String = query match {
      case m: Map[String, Any] => s"{${m.map(toJson(_)).mkString(",")}}"
      case t: (String, Any) => s""""${t._1}":${toJson(t._2)}"""
      case ss: Seq[Any] => s"""[${ss.map(toJson(_)).mkString(",")}]"""
      case s: String => s""""$s""""
      case null => "null"
      case _ => query.toString
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

    // Open csv file
    val src = io.Source.fromFile("MOCK_DATA.csv")
    // Get lines from csv
    val lines = src.getLines
    // Get first line (header) and convert to array
    val header = lines.take(1).next.split(",")

    // Iterate through the rest of the lines (up to 10000)
    for (row <- lines.take(10000)) {
      // Convert line to array
      val rowArr = row.split(",")
      // Create map from header and line
      val rowMap = header.zip(rowArr).toMap
      // Send row map via the producer
      producer.send(new ProducerRecord[String, String]("myTopic",rowMap("ip_address"),toJson(rowMap)))
      println(s"Sent row of ${rowMap("ip_address")} to myTopic")
      // Wait before sending next row
      Thread.sleep(2000)
    }

    // Close the producer and file once done
    producer.close()
    src.close()
  }
}