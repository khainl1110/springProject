1. cd into /usr/bin
To create a new topic, use this command kafka-topics --create --topic topic1 --bootstrap-server localhost:9092
kafka-topics --create --topic topic2 --partitions 3 --replication-factor 3 --bootstrap-server localhost:9092
- Replication factor cannot be greater than number of partitions
- Consumer must less than or equal to partitions