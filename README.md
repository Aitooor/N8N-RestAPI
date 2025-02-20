# IgnisGuard

Anti VPN/PROXY/TOR/3G/4G/5G system with RestAPI

## Requeriments

- `git`
- `Java 17`
- `gradle` (or use `./gradlew` instead)

## How to Run without build

- `gradle bootRun` (or `./gradlew bootRun`)
- This will run without build at http://localhost:9000

## How to Build

- `gradle build` (or `./gradlew build`)
- The .jar will generate at `/build/libs/LicenceGuard-VERSION.jar`
- Run the .jar anywhere you wanna this will open at http://localhost:9000


## How Build and Copy to Local Server

- `gradle buildAndCopyJarToLocalServer` (or `./gradlew buildAndCopyJarToLocalServer`)
- This will update the `/locahost/` directory and start.sh too
- `cd localhost`
- `bash start.sh`(change the script to your own, don't push)
- This will open at http://localhost:9000