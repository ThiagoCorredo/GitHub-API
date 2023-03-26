package com.tcorredo.githubapi.data.mapper

import com.tcorredo.githubapi.data.domain.Mapper
import com.tcorredo.githubapi.data.domain.entity.Gist
import com.tcorredo.githubapi.data.remote.gist.GistResponse

class GistResponseToGistDomainMapper : Mapper<GistResponse, Gist> {
    override fun invoke(gistResponse: GistResponse): Gist {
        return Gist(
            id = gistResponse.id,
            createAt = gistResponse.createAt,
            files = gistResponse.files.keys.toList(),
            comments = gistResponse.comments,
            ownerName = gistResponse.owner.login,
            ownerAvatar = gistResponse.owner.avatarUrl
        )
    }
}
