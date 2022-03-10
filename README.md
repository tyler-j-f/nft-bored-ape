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
  * [Guide](https://ladvien.com/data-analytics-mysql-localhost-setup/) to setup a local MySQL instance
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
TransferEventDTO(id=1, tokenId=2292, transactionHash=0x2820591a40edeb5d7659802481d1d2226b5bd9d948b7ac27206f1639aba2af67, fromAddress=0xd602ed2730a71743757d556612e1923b89c0e743, toAddress=0x8f5961f3a3d510aa987f2a96a204b14d63cb4230, isRead=false)

Event 2:
TransferEventDTO(id=2, tokenId=433, transactionHash=0x8cd0b6e5c0de3a712151a58ae066272c106b1e4723e181a1da422c3fefcae9cb, fromAddress=0x6639c089adfba8bb9968da643c6be208a70d6daa, toAddress=0xc310e760778ecbca4c65b6c559874757a4c4ece0, isRead=false)

Event 3:
TransferEventDTO(id=3, tokenId=5167, transactionHash=0x14cc5eedae1b8971db1d421ee1512933e9550957e7f89626bd4aba86750aecb6, fromAddress=0x48bcf7528731385aedf918135bf69e463b23f207, toAddress=0x2a363850a60b46046f8a3d9fdb63d881905da6ee, isRead=false)

Event 4:
TransferEventDTO(id=4, tokenId=433, transactionHash=0x50dd6650171250537a0755bcbb92bb530a06f8b3e1f8695944853f0411864daf, fromAddress=0xc310e760778ecbca4c65b6c559874757a4c4ece0, toAddress=0xe947cf763ab06c2adecd7382877ee307559c06c9, isRead=false)

Event 5:
TransferEventDTO(id=5, tokenId=3169, transactionHash=0x0b99cff339f176c6a3d967b50a8ef52e12a3e5f8b1e5ae6257f244f34fd13e0f, fromAddress=0x63f7e811a4dd446cc34a19fb7cc66fbd5af0fd06, toAddress=0x3ae83c82dda6323ff2ca8c09b19bcdd8fd8b37a7, isRead=false)

Event 6:
TransferEventDTO(id=6, tokenId=4773, transactionHash=0x3c98a5de9f10029a92b8a2b19f0ad2928bf405b605d695db13e819c51b26b482, fromAddress=0x1f3e6005637d630039752d33ca7ccaf55e01e0e7, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 7:
TransferEventDTO(id=7, tokenId=7006, transactionHash=0x8478a98b148fa180b2388533cdd7f82235a674ee0421e58ddaa602f2e0177d91, fromAddress=0x4cf398c58e8100fb47adf36557f3de21e3ea3033, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 8:
TransferEventDTO(id=8, tokenId=6873, transactionHash=0x0508b820fbb0fd4711a146733080e7c54aaac5b2fcf6ff7b3fdaf587e8dbaa9d, fromAddress=0x894e4ff1f6123634f676593feb0107e852ac7f4b, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 9:
TransferEventDTO(id=9, tokenId=276, transactionHash=0x342fc3676ab6f413a6e71c959823482ea605ee6cae735c5eefe90945e8aebc51, fromAddress=0x9e116c88c1bcb1ae8d82ec893bbf438247015fd4, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 10:
TransferEventDTO(id=10, tokenId=120, transactionHash=0x0bf4c70f9034858c9b7d4eb2f24632f6f2fa6996977fe449157fda5b44bca719, fromAddress=0x67b292f2b1c900be1455d755b1e8a7f379f9dc7d, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 11:
TransferEventDTO(id=11, tokenId=630, transactionHash=0x0d553eef87cf3006749d8b56ee77d0257d947a229c638d2455c0ac7ea53f45bc, fromAddress=0xe50c98ff2a17f92e9c202f3891700cae3e4fe05b, toAddress=0xd4718d35bbb86da89e18127043bc39451d246844, isRead=false)

Event 12:
TransferEventDTO(id=12, tokenId=630, transactionHash=0x435b8eea304013c8dc7d94595e77cc464f7156766ce137582270dec917cc97ab, fromAddress=0xd4718d35bbb86da89e18127043bc39451d246844, toAddress=0x7e315cec50bcfa87a66cf7e5ba03d319b50cea71, isRead=false)

Event 13:
TransferEventDTO(id=13, tokenId=630, transactionHash=0xc077fd7e0c79d393beff2e3f8a00089bfa4e1c322d5a311078df6256f84851c9, fromAddress=0x7e315cec50bcfa87a66cf7e5ba03d319b50cea71, toAddress=0x0a338d2a1d86719a7fa352186191ba1ad5ff35c0, isRead=false)

Event 14:
TransferEventDTO(id=14, tokenId=5662, transactionHash=0xbd6c2c514b156aa6765d07feece2f3ce97602f2214882b846f1cef74ddd5210c, fromAddress=0xdb9a26cd9e6c2a2acd8896670e6a71d505209415, toAddress=0x951dd3758f51d12c8c1d280e194d369c71fedf7e, isRead=false)
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
curl localhost:8080/api/events/get/all
Events:

Event 1:
TransferEventDTO(id=1, tokenId=2292, transactionHash=0x2820591a40edeb5d7659802481d1d2226b5bd9d948b7ac27206f1639aba2af67, fromAddress=0xd602ed2730a71743757d556612e1923b89c0e743, toAddress=0x8f5961f3a3d510aa987f2a96a204b14d63cb4230, isRead=true)

Event 2:
TransferEventDTO(id=2, tokenId=433, transactionHash=0x8cd0b6e5c0de3a712151a58ae066272c106b1e4723e181a1da422c3fefcae9cb, fromAddress=0x6639c089adfba8bb9968da643c6be208a70d6daa, toAddress=0xc310e760778ecbca4c65b6c559874757a4c4ece0, isRead=false)

Event 3:
TransferEventDTO(id=3, tokenId=5167, transactionHash=0x14cc5eedae1b8971db1d421ee1512933e9550957e7f89626bd4aba86750aecb6, fromAddress=0x48bcf7528731385aedf918135bf69e463b23f207, toAddress=0x2a363850a60b46046f8a3d9fdb63d881905da6ee, isRead=true)

Event 4:
TransferEventDTO(id=4, tokenId=433, transactionHash=0x50dd6650171250537a0755bcbb92bb530a06f8b3e1f8695944853f0411864daf, fromAddress=0xc310e760778ecbca4c65b6c559874757a4c4ece0, toAddress=0xe947cf763ab06c2adecd7382877ee307559c06c9, isRead=false)

Event 5:
TransferEventDTO(id=5, tokenId=3169, transactionHash=0x0b99cff339f176c6a3d967b50a8ef52e12a3e5f8b1e5ae6257f244f34fd13e0f, fromAddress=0x63f7e811a4dd446cc34a19fb7cc66fbd5af0fd06, toAddress=0x3ae83c82dda6323ff2ca8c09b19bcdd8fd8b37a7, isRead=true)

Event 6:
TransferEventDTO(id=6, tokenId=4773, transactionHash=0x3c98a5de9f10029a92b8a2b19f0ad2928bf405b605d695db13e819c51b26b482, fromAddress=0x1f3e6005637d630039752d33ca7ccaf55e01e0e7, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 7:
TransferEventDTO(id=7, tokenId=7006, transactionHash=0x8478a98b148fa180b2388533cdd7f82235a674ee0421e58ddaa602f2e0177d91, fromAddress=0x4cf398c58e8100fb47adf36557f3de21e3ea3033, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)
...
...
```
* Call `api/events/get/unread` and expect to see events 2, 4, and 6 are still not read
```bash
Events:

Event 1:
TransferEventDTO(id=2, tokenId=433, transactionHash=0x8cd0b6e5c0de3a712151a58ae066272c106b1e4723e181a1da422c3fefcae9cb, fromAddress=0x6639c089adfba8bb9968da643c6be208a70d6daa, toAddress=0xc310e760778ecbca4c65b6c559874757a4c4ece0, isRead=false)

Event 2:
TransferEventDTO(id=4, tokenId=433, transactionHash=0x50dd6650171250537a0755bcbb92bb530a06f8b3e1f8695944853f0411864daf, fromAddress=0xc310e760778ecbca4c65b6c559874757a4c4ece0, toAddress=0xe947cf763ab06c2adecd7382877ee307559c06c9, isRead=false)

Event 3:
TransferEventDTO(id=6, tokenId=4773, transactionHash=0x3c98a5de9f10029a92b8a2b19f0ad2928bf405b605d695db13e819c51b26b482, fromAddress=0x1f3e6005637d630039752d33ca7ccaf55e01e0e7, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 4:
TransferEventDTO(id=7, tokenId=7006, transactionHash=0x8478a98b148fa180b2388533cdd7f82235a674ee0421e58ddaa602f2e0177d91, fromAddress=0x4cf398c58e8100fb47adf36557f3de21e3ea3033, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 5:
TransferEventDTO(id=8, tokenId=6873, transactionHash=0x0508b820fbb0fd4711a146733080e7c54aaac5b2fcf6ff7b3fdaf587e8dbaa9d, fromAddress=0x894e4ff1f6123634f676593feb0107e852ac7f4b, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 6:
TransferEventDTO(id=9, tokenId=276, transactionHash=0x342fc3676ab6f413a6e71c959823482ea605ee6cae735c5eefe90945e8aebc51, fromAddress=0x9e116c88c1bcb1ae8d82ec893bbf438247015fd4, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 7:
TransferEventDTO(id=10, tokenId=120, transactionHash=0x0bf4c70f9034858c9b7d4eb2f24632f6f2fa6996977fe449157fda5b44bca719, fromAddress=0x67b292f2b1c900be1455d755b1e8a7f379f9dc7d, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 8:
TransferEventDTO(id=11, tokenId=630, transactionHash=0x0d553eef87cf3006749d8b56ee77d0257d947a229c638d2455c0ac7ea53f45bc, fromAddress=0xe50c98ff2a17f92e9c202f3891700cae3e4fe05b, toAddress=0xd4718d35bbb86da89e18127043bc39451d246844, isRead=false)

Event 9:
TransferEventDTO(id=12, tokenId=630, transactionHash=0x435b8eea304013c8dc7d94595e77cc464f7156766ce137582270dec917cc97ab, fromAddress=0xd4718d35bbb86da89e18127043bc39451d246844, toAddress=0x7e315cec50bcfa87a66cf7e5ba03d319b50cea71, isRead=false)

Event 10:
TransferEventDTO(id=13, tokenId=630, transactionHash=0xc077fd7e0c79d393beff2e3f8a00089bfa4e1c322d5a311078df6256f84851c9, fromAddress=0x7e315cec50bcfa87a66cf7e5ba03d319b50cea71, toAddress=0x0a338d2a1d86719a7fa352186191ba1ad5ff35c0, isRead=false)

Event 11:
TransferEventDTO(id=14, tokenId=5662, transactionHash=0xbd6c2c514b156aa6765d07feece2f3ce97602f2214882b846f1cef74ddd5210c, fromAddress=0xdb9a26cd9e6c2a2acd8896670e6a71d505209415, toAddress=0x951dd3758f51d12c8c1d280e194d369c71fedf7e, isRead=false)
```

* Get events by tokenId output
```bash
curl localhost:8080/api/events/get/tokenId/433
Events:

Event 1:
TransferEventDTO(id=2, tokenId=433, transactionHash=0x8cd0b6e5c0de3a712151a58ae066272c106b1e4723e181a1da422c3fefcae9cb, fromAddress=0x6639c089adfba8bb9968da643c6be208a70d6daa, toAddress=0xc310e760778ecbca4c65b6c559874757a4c4ece0, isRead=false)

Event 2:
TransferEventDTO(id=4, tokenId=433, transactionHash=0x50dd6650171250537a0755bcbb92bb530a06f8b3e1f8695944853f0411864daf, fromAddress=0xc310e760778ecbca4c65b6c559874757a4c4ece0, toAddress=0xe947cf763ab06c2adecd7382877ee307559c06c9, isRead=false)
```

* Get events by address output
```bash
curl localhost:8080/api/events/get/address/0x44b51387773ce3581156d9accb27849a204f31dc
Events:

Event 1:
TransferEventDTO(id=6, tokenId=4773, transactionHash=0x3c98a5de9f10029a92b8a2b19f0ad2928bf405b605d695db13e819c51b26b482, fromAddress=0x1f3e6005637d630039752d33ca7ccaf55e01e0e7, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 2:
TransferEventDTO(id=7, tokenId=7006, transactionHash=0x8478a98b148fa180b2388533cdd7f82235a674ee0421e58ddaa602f2e0177d91, fromAddress=0x4cf398c58e8100fb47adf36557f3de21e3ea3033, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 3:
TransferEventDTO(id=8, tokenId=6873, transactionHash=0x0508b820fbb0fd4711a146733080e7c54aaac5b2fcf6ff7b3fdaf587e8dbaa9d, fromAddress=0x894e4ff1f6123634f676593feb0107e852ac7f4b, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 4:
TransferEventDTO(id=9, tokenId=276, transactionHash=0x342fc3676ab6f413a6e71c959823482ea605ee6cae735c5eefe90945e8aebc51, fromAddress=0x9e116c88c1bcb1ae8d82ec893bbf438247015fd4, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)

Event 5:
TransferEventDTO(id=10, tokenId=120, transactionHash=0x0bf4c70f9034858c9b7d4eb2f24632f6f2fa6996977fe449157fda5b44bca719, fromAddress=0x67b292f2b1c900be1455d755b1e8a7f379f9dc7d, toAddress=0x44b51387773ce3581156d9accb27849a204f31dc, isRead=false)
```
