NOTES TO APPLICATION REVIEWERS:
* There were a couple of technologies that I opted not to use because of the time constraint.
* I created a REST API rather than a graphQL server
    * It has been a couple of years since I have used GraphQL
    * With the small amount of time allotted... I chose not to implement GraphQL
    * The configuration alone for GraphQL could have taken quite some time.
      * If I had a previous Java/Springboot & GraphQL implementation, this configuration time could maybe be cut down.
    * Even once GraphQL was configured... I would have to re-teach myself GraphQL which would take some time
* I chose to use MySql rather than postgresql
  * I have not installed and configured postgresql on a local machine ever before.
    * Nor have I used postgresql with Java...
    * I was again concerned with how long installing and configuring a new technology like postgres would take.

End Pre-Read Notes
_____
_____

# Bored Ape Transfer Events Service
* This app is used to run a server which listens to all transfer events for the Bored Ape NFT platform
* The events will be added to a SQL DB to be retrieved

## Basic functionalities
The server has the following functions:
* Query events by ethereum address, tokenId
* Mark events as “read” in the database
* Return all “unread” events

The API for interacting with this server is listed below.

## Technologies Used
* Java
* Springboot Framework
* MySQL
* Web3J
* Maven

## Prerequisites
* [Download/Install Maven](https://maven.apache.org/download.cgi)
* Download/Install & Configure MySQL/MySQL Workbench
  * [MySQL](https://maven.apache.org/download.cgi)
  * [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
* [Alchemy](https://www.alchemy.com/) API key setup for ETH mainnet

## Configuring The Application
* To get this app to function properly some config values will need to be updated in:
`src/main/resources/application.yaml`
* SQL config values:
  * These values should be known from setting up MySQL/MySQL Workbench
  * `spring.datasource.url`
  * `spring.datasource.username`
  * `spring.datasource.password`
* Alchemy API config values:
  * These values should be known from setting up the Alchemy API
  * `spring.application.eth.alchemyAPIKey`
  * `spring.application.eth.alchemyBaseUrl`

## Building The Application
Run (from project root)
```bash
mvn clean install
```
* This will build the image:
`target/nft-bored-ape-0.0.1-SNAPSHOT.jar`


## Running The Application
* Assure MySQL is running

Run (from project root)
```bash
java -jar target/nft-bored-ape-0.0.1-SNAPSHOT.jar
```
* This will run the application server
* The server will immediately start polling the blockchain for Bored Ape NFT transfer events
  * This polling, by default, will occur once every minute and will look back roughly an hour
    * These values can be configured in `application.yml`
* The REST API endpoints can then be hit
* The events will be printed to the console as they are read and added to the DB. This is a way to read the events in the DB without calling the REST API

## REST API Documentation
Once the application is running the REST API can be hit via CURL request or by the web browser on your local machine

### Get All Events

#### /api/events/get/all
Run (from project root)
```bash
curl localhost:8080/api/events/get/all
```
Or visit:
http://localhost:8080/api/events/get/all

### Get Events By TokenId
#### /api/events/get/tokenId/{tokenId}
Run (from project root)
```bash
curl localhost:8080/api/events/get/tokenId/{tokenId}
```
Or visit:
http://localhost:8080/api/events/get/tokenId/{tokenId}
* tokenId will be a hex string

### Get Events By Address
#### /api/events/get/address/{address}
Run (from project root)
```bash
curl localhost:8080/api/events/get/address/{address}
```
Or visit:
http://localhost:8080/api/events/get/address/{address}
* address will be a hex string

### Mark An Event As "Read"
#### /api/events/get/address/{address}
Run (from project root)

```bash
curl localhost:8080/api/events/update/markEventAsRead/{eventId}
```
Or visit:
http://localhost:8080/api/events/update/markEventAsRead/{eventId}
* To get an `eventId` use the API to get all events OR all unread events
    * The `eventId` will be listed for each event
    * Use int value of eventId

### Get All Events that have not been marked as "Read"
#### /api/events/get/unread
Run (from project root)
```bash
curl localhost:8080/api/events/get/unread
```
Or visit:
http://localhost:8080/api/events/get/unread

## Resetting the SQL tables
* There is an option to run the app in a manner where the SQL tables will be deleted and then recreated

Run (from project root)
```bash
java -jar target/nft-bored-ape-0.0.1-SNAPSHOT.jar --shouldResetSqlTables=true
```

### Example output
Here is some example  output from running this app for a short time and interacting with it:
* Call `getAll` to see all transfer events
```bash
curl localhost:8080/api/events/get/all
Events:

Event 1:
TransferEventDTO(id=1, tokenId=0x0000000000000000000000000000000000000000000000000000000000000c61, transactionHash=0x0b99cff339f176c6a3d967b50a8ef52e12a3e5f8b1e5ae6257f244f34fd13e0f, fromAddress=0x00000000000000000000000063f7e811a4dd446cc34a19fb7cc66fbd5af0fd06, toAddress=0x0000000000000000000000003ae83c82dda6323ff2ca8c09b19bcdd8fd8b37a7, isRead=false)

Event 2:
TransferEventDTO(id=2, tokenId=0x00000000000000000000000000000000000000000000000000000000000012a5, transactionHash=0x3c98a5de9f10029a92b8a2b19f0ad2928bf405b605d695db13e819c51b26b482, fromAddress=0x0000000000000000000000001f3e6005637d630039752d33ca7ccaf55e01e0e7, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 3:
TransferEventDTO(id=3, tokenId=0x0000000000000000000000000000000000000000000000000000000000001b5e, transactionHash=0x8478a98b148fa180b2388533cdd7f82235a674ee0421e58ddaa602f2e0177d91, fromAddress=0x0000000000000000000000004cf398c58e8100fb47adf36557f3de21e3ea3033, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 4:
TransferEventDTO(id=4, tokenId=0x0000000000000000000000000000000000000000000000000000000000001ad9, transactionHash=0x0508b820fbb0fd4711a146733080e7c54aaac5b2fcf6ff7b3fdaf587e8dbaa9d, fromAddress=0x000000000000000000000000894e4ff1f6123634f676593feb0107e852ac7f4b, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 5:
TransferEventDTO(id=5, tokenId=0x0000000000000000000000000000000000000000000000000000000000000114, transactionHash=0x342fc3676ab6f413a6e71c959823482ea605ee6cae735c5eefe90945e8aebc51, fromAddress=0x0000000000000000000000009e116c88c1bcb1ae8d82ec893bbf438247015fd4, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 6:
TransferEventDTO(id=6, tokenId=0x0000000000000000000000000000000000000000000000000000000000000078, transactionHash=0x0bf4c70f9034858c9b7d4eb2f24632f6f2fa6996977fe449157fda5b44bca719, fromAddress=0x00000000000000000000000067b292f2b1c900be1455d755b1e8a7f379f9dc7d, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)
```
* Call `markEventAsRead` for transfer events 1, 3, and 5
* Then call `getAll` again to see the updates on those transfer event entries
  * `isRead` should be marked as true for those entries
```bash
curl localhost:8080/api/events/update/markEventAsRead/1
Successfully marked as read.%
 tylerfitzgerald@Tylers-MacBook-Pro  ~  curl localhost:8080/api/events/update/markEventAsRead/3
Successfully marked as read.%
 tylerfitzgerald@Tylers-MacBook-Pro  ~  curl localhost:8080/api/events/update/markEventAsRead/5
Successfully marked as read.%
 tylerfitzgerald@Tylers-MacBook-Pro  ~  curl localhost:8080/api/events/update/markEventAsRead/3
 ✘ tylerfitzgerald@Tylers-MacBook-Pro  ~  curl localhost:8080/api/events/get/all
Events:

Event 1:
TransferEventDTO(id=1, tokenId=0x0000000000000000000000000000000000000000000000000000000000000c61, transactionHash=0x0b99cff339f176c6a3d967b50a8ef52e12a3e5f8b1e5ae6257f244f34fd13e0f, fromAddress=0x00000000000000000000000063f7e811a4dd446cc34a19fb7cc66fbd5af0fd06, toAddress=0x0000000000000000000000003ae83c82dda6323ff2ca8c09b19bcdd8fd8b37a7, isRead=true)

Event 2:
TransferEventDTO(id=2, tokenId=0x00000000000000000000000000000000000000000000000000000000000012a5, transactionHash=0x3c98a5de9f10029a92b8a2b19f0ad2928bf405b605d695db13e819c51b26b482, fromAddress=0x0000000000000000000000001f3e6005637d630039752d33ca7ccaf55e01e0e7, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 3:
TransferEventDTO(id=3, tokenId=0x0000000000000000000000000000000000000000000000000000000000001b5e, transactionHash=0x8478a98b148fa180b2388533cdd7f82235a674ee0421e58ddaa602f2e0177d91, fromAddress=0x0000000000000000000000004cf398c58e8100fb47adf36557f3de21e3ea3033, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=true)

Event 4:
TransferEventDTO(id=4, tokenId=0x0000000000000000000000000000000000000000000000000000000000001ad9, transactionHash=0x0508b820fbb0fd4711a146733080e7c54aaac5b2fcf6ff7b3fdaf587e8dbaa9d, fromAddress=0x000000000000000000000000894e4ff1f6123634f676593feb0107e852ac7f4b, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 5:
TransferEventDTO(id=5, tokenId=0x0000000000000000000000000000000000000000000000000000000000000114, transactionHash=0x342fc3676ab6f413a6e71c959823482ea605ee6cae735c5eefe90945e8aebc51, fromAddress=0x0000000000000000000000009e116c88c1bcb1ae8d82ec893bbf438247015fd4, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=true)

Event 6:
TransferEventDTO(id=6, tokenId=0x0000000000000000000000000000000000000000000000000000000000000078, transactionHash=0x0bf4c70f9034858c9b7d4eb2f24632f6f2fa6996977fe449157fda5b44bca719, fromAddress=0x00000000000000000000000067b292f2b1c900be1455d755b1e8a7f379f9dc7d, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)
```
* Call `api/events/get/unread` and expect to see events 2, 4, and 6 are still not read
```bash
curl localhost:8080/api/events/get/unread
Events:

Event 1:
TransferEventDTO(id=2, tokenId=0x00000000000000000000000000000000000000000000000000000000000012a5, transactionHash=0x3c98a5de9f10029a92b8a2b19f0ad2928bf405b605d695db13e819c51b26b482, fromAddress=0x0000000000000000000000001f3e6005637d630039752d33ca7ccaf55e01e0e7, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 2:
TransferEventDTO(id=4, tokenId=0x0000000000000000000000000000000000000000000000000000000000001ad9, transactionHash=0x0508b820fbb0fd4711a146733080e7c54aaac5b2fcf6ff7b3fdaf587e8dbaa9d, fromAddress=0x000000000000000000000000894e4ff1f6123634f676593feb0107e852ac7f4b, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 3:
TransferEventDTO(id=6, tokenId=0x0000000000000000000000000000000000000000000000000000000000000078, transactionHash=0x0bf4c70f9034858c9b7d4eb2f24632f6f2fa6996977fe449157fda5b44bca719, fromAddress=0x00000000000000000000000067b292f2b1c900be1455d755b1e8a7f379f9dc7d, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false
```

* Get events by tokenId output
```bash
curl localhost:8080/api/events/get/tokenId/0x0000000000000000000000000000000000000000000000000000000000001ad9
Events:

Event 1:
TransferEventDTO(id=4, tokenId=0x0000000000000000000000000000000000000000000000000000000000001ad9, transactionHash=0x0508b820fbb0fd4711a146733080e7c54aaac5b2fcf6ff7b3fdaf587e8dbaa9d, fromAddress=0x000000000000000000000000894e4ff1f6123634f676593feb0107e852ac7f4b, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)
```

* Get events by address output
```bash
curl localhost:8080/api/events/get/address/0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc
Events:

Event 1:
TransferEventDTO(id=2, tokenId=0x00000000000000000000000000000000000000000000000000000000000012a5, transactionHash=0x3c98a5de9f10029a92b8a2b19f0ad2928bf405b605d695db13e819c51b26b482, fromAddress=0x0000000000000000000000001f3e6005637d630039752d33ca7ccaf55e01e0e7, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 2:
TransferEventDTO(id=3, tokenId=0x0000000000000000000000000000000000000000000000000000000000001b5e, transactionHash=0x8478a98b148fa180b2388533cdd7f82235a674ee0421e58ddaa602f2e0177d91, fromAddress=0x0000000000000000000000004cf398c58e8100fb47adf36557f3de21e3ea3033, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=true)

Event 3:
TransferEventDTO(id=4, tokenId=0x0000000000000000000000000000000000000000000000000000000000001ad9, transactionHash=0x0508b820fbb0fd4711a146733080e7c54aaac5b2fcf6ff7b3fdaf587e8dbaa9d, fromAddress=0x000000000000000000000000894e4ff1f6123634f676593feb0107e852ac7f4b, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 4:
TransferEventDTO(id=5, tokenId=0x0000000000000000000000000000000000000000000000000000000000000114, transactionHash=0x342fc3676ab6f413a6e71c959823482ea605ee6cae735c5eefe90945e8aebc51, fromAddress=0x0000000000000000000000009e116c88c1bcb1ae8d82ec893bbf438247015fd4, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=true)

Event 5:
TransferEventDTO(id=6, tokenId=0x0000000000000000000000000000000000000000000000000000000000000078, transactionHash=0x0bf4c70f9034858c9b7d4eb2f24632f6f2fa6996977fe449157fda5b44bca719, fromAddress=0x00000000000000000000000067b292f2b1c900be1455d755b1e8a7f379f9dc7d, toAddress=0x00000000000000000000000044b51387773ce3581156d9accb27849a204f31dc, isRead=false)
```
