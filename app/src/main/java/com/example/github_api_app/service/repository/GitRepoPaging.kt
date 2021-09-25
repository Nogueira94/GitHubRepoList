package com.example.github_api_app.service.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.github_api_app.model.Item
import com.example.github_api_app.service.service.GitHubService
import retrofit2.HttpException
import java.io.IOException

private const val START_PAGE_INDEX = 1

class GitRepoPaging(val q: String, val service: GitHubService) : PagingSource<Int, Item>() {

override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state?.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {

        val page = params.key ?: START_PAGE_INDEX

        val data = service.getRepositoriesByStar(q,page = page)
        var itens: ArrayList<Item>
        return try {
            if(data.isSuccessful) {
                itens = data.body()?.items!!
            } else {
                throw APIResponseERROR(data.errorBody().toString())
            }
            LoadResult.Page(
                data = itens,
                prevKey = if (page == START_PAGE_INDEX) null else page - 1,
                nextKey = if (itens.isEmpty()) null else page+ 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        } catch (e: APIResponseERROR){
            LoadResult.Error(e)

        }
    }

    inner class APIResponseERROR(s: String) : Exception(s)

}