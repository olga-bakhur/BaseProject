package com.olgabakhur.data.model.pojo

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("articles") val articleResponseList: List<ArticleResponse>
)