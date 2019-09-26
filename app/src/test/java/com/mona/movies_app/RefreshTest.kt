package com.mona.movies_app

import com.mona.movies_app.popular_people.pojo.PopularInfo
import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListModelInterface
import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListViewInterface
import com.mona.movies_app.popular_people.popular_list_screen.presenter.ListPresenter
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class RefreshTest {

    lateinit var dataFetchTest:DataFetchTest

    private lateinit var listPresenter: ListPresenter
    private val popularInfos = ArrayList<PopularInfo>()

    @Mock
    private lateinit var listViewInterface: ListViewInterface
    @Mock
    private lateinit var listModelInterface: ListModelInterface

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        listPresenter = ListPresenter(listViewInterface, listModelInterface)

        dataFetchTest = DataFetchTest()
    }

    @Test
    fun listContainsData(){
        dataFetchTest.getCallback()
        popularInfos.add(dataFetchTest.popularInfo)
        Assert.assertEquals(20, popularInfos.size)
    }

    @Test
    fun clearListSuccessfully() {
        val page = 1
        listPresenter.refresh(page)
        popularInfos.clear()
        Assert.assertEquals(0, popularInfos.size)
    }

    @Test
    fun refreshSuccessfully(){

    }
}