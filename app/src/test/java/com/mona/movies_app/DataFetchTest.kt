package com.mona.movies_app

import com.mona.movies_app.popular_people.pojo.PopularInfo
import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListModelInterface
import com.mona.movies_app.popular_people.popular_list_screen.interfaces.ListViewInterface
import com.mona.movies_app.popular_people.popular_list_screen.presenter.ListPresenter
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DataFetchTest {

    private lateinit var listPresenter: ListPresenter

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
        val page = 1
        listPresenter.callJson(page)
        verify(listModelInterface).getUrl(eq(page.toString()), any())
    }

    @Test
    fun getCallback() {
        val page = 1

        val callback = argumentCaptor<(PopularInfo?) -> Unit>()

        val popularInfo = PopularInfo()

        Mockito.`when`(listModelInterface.getUrl(eq(page.toString()), callback.capture()))
                .then {
                    callback.firstValue.invoke(popularInfo)
                }

        listPresenter.callJson(page)
        verify(listViewInterface).addPopularList(popularInfo)
    }
}