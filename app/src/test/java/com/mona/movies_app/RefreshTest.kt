package com.mona.movies_app

import com.mona.movies_app.popular_people.pojo.PopularInfo
import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListModelInterface
import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListViewInterface
import com.mona.movies_app.popular_people.popular_list_screen.presenter.ListPresenter
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.ArrayList

class RefreshTest {

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
    }

    @Test
    fun clearListSuccessfully() {
        val page = 1
        listPresenter.refresh(page)
        popularInfos.clear()
    }

    @Test
    fun refreshSuccessfully(){

    }
}