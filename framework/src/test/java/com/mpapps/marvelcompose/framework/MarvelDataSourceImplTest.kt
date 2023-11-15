package com.mpapps.marvelcompose.framework

import com.mpapps.marvelcompose.framework.datasources.cloud.api.MarvelApi
import com.mpapps.marvelcompose.framework.datasources.cloud.MarvelDataSourceImpl
import com.mpapps.marvelcompose.framework.datasources.cloud.response.CharacterResponse
import com.mpapps.marvelcompose.framework.datasources.cloud.response.DataResponse
import com.mpapps.marvelcompose.framework.datasources.cloud.response.MarvelApiResponse
import com.mpapps.marvelcompose.framework.datasources.cloud.response.Thumbnail
import com.mpapps.marvelcompose.framework.util.CoroutineUnitTest
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock

internal class MarvelDataSourceImplTest : CoroutineUnitTest() {

    @InjectMocks
    private lateinit var marvelDataSourceImpl: MarvelDataSourceImpl

    @Mock
    private lateinit var api: MarvelApi

    private val marvelApiResponse = MarvelApiResponse(
        code = 200,
        data = DataResponse(
            listOf(
                CharacterResponse(
                    id = "id",
                    name = "name",
                    description = "description",
                    thumbnail = Thumbnail(
                        path = "path",
                        extension = "extension"
                    )
                )
            )
        )
    )

    @Test
    fun getDataCharacters_Success() = runBlockingTest {

        val flowResponse: Flow<MarvelApiResponse> = flow {
            emit(marvelApiResponse)
        }

        whenever(api.getCharacters(any(), any(), any())).doReturn(flowResponse)
        val charactersDto = marvelDataSourceImpl.getCharacters().first()

        verify(api).getCharacters(any(), any(), any())
        Assert.assertEquals(charactersDto[0].id, "id")
        Assert.assertEquals(charactersDto[0].name, "name")
        Assert.assertEquals(charactersDto[0].description, "description")
        Assert.assertEquals(charactersDto[0].thumbnail, "path.extension")
    }
}