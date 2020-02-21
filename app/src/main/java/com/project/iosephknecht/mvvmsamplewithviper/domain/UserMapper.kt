package com.project.iosephknecht.mvvmsamplewithviper.domain

import com.project.iosephknecht.mvvmsamplewithviper.data.User as PresentationUser
import com.project.iosephknecht.mvvmsamplewithviper.domain.gson.User as GsonUser

internal object UserMapper {
    fun fromGson(user: GsonUser): PresentationUser {
        return user.run {
            PresentationUser(
                login = login,
                updated_at = updated_at,
                url = url,
                html_url = html_url,
                created_at = created_at,
                name = name,
                type = type,
                avatar_id = avatar_id,
                avatar_url = avatar_url,
                blog = blog,
                email = email,
                events_url = events_url,
                followers = followers,
                followers_url = followers_url,
                following = following,
                following_url = following_url,
                gists_url = gists_url,
                id = id,
                location = location,
                organizations_url = organizations_url,
                public_gists = public_gists,
                public_repos = public_repos,
                received_events_url = received_events_url,
                repos_url = repos_url,
                starred_url = starred_url,
                subscriptions_url = subscriptions_url
            )
        }
    }
}