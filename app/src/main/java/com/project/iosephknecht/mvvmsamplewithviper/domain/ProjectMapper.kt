package com.project.iosephknecht.mvvmsamplewithviper.domain

import com.project.iosephknecht.mvvmsamplewithviper.data.Project as PresentationProject
import com.project.iosephknecht.mvvmsamplewithviper.domain.gson.Project as GsonProject

internal object ProjectMapper {
    fun fromGson(project: GsonProject): PresentationProject {
        return project.run {
            PresentationProject(
                id = id,
                name = name,
                description = description,
                clone_url = clone_url,
                created_at = created_at,
                default_branch = default_branch,
                forks = forks,
                forks_count = forks_count,
                full_name = full_name,
                git_url = git_url,
                has_downloads = has_downloads,
                has_issues = has_issues,
                has_pages = has_pages,
                has_wiki = has_wiki,
                homepage = homepage,
                html_url = html_url,
                language = language,
                open_issues = open_issues,
                open_issues_count = open_issues_count,
                pushed_at = pushed_at,
                watchers_count = watchers_count,
                watchers = watchers,
                url = url,
                stargazers_count = stargazers_count,
                svn_url = svn_url,
                updated_at = updated_at,
                ssh_url = ssh_url,
                owner = UserMapper.fromGson(owner!!)
            )
        }
    }
}