package com.github.damipen.steam.store

class StoreServiceUsecase(
    private val key: String,
    private val api: StoreServiceApi
) {

    suspend fun getAll(modifiedSince: Long, lastAppId: Long): Apps {
        return getApps(modifiedSince, lastAppId, AppType.ALL)
    }

    suspend fun getGames(modifiedSince: Long, lastAppId: Long): Apps {
        return getApps(modifiedSince, lastAppId, AppType.GAME)
    }

    suspend fun getDLCs(modifiedSince: Long, lastAppId: Long): Apps {
        return getApps(modifiedSince, lastAppId, AppType.DLC)
    }

    suspend fun getSoftwares(modifiedSince: Long, lastAppId: Long): Apps {
        return getApps(modifiedSince, lastAppId, AppType.SOFTWARE)
    }

    suspend fun getVideos(modifiedSince: Long, lastAppId: Long): Apps {
        return getApps(modifiedSince, lastAppId, AppType.VIDEOS)
    }

    suspend fun getHardware(modifiedSince: Long, lastAppId: Long): Apps {
        return getApps(modifiedSince, lastAppId, AppType.HARDWARE)
    }

    private sealed class AppType {
        object ALL : AppType()
        object GAME : AppType()
        object DLC : AppType()
        object SOFTWARE : AppType()
        object VIDEOS : AppType()
        object HARDWARE : AppType()
    }

    private suspend fun getApps(modifiedSince: Long = 0L, lastAppId: Long = 0L, vararg appType: AppType): Apps {
        return api.getAppList(
            key = key,
            ifModifiedSince = modifiedSince,
            includeGames = appType.any { (it is AppType.GAME) or (it is AppType.ALL) },
            includeDlc = appType.any { (it is AppType.DLC) or (it is AppType.ALL) },
            includeSoftware = appType.any { (it is AppType.SOFTWARE) or (it is AppType.ALL) },
            includeVideos = appType.any { (it is AppType.VIDEOS) or (it is AppType.ALL) },
            includeHardware = appType.any { (it is AppType.HARDWARE) or (it is AppType.ALL) },
            lastAppId = lastAppId
        ).response
    }
}
