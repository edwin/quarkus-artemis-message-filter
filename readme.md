# Quarkus and Red Hat AMQ Broker Message Filtering

## Purpose
Connecting Quarkus to a message broker with two different listener doing messages filtering. 

## Concept
Create 2 different listener with each listen to a specific message property (key). Each key is filled with a random number between 1 and 6, and each listener listen to the same key with specific filtering. 

Listener one filter to `key <= 3`, while listener two filter to `key > 3` 

## Sample Logs
```
__  ____  __  _____   ___  __ ____  ______ 
 --/ __ \/ / / / _ | / _ \/ //_/ / / / __/ 
 -/ /_/ / /_/ / __ |/ , _/ ,< / /_/ /\ \   
--\___\_\____/_/ |_/_/|_/_/|_|\____/___/   
2024-07-08 20:44:48,768 INFO  [io.quarkus] (Quarkus Main Thread) quarkus-artemis-message-filter 1.0-SNAPSHOT on JVM (powered by Quarkus 3.8.4.redhat-00002) started in 1.364s. 
2024-07-08 20:44:48,770 INFO  [io.quarkus] (Quarkus Main Thread) Profile dev activated. Live Coding activated.
2024-07-08 20:44:48,771 INFO  [io.quarkus] (Quarkus Main Thread) Installed features: [artemis-jms, cdi]
2024-07-08 20:44:49,029 INFO  [com.edw.ser.MessageProducer] (pool-5-thread-1) sending message >>> 3---c055bb8b-ed0a-401c-9a6c-bcef03cfbb2d
2024-07-08 20:44:49,030 INFO  [com.edw.ser.MessageConsumer] (Thread-41) ---- msg from listener with key less or equals to 3 ----  3---c055bb8b-ed0a-401c-9a6c-bcef03cfbb2d
2024-07-08 20:44:54,065 INFO  [com.edw.ser.MessageProducer] (pool-5-thread-1) sending message >>> 2---7e768740-a567-4f93-8014-e98a5a1d21c5
2024-07-08 20:44:54,065 INFO  [com.edw.ser.MessageConsumer] (Thread-41) ---- msg from listener with key less or equals to 3 ----  2---7e768740-a567-4f93-8014-e98a5a1d21c5
2024-07-08 20:44:59,086 INFO  [com.edw.ser.MessageProducer] (pool-5-thread-1) sending message >>> 2---92d137e7-bc41-45aa-b930-b599c7227cc7
2024-07-08 20:44:59,086 INFO  [com.edw.ser.MessageConsumer] (Thread-41) ---- msg from listener with key less or equals to 3 ----  2---92d137e7-bc41-45aa-b930-b599c7227cc7
2024-07-08 20:45:04,103 INFO  [com.edw.ser.MessageProducer] (pool-5-thread-1) sending message >>> 3---1ad6eaee-035c-4413-a256-a941d1f82beb
2024-07-08 20:45:04,103 INFO  [com.edw.ser.MessageConsumer] (Thread-41) ---- msg from listener with key less or equals to 3 ----  3---1ad6eaee-035c-4413-a256-a941d1f82beb
2024-07-08 20:45:09,117 INFO  [com.edw.ser.MessageProducer] (pool-5-thread-1) sending message >>> 4---335f5a19-471d-4fb8-b2c6-bf7a1c561b41
2024-07-08 20:45:09,117 INFO  [com.edw.ser.MessageConsumer] (Thread-42) ++++ msg from listener with key more than 3 ++++  4---335f5a19-471d-4fb8-b2c6-bf7a1c561b41
2024-07-08 20:45:14,142 INFO  [com.edw.ser.MessageProducer] (pool-5-thread-1) sending message >>> 1---6565dcfb-30a3-4be8-a7de-560613e22ae1
2024-07-08 20:45:14,142 INFO  [com.edw.ser.MessageConsumer] (Thread-41) ---- msg from listener with key less or equals to 3 ----  1---6565dcfb-30a3-4be8-a7de-560613e22ae1
2024-07-08 20:45:19,163 INFO  [com.edw.ser.MessageProducer] (pool-5-thread-1) sending message >>> 4---9393def5-b0e3-4990-b982-f0c68144692b
2024-07-08 20:45:19,163 INFO  [com.edw.ser.MessageConsumer] (Thread-42) ++++ msg from listener with key more than 3 ++++  4---9393def5-b0e3-4990-b982-f0c68144692b
2024-07-08 20:45:24,180 INFO  [com.edw.ser.MessageProducer] (pool-5-thread-1) sending message >>> 1---a0709abc-93eb-497a-8443-69897dfd3540
2024-07-08 20:45:24,180 INFO  [com.edw.ser.MessageConsumer] (Thread-41) ---- msg from listener with key less or equals to 3 ----  1---a0709abc-93eb-497a-8443-69897dfd3540
```