package com.rossellamorgante.stackexchange

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rossellamorgante.stackexchange.model.Badge
import com.rossellamorgante.stackexchange.model.StackexchangeService
import com.rossellamorgante.stackexchange.model.User
import com.rossellamorgante.stackexchange.viewmodel.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUpRxSchedulers(){
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor {it.run() }, true)
            }
        }
        RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate}
    }

    @Mock
    lateinit var stackexchangeService: StackexchangeService

    @InjectMocks
    var listViewModel = ListViewModel()

    private var testSingle: Single<List<User>>? = null

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }
    @Test
    fun getUsersSucces(){

        val user = User(123, 1802, 1431590710.00, 4898937,
            "Cremona, CR, Italia", "https://i.stack.imgur.com/iwgDT.jpg?s=128&g=1",
            "ciaoben", Badge(28,13,0))

        val searchname = "ben"
        val usersList = arrayListOf(user)

        testSingle = Single.just(usersList)

        `when`(stackexchangeService.getUsers(searchname)).thenReturn(testSingle)

        listViewModel.refresh(searchname)

        Assert.assertEquals(1, listViewModel.users.value?.size)
        Assert.assertEquals(false, listViewModel.userLoadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)

    }

    @Test
    fun getMenuFail(){
        val searchname = "ben"
        testSingle = Single.error(Throwable())

        `when`(stackexchangeService.getUsers(searchname)).thenReturn(testSingle)

        listViewModel.refresh(searchname)

        Assert.assertEquals(true, listViewModel.userLoadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)

    }

}