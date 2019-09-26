package com.mona.movies_app

import com.mona.movies_app.popular_people.pojo.PopularInfo
import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListModelInterface
import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListViewInterface
import com.mona.movies_app.popular_people.popular_list_screen.presenter.ListPresenter
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.ArrayList

class DataFetchTest {

    private var page: Int = 1
    private lateinit var listPresenter: ListPresenter
    lateinit var popularInfo: PopularInfo
    private val popularInfos = ArrayList<PopularInfo>()

    @Mock
    private lateinit var listViewInterface: ListViewInterface
    @Mock
    private lateinit var listModelInterface: ListModelInterface

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        listPresenter = ListPresenter(listViewInterface, listModelInterface)
    }

    @Test
    fun fetchDataSuccessfully() {
        listPresenter.callJson(page)
        verify(listModelInterface).getUrl(eq(page.toString()), any())
    }

    @Test
    fun getCallback() {
        val callback = argumentCaptor<(PopularInfo?) -> Unit>()

        popularInfo = PopularInfo()

        Mockito.`when`(listModelInterface.getUrl(eq(page.toString()), callback.capture()))
                .then {
                    callback.firstValue.invoke(popularInfo)
                }
        for (i in 0..19) {
            popularInfos.add(popularInfo)
        }

        Assert.assertEquals(20, popularInfos.size)
        listPresenter.callJson(page)
    }

    @Test
    fun getNextPage(){
        listPresenter.loadNextPage(page++)
        verify(listModelInterface).getUrl(eq(page.toString()), any())
    }

    @Test
    fun incrementPopularListSize(){
        getCallback()

        for (i in 20..39) {
            popularInfos.add(popularInfo)
        }

        Assert.assertEquals(40, popularInfos.size)
        verify(listViewInterface).addPopularList(popularInfo)
    }
}