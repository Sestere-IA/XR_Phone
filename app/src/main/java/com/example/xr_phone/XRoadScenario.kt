package com.example.xr_phone

import org.json.JSONArray
import org.json.JSONObject

enum class EGamePlayType
{
    Disabled,
    Pedestrian,
    Driver,
    Passenger
}

enum class EPawnType
{
    Aucun,
    Walk_Pawn_Begin,
    Adult,
    Teenager,
    Child,
    Walk_Pawn_End,
    Vehicle_Pawn_Begin,
    Kick_Scooter,
    Kick_Scooter_Child,
    Bike,
    Bike_Child,
    Scooter,
    Car,
    Bus,
    Vehicle_Pawn_End,
    Passenger_Pawn_Begin,
    Seat_1,
    Seat_2,
    Seat_3,
    Seat_4,
    Passenger_Pawn_End
}

enum class EAdvertising
{
    XROAD,
    AOS,
    DEKRA,
    EDF,
    GRTGAZ,
    J3L,
    PLATEAU_BRIARD,
    SARAWAK,
    SNCF,
    TRANSDEV

}

enum class EAIDensity
{
    NONE,
    LOW,
    NORMAL,
    HIGH
}

enum class EAIGenerationMethod
{
    STATIC,
    DYNAMIC
}

enum class ETimeOfDay
{
    AM_12,
    AM_1,
    AM_2,
    AM_3,
    AM_4,
    AM_5,
    AM_6,
    AM_7,
    AM_8,
    AM_9,
    AM_10,
    AM_11,

    PM_12,
    PM_1,
    PM_2,
    PM_3,
    PM_4,
    PM_5,
    PM_6,
    PM_7,
    PM_8,
    PM_9,
    PM_10,
    PM_11
}

enum class EWeather {
    SUNNY,
    CLOUDY,
    OVERCAST,
    FOG,
    RAIN,
    SNOW
}

enum class ESurfaceCondition
{
    DRY,
    WET,
    ICY
}


class XRoadScenario()
{
    companion object {
        fun FromJson(json: JSONObject) : XRoadScenario
        {
            var scenario:XRoadScenario = XRoadScenario()
            try {
                scenario.name = json.getString("name")
                scenario.description = json.getString("description")
                scenario.category = json.getString("category")
                scenario.uniqueTag = json.getString("uniqueTag")
                scenario.bHasShortVersion = json.getBoolean("bHasShortVersion")
                scenario.expectedTime = json.getInt("expectedTime")
                scenario.expectedTimeShort = json.getInt("expectedTimeShort")
                scenario.bNoSound = json.getBoolean("bNoSound")
                scenario.bLockPlayerMovementToPath = json.getBoolean("bLockPlayerMovementToPath")
                scenario.map = json.getString("map")
                scenario.worldSeed = json.getInt("worldSeed")
                scenario.timeOfDay = ETimeOfDay.values()[json.getInt("timeOfDay")]
                scenario.weather = EWeather.values()[json.getInt("weather")]
                scenario.surfaceCondition = ESurfaceCondition.values()[json.getInt("surfaceCondition")]
                scenario.aIdensity = EAIDensity.values()[json.getInt("aIdensity")]
                scenario.aIGenerationMethod = EAIGenerationMethod.values()[json.getInt("aIGenerationMethod")]
                scenario.aISeed = json.getInt("aISeed")
                //scenario.advertising = EAdvertising.values()[json.getString("Advertising")]

            } catch (e: org.json.JSONException)
            {
                check(false)
            }
            return scenario
        }
    }

    var name: String = ""
    var description: String = ""
    var category: String = ""
    var uniqueTag: String = ""
    var bHasShortVersion : Boolean = false
    var expectedTime: Int = 0
    var expectedTimeShort: Int = 0
    var bNoSound: Boolean = false
    var bLockPlayerMovementToPath: Boolean = false
    var map: String = ""
    var worldSeed: Int = 0
    var timeOfDay: ETimeOfDay = ETimeOfDay.AM_10
    var weather: EWeather = EWeather.SUNNY
    var surfaceCondition: ESurfaceCondition = ESurfaceCondition.DRY
    var aIdensity : EAIDensity = EAIDensity.LOW
    var aIGenerationMethod : EAIGenerationMethod = EAIGenerationMethod.STATIC
    var aISeed : Int = 0
    var advertising : EAdvertising = EAdvertising.XROAD
}


class XRoadLobbyResponse()
{
    companion object {
        fun FromJson(json: JSONObject) : XRoadLobbyResponse
        {
            var lobbyresponse:XRoadLobbyResponse = XRoadLobbyResponse()
            try {
                lobbyresponse.bNoSound = json.getBoolean("bNoSound")
                lobbyresponse.bLockPlayerMovementToPath = json.getBoolean("bLockPlayerMovement")
                lobbyresponse.spawnRotation = json.getInt("SpawnRotationOffset")
                lobbyresponse.advertising = EAdvertising.valueOf(json.getString("Advertising").uppercase())
                lobbyresponse.timeOfDay = ETimeOfDay.values()[json.getInt("TimeOfDay")]
                lobbyresponse.weather = EWeather.values()[json.getInt("Weather")]
                lobbyresponse.surfaceCondition = ESurfaceCondition.values()[json.getInt("SurfaceCondition")]
                lobbyresponse.worldSeed = json.getInt("WorldSeed")
                lobbyresponse.aIGenerationMethod = EAIGenerationMethod.values()[json.getInt("AIGenerationMethod")]
                lobbyresponse.aIdensity = EAIDensity.values()[json.getInt("AIDensity")]
                lobbyresponse.aISeed = json.getInt("AISeed")
                lobbyresponse.bMatrixReplayEnabled = json.getBoolean("bMatrixReplayEnabled")
                lobbyresponse.bEyeTrackingEnabled = json.getBoolean("bEyeTrackingEnabled")
                lobbyresponse.bMetricsEnabled = json.getBoolean("bMetricsEnabled")
                lobbyresponse.PlayerData = json.getJSONArray("PlayerData")



            } catch (e: org.json.JSONException)
            {
                check(false)
            }
            return lobbyresponse
        }
    }

    var bNoSound: Boolean = false
    var bLockPlayerMovementToPath: Boolean = false
    var map: String = ""
    var worldSeed: Int = 0
    var timeOfDay: ETimeOfDay = ETimeOfDay.AM_10
    var weather: EWeather = EWeather.SUNNY
    var surfaceCondition: ESurfaceCondition = ESurfaceCondition.DRY
    var aIdensity : EAIDensity = EAIDensity.LOW
    var aIGenerationMethod : EAIGenerationMethod = EAIGenerationMethod.STATIC
    var aISeed : Int = 0
    var advertising : EAdvertising = EAdvertising.XROAD
    var spawnRotation : Int = 0
    var bMatrixReplayEnabled : Boolean = true
    var bEyeTrackingEnabled :  Boolean = false
    var bMetricsEnabled : Boolean = false
    var PlayerData : JSONArray = JSONArray()
}