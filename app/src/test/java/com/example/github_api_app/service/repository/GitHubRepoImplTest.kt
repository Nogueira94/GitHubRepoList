package com.example.github_api_app.service.repository

import com.example.github_api_app.model.Item
import com.example.github_api_app.model.Owner
import com.example.github_api_app.model.SearchResult
import com.example.github_api_app.service.service.GitHubService
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GitHubRepoImplTest {

    private lateinit var service: GitHubService
    private lateinit var owner: Owner
    private lateinit var repo : SearchResult<Item>

    @Before
    fun setUp() {
        owner = Owner("User", "User", "User")
        repo =
            SearchResult(
                1, false,
                arrayListOf(
                    Item(1, "Kotlin", "Kotlin", owner, "Kotlin", "Kotlin", 1, 1),
                    Item(2, "Java", "Java", owner, "Java", "Java", 2, 2),
                )
            )
    }

    @Test
    fun testGetRepositoriesByStarSuccess() = runBlocking {
        service = FakeRepo("success",owner)
        Assert.assertEquals(200,service.getRepositoriesByStar("","",1).code())
    }

    @Test
    fun testGetRepositoriesByStarFailure() = runBlocking {
        service = FakeRepo("failure",owner)
        Assert.assertEquals(404,service.getRepositoriesByStar("","",1).code())
    }

    @Test
    fun testGerRepositoriesByStar() = runBlocking {
        service = FakeRepo("success",owner)
        Assert.assertEquals(repo,service.getRepositoriesByStar("","",1).body())
    }

}

class FakeRepo (private val result : String, private val owner: Owner) : GitHubService {
    override suspend fun getRepositoriesByStar(
        queryParams: String,
        sort: String,
        page: Int
    ): Response<SearchResult<Item>> {
        return if(result == "success"){
            Response.success(200,
                SearchResult(1,false,
                    arrayListOf(
                        Item(1,"Kotlin","Kotlin",owner,"Kotlin","Kotlin",1,1),
                        Item(2,"Java","Java",owner,"Java","Java",2,2),
                    )
                )
            )
        } else if (result == "failure") {
            Response.error(404, ResponseBody.create(null,"Error"))
        } else {
            Response.error(500, ResponseBody.create(null,"Internal Error"))
        }
    }
}