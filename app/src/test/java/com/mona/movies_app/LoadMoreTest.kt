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

class LoadMoreTest {

    private lateinit var listPresenter: ListPresenter
    private lateinit var popularInfo: PopularInfo
    private var page = 1
    private val popularInfos = ArrayList<PopularInfo>()
    var dataFetchTest: DataFetchTest = DataFetchTest()

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
    fun getNextPage(){
        listPresenter.loadNextPage(page++)
        verify(listModelInterface).getUrl(eq(page.toString()), any())
    }

    @Test
    fun incrementPopularListSize(){
        val callback = argumentCaptor<(PopularInfo?) -> Unit>()

        popularInfo = PopularInfo()
        popularInfo = dataFetchTest.popularInfo

            Mockito.`when`(listModelInterface.getUrl(eq(page.toString()), callback.capture()))
                    .then {
                        callback.firstValue.invoke(popularInfo)
                    }
        for (i in 20..39) {
            popularInfos.add(popularInfo)
        }

        Assert.assertEquals(40, popularInfos.size)
        verify(listViewInterface).addPopularList(popularInfo)
    }
}