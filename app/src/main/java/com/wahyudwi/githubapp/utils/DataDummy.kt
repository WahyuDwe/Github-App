package com.wahyudwi.githubapp.utils

import com.wahyudwi.githubapp.data.local.entity.FavoriteEntity

object DataDummy {
    fun dataDummyUser(): ArrayList<FavoriteEntity> {
        val user = ArrayList<FavoriteEntity>()

        user.add(
            FavoriteEntity(
                0,
                "user",
                "dummy.jpg"
            )
        )

        user.add(
            FavoriteEntity(
                1,
                "user1",
                "dummy1.jpg"
            )
        )

        user.add(
            FavoriteEntity(
                2,
                "user2",
                "dummy2.jpg"
            )
        )

        user.add(
            FavoriteEntity(
                3,
                "user3",
                "dummy3.jpg"
            )
        )

        user.add(
            FavoriteEntity(
                4,
                "user4",
                "dummy4.jpg"
            )
        )
        return user
    }
}